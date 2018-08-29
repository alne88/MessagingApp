import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class User {


    String email;
    String password;
    int age;

    public User(String email, String password, int age) {
        this.email = email;
        this.password = password;
        this.age = age;
        saveUserInfoToFile(email, password, age);
    }

    public void saveUserInfoToFile(String email, String password, int age) {
        try {
            PrintStream fileStream = new PrintStream(new File(email + ".txt"));
            String toFile = "Email:" + email + " " + "Password:" + password + " " + "Age:" + age;
            fileStream.println(toFile);
        } catch (FileNotFoundException e) {
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }
}
