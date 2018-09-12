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

    public void deleteUser (String email, String password) {
        if (ioUtils.doesUserFileExist(email)) {
            try {
                Path filePath = Paths.get(email+".txt");
                List<String> userInfo = Files.readAllLines(filePath);

                if (!email.equals(userInfo.get(0))) {
                    ioUtils.printMessage("Wrong email/password");
                    return;
                }
                if (!password.equals(userInfo.get(1))) {
                    ioUtils.printMessage("Wrong email/password");
                    return;
                }
                Files.delete(filePath);
                ioUtils.printMessage("User was successfully deleted");
            } catch (IOException e) {
            }
        } else  {
            ioUtils.printMessage("User was not found");
        }
    }

    public void sendMessage (String senderEmail, String receiverEmail, String message) {
        String messageHistoryPath = ioUtils.getMessageFilePath(senderEmail, receiverEmail);
        String timestamp = String.valueOf(LocalDateTime.now());
        ioUtils.saveLastLoginTimeToFile(senderEmail, timestamp);
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
        try {
            Path filePath = Paths.get(messageHistoryPath);
            List<String> conversation = Files.readAllLines(filePath);
            for (String line : conversation) {
                System.out.println(line);
            }
            System.out.println("----------------------------");
        } catch (IOException e) {
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

    public void newMessageChecker (String senderEmail, String receiverEmail) {
        String messageHistoryPath = ioUtils.getMessageFilePath(senderEmail, receiverEmail);
        String lastLoginTime = ioUtils.getLastLoginTime(senderEmail);

        try {
            Path filePath = Paths.get(messageHistoryPath);
            List<String> conversation = Files.readAllLines(filePath);

            Thread newMessageCheckerThread = new Thread(() -> {
                int newMessagesNumber = 0;
                String lastMessageTime = conversation.get(conversation.size()-1);
                if (lastLoginTime.equals(lastMessageTime)) {
                    System.out.println("You have no new messages");
                } else {
                    System.out.println("You have " + newMessagesNumber + " new messages");
                }
            });

            newMessageCheckerThread.start();

            System.out.println("----------------------------");
        } catch (IOException e) {
        }
    }
}
