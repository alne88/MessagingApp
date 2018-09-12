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

    public String saveLastLoginTimeToFile (String email, String timestamp) {
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
