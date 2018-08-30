import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MessagingApp {

    IOUtils ioUtils = new IOUtils();

    public void createNewUser (String email, String password, int age) {
        if (email == null) {
            throw new ExceptionErrorMessage("No email was inserted");
        }
        if (email.indexOf("@") < 0 || email.indexOf(".") < 0) {
            throw new ExceptionErrorMessage("Inserted email is incorrect");
        }
        if (password == null) {
            throw new ExceptionErrorMessage("No password was inserted");
        }
        if (age <= 0) {
            throw new ExceptionErrorMessage("Age incorrect");
        }
        if (ioUtils.doesFileExist(email)) {
            throw new ExceptionErrorMessage("User already exists");
        }
        //save user info to file
        ioUtils.saveUserInfoToFile(email, password, age);
        ioUtils.printMessage("User was created, please log in");
    }

    public void loginUser (String email, String password) {
        if (ioUtils.doesFileExist(email)) {
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
                ioUtils.printMessage("User was successfully logged in");

                //do something so user can send messages

            } catch (IOException e) {
            }
        } else  {
            ioUtils.printMessage("User was not found");
        }
    }

    public void deleteUser (String email, String password) {
        if (ioUtils.doesFileExist(email)) {
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
}
