import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class MessagingAppTest {

    IOUtils ioUtils;
    MessagingApp messagingApp = new MessagingApp();

    @Test
    public void createNewUser_ThrowException_IfEmailFieldIsEmpty() {
        //given
        String email = null;
        String password = "random";
        int age = 20;

        //when
        try {
            messagingApp.createNewUser(email, password, age);
            fail();
        } catch (ExceptionErrorMessage e) {
            //then
            assertEquals("No email was inserted", e.getMessage());
        }
    }

    @Test
    public void createNewUser_ThrowException_IfEmailDoesNotContainAtSign() {
        //given
        String email = "random.ee";
        String password = "random";
        int age = 20;

        //when
        try {
            messagingApp.createNewUser(email, password, age);
            fail();
        } catch (ExceptionErrorMessage e) {
            //then
            assertEquals("Inserted email is incorrect", e.getMessage());
        }
    }

    @Test
    public void createNewUser_ThrowException_IfEmailDoesNotContainPeriod() {
        //given
        String email = "random@random";
        String password = "random";
        int age = 20;

        //when
        try {
            messagingApp.createNewUser(email, password, age);
            fail();
        } catch (ExceptionErrorMessage e) {
            //then
            assertEquals("Inserted email is incorrect", e.getMessage());
        }
    }

    @Test
    public void createNewUser_ThrowException_IfPasswordFieldIsEmpty() {
        //given
        String email = "random@random.ee";
        String password = null;
        int age = 20;

        //when
        try {
            messagingApp.createNewUser(email, password, age);
            fail();
        } catch (ExceptionErrorMessage e) {
            //then
            assertEquals("No password was inserted", e.getMessage());
        }
    }

    @Test
    public void createNewUser_ThrowException_IfAgeIsZeroOrLess() {
        //given
        String email = "random@random.ee";
        String password = "random";
        int age = -1;

        //when
        try {
            messagingApp.createNewUser(email, password, age);
            fail();
        } catch (ExceptionErrorMessage e) {
            //then
            assertEquals("Age incorrect", e.getMessage());
        }
    }

    @Test
    public void createNewUser_ThrowException_IfUserAlreadyExists() {
        //given
        String email = "random@random.ee";
        String password = "random";
        int age = -1;

        //when
        try {
            messagingApp.createNewUser(email, password, age);
            fail();
        } catch (ExceptionErrorMessage e) {
            //then
            assertEquals("Age incorrect", e.getMessage());
        }
    }

    @Test
    public void createNewUser_PrintMessage_IfEverythingWentOK() {
        //given
        String email = "random@random.ee";
        String password = "random";
        int age = 20;

        //when
        try {
            messagingApp.createNewUser(email, password, age);
            fail();
        } catch (IOUtils e) {
            //then
            assertEquals("Age incorrect", e.printMessage());
        }
    }

    @Test
    public void loginUser() {
    }

    @Test
    public void deleteUser() {
    }
}