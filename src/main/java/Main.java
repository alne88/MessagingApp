import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        MessagingApp messagingApp = new MessagingApp();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to BestMessages app, please log in or create new user");
        System.out.println("1 - Create new user");
        System.out.println("2 - Log in");
        System.out.println("0 - Exit");
        int userChoice = scanner.nextInt();

        while (true) {
            String email;
            String password;
            int age;

            switch (userChoice) {
                case 1:
                    System.out.println("Enter following details for creating a new user");
                    System.out.println("Email: ");
                    email = scanner.next();
                    System.out.println("Password: ");
                    password = scanner.next();
                    System.out.println("Age: ");
                    age = scanner.nextInt();

                    messagingApp.createNewUser(email, password, age);
                    System.out.println("User was created, please log in");
                    break;

                case 2:
                    System.out.println("Enter login information");
                    System.out.println("Email: ");
                    email = scanner.next();
                    System.out.println("Password: ");
                    password = scanner.next();

                    User user = messagingApp.findUser(email);
                    if (user == null) {
                        System.out.println("User was not found");
                        break;
                    }
                    if (!user.getEmail().equals(email)) {
                        System.out.println("Wrong email/password");
                        break;
                    }
                    if (!user.getPassword().equals(password)) {
                        System.out.println("Wrong email/password");
                        break;
                    }

                    // if everything is correct, do something so that user can send message
                    break;

                case 0:
                    return;

                default:
                    break;
            }
            System.out.println("--------------------------------------------");
        }
    }
}
