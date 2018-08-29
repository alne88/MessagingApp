import java.util.ArrayList;
import java.util.List;

public class MessagingApp {

    private List<User> listOfUsers;

    public MessagingApp() {
        this.listOfUsers = new ArrayList<User>();
    }

    public void createNewUser (String email, String password, int age) {
        listOfUsers.add(new User(email, password, age));
    }

    public User findUser (String email) {
        for (User user : listOfUsers) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    public void deleteUser (String email) {
        for (User user : listOfUsers) {
            if (email.equals(user.getEmail())) {
                listOfUsers.remove(user);

                // need to delete the user file
            }
        }
    }

    public List<User> getListOfUsers() {
        return listOfUsers;
    }
}
