import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
    public void createNewUser_ThrowException_IfEmailIncorrectNoAtSign() {
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
            assertEquals("Inserted email format is incorrect", e.getMessage());
        }
    }

    @Test
    public void createNewUser_ThrowException_IfEmailIncorrectNoPeriod() {
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
            assertEquals("Inserted email format is incorrect", e.getMessage());
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


//    @Test
//    public void createNewUser_ThrowException_IfUserAlreadyExists() {
//        //given
//        String email = "random@random.ee";
//        String password = "random";
//        int age = 20;
//        IOUtils ioUtils = mock(IOUtils.class);
//
//        //when
//        try {
//            when(ioUtils.doesUserFileExist(email)).thenReturn(true);
//            messagingApp.createNewUser(email, password, age);
//            fail();
//        } catch (ExceptionErrorMessage e) {
//            //then
//            assertEquals("User already exists", e.getMessage());
//        }
//    }

    @Test
    public void createNewUser_ThrowException_IfUserAlreadyExists() {
        //given
        IOUtils ioUtils = mock(IOUtils.class);

        //when
        when(ioUtils.doesUserFileExist(anyString())).thenReturn(true);
        messagingApp.createNewUser("random@random.ee", "ranodm", 20);

        //then
        verify(ioUtils).printMessage(eq("User already exists"));

    }

    @Test
    public void createNewUser_PrintMessage_IfEverythingWentOK() {
        //given
        String email = "random@random.ee";
        String password = "random";
        int age = 20;
        IOUtils ioUtils = mock(IOUtils.class);

        //when
        doNothing().when(ioUtils).saveUserInfoToFile(email, password, age);
        messagingApp.createNewUser(email, password, age);

        //then
        verify(ioUtils).printMessage(eq("User was created, please log in"));
    }


    @Test
    public void loginUser_PrintMessage_IfUserDoesNotExist() {
        //given
        IOUtils ioUtils = mock(IOUtils.class);


        //when
        when(ioUtils.doesUserFileExist(anyString())).thenReturn(false);
        messagingApp.loginUser(anyString(), anyString());

        //then
        verify(ioUtils).printMessage(eq("User was not found"));

    }

    @Test
    public void deleteUser() {
    }
}