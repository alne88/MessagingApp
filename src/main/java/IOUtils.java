import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

public class IOUtils {

    public boolean doesUserFileExist(String email) {
        return new File(email+".txt").isFile();
    }

    public boolean doesConversationFileExist(String filepath) {
        if (new File(filepath).isFile()) {
            return true;
        }
        return false;
    }

    public String getMessageFilePath (String senderEmail, String receiverEmail) {
        if (new File(senderEmail + "_" + receiverEmail + ".txt").isFile()) {
            return (senderEmail + "_" + receiverEmail + ".txt");
        }
        if (new File(receiverEmail + "_" + senderEmail + ".txt").isFile()) {
            return (receiverEmail + "_" + senderEmail + ".txt");
        }
        return (senderEmail + "_" + receiverEmail + ".txt");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void saveUserInfoToFile (String email, String password, int age) {
        try {
            PrintStream fileStream = new PrintStream(new File(email + ".txt"));
            fileStream.println(email);
            fileStream.println(password);
            fileStream.println(age);
            fileStream.println(String.valueOf(LocalDateTime.now()));
        } catch (FileNotFoundException e) {
        }
    }

    public boolean isEmailIncorrect (String email) {
        if (email.indexOf("@") < 0 || email.indexOf(".") < 0) {
            return true;
        }
        return false;
    }

    public String getLastLoginTime (String email) {
        try {
            Path filePath = Paths.get(email+".txt");
            List<String> conversation = Files.readAllLines(filePath);
            return conversation.get(3);
        } catch (IOException e) {
        }
        return null;
    }

    public String getLastMessageTime (String conversationHistoryPath) {
        try {
            Path filePath = Paths.get(conversationHistoryPath);
            List<String> conversation = Files.readAllLines(filePath);
            return conversation.get(conversation.size()-1);                     //this can lead to problems in the future when user sends multi line message, right now timestamp is line above the message.
        } catch (IOException e) {
        }
        return null;
    }

    public int numberOfUnreadMessages (String conversationHistoryPath, String email) {
        try {
            Path filePath = Paths.get(conversationHistoryPath);
            List<String> conversation = Files.readAllLines(filePath);

            if (getLastLoginTime(email).equals(getLastMessageTime(conversationHistoryPath))) {
                return 0;
            }
            return ((conversation.size() - conversation.indexOf(getLastLoginTime(email))) / 3);         //each message consists of 3 lines - timestamp, message and empty line

        } catch (IOException e) {
        }
        return 0;
    }

    public String saveTimestampToUserFile(String email, String timestamp) {
        try {
            Path filePath = Paths.get(email+".txt");
            List<String> conversation = Files.readAllLines(filePath);

            PrintStream fileStream = new PrintStream(new File(email + ".txt"));
            fileStream.println(conversation.get(0));
            fileStream.println(conversation.get(1));
            fileStream.println(conversation.get(2));
            fileStream.println(timestamp);
        } catch (IOException e) {
        }
        return null;
    }
}
