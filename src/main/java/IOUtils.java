import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class IOUtils {

        public boolean doesFileExist (String email) {
            return new File(email+".txt").isFile();
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
            } catch (FileNotFoundException e) {
            }
        }

}
