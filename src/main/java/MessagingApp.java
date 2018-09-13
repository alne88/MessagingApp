import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

public class MessagingApp {

    IOUtils ioUtils = new IOUtils();

    public void createNewUser (String email, String password, int age) {
        if (email == null) {
            throw new ExceptionErrorMessage("No email was inserted");
        }
        if (ioUtils.isEmailIncorrect(email)) {
            throw new ExceptionErrorMessage("Inserted email format is incorrect");
        }
        if (password == null) {
            throw new ExceptionErrorMessage("No password was inserted");
        }
        if (age <= 0) {
            throw new ExceptionErrorMessage("Age incorrect");
        }
        if (ioUtils.doesUserFileExist(email)) {
            ioUtils.printMessage("User already exists");
            return;
//            throw new ExceptionErrorMessage("User already exists");
        }
        //save user info to file
        ioUtils.saveUserInfoToFile(email, password, age);
        ioUtils.printMessage("User was created, please log in");
    }

    public boolean loginUser (String email, String password) {
        if (ioUtils.doesUserFileExist(email)) {
            try {
                Path filePath = Paths.get(email+".txt");
                List<String> userInfo = Files.readAllLines(filePath);

                if (!email.equals(userInfo.get(0))) {
                    ioUtils.printMessage("Wrong email/password");
                    return false;
                }
                if (!password.equals(userInfo.get(1))) {
                    ioUtils.printMessage("Wrong email/password");
                    return false;
                }
                ioUtils.printMessage("User was successfully logged in");
                return true;

            } catch (IOException e) {
            }
        }
        ioUtils.printMessage("User was not found");
        return false;
    }

    public void deleteUser (String email) {
        Path filePath = Paths.get(email+".txt");
        try {
            Files.delete(filePath);
        } catch (IOException e) {
        }
        ioUtils.printMessage("User was successfully deleted");
    }

    public void sendMessage (String senderEmail, String receiverEmail, String message) {
        String messageHistoryPath = ioUtils.getMessageFilePath(senderEmail, receiverEmail);
        String timestamp = String.valueOf(LocalDateTime.now());
        ioUtils.saveTimestampToUserFile(senderEmail, timestamp);
        try {
            FileWriter writer = new FileWriter(messageHistoryPath, true);
            writer.write("\n" + timestamp + "\n");
            writer.write(senderEmail + " says: " + message);
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
        }
    }

    public void printConversationHistory(String senderEmail, String receiverEmail) {
        String messageHistoryPath = ioUtils.getMessageFilePath(senderEmail, receiverEmail);
        if (ioUtils.doesConversationFileExist(messageHistoryPath)) {
            try {
                Path filePath = Paths.get(messageHistoryPath);
                List<String> conversation = Files.readAllLines(filePath);
                for (String line : conversation) {
                    ioUtils.printMessage(line);
                }
                ioUtils.printMessage("----------------------------");
            } catch (IOException e) {
            }
        } else {
            ioUtils.printMessage("Conversation history is empty. Go ahead, send the first message");
            ioUtils.printMessage("----------------------------");
            return;
        }
    }

    public void printUnreadMessages(String senderEmail, String receiverEmail) {
        String messageHistoryPath = ioUtils.getMessageFilePath(senderEmail, receiverEmail);
        if (ioUtils.doesConversationFileExist(messageHistoryPath)) {
            if (ioUtils.numberOfUnreadMessages(messageHistoryPath, senderEmail) > 0) {
                try {
                    Path filePath = Paths.get(messageHistoryPath);
                    List<String> conversation = Files.readAllLines(filePath);
                    int startingPoint = conversation.indexOf(ioUtils.getLastLoginTime(senderEmail));

                    for (int i = startingPoint + 3; i < conversation.size(); i++) {  // +3 to skip last seen message
                        ioUtils.printMessage(conversation.get(i));
                    }
                    ioUtils.printMessage("----------------------------");
                    ioUtils.saveTimestampToUserFile(senderEmail, ioUtils.getLastMessageTime(messageHistoryPath));
                } catch (IOException e) {
                }
            } else {
                ioUtils.printMessage("No new messages");
                System.out.println("----------------------------");
            }
        } else {
            System.out.println("Conversation history is empty. Go ahead, send the first message");
            System.out.println("----------------------------");
        }
    }

    public boolean doesUserExist (String email) {
        if (ioUtils.isEmailIncorrect(email)) {
            System.out.println("Email incorrect");
            return false;
        }
        if (ioUtils.doesUserFileExist(email)) {
            return true;
        }
        ioUtils.printMessage("User was not found");
        return false;
    }

    synchronized void  newMessageChecker (String senderEmail, String receiverEmail) {
        new Thread(() -> {
            String messageHistoryPath = ioUtils.getMessageFilePath(senderEmail, receiverEmail);
            boolean doIt = true;
            String lastCheck = null;

            while (true) {
                try {
                    if (ioUtils.doesConversationFileExist(messageHistoryPath)) {
                        if (doIt) {
                            System.out.println("You have " + ioUtils.numberOfUnreadMessages(messageHistoryPath, senderEmail) + " new messages");
                            System.out.println("----------------------------");
                            lastCheck = ioUtils.getLastMessageTime(messageHistoryPath);
                            doIt = false;
                        } else
                        if (lastCheck.equals(ioUtils.getLastMessageTime(messageHistoryPath))) {
                            doIt = false;
                        } else {
                            doIt = true;
                        }
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }
}
