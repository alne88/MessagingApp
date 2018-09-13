import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MessagingApp messagingApp = new MessagingApp();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to BestMessages app, please log in or create new user");
            System.out.println("1 - Create new user");
            System.out.println("2 - Log in");
            System.out.println("3 - Delete user");
            System.out.println("0 - Exit");

            int userChoice = scanner.nextInt();
            scanner.nextLine();
            //empty variables so I don't have to create new ones for each case.
            String email;
            String password;
            int age;

            switch (userChoice) {
                case 1:     //create new user
                    System.out.println("Enter following details for creating a new user");
                    System.out.println("Email: ");
                    email = scanner.nextLine();
                    System.out.println("Password: ");
                    password = scanner.nextLine();
                    System.out.println("Age: ");
                    age = scanner.nextInt();
                    scanner.nextLine();

                    messagingApp.createNewUser(email, password, age);
                    break;

                case 2:     //log in
                    System.out.println("Enter login information");
                    System.out.println("Email: ");
                    email = scanner.nextLine();
                    System.out.println("Password: ");
                    password = scanner.nextLine();

                    if (messagingApp.loginUser(email, password)) {
                        System.out.println("Who would you like to send a message to?");
                        String receiverUser = scanner.nextLine();
                        if (messagingApp.doesUserExist(receiverUser)) {
                            messagingApp.newMessageChecker(email, receiverUser);
                            sendMessageMenu(email, receiverUser);
                        }
                    }
                    break;

                case 3:     //delete user
                    System.out.println("Enter login information");
                    System.out.println("Email: ");
                    email = scanner.next();
                    System.out.println("Password: ");
                    password = scanner.next();
                    if (messagingApp.loginUser(email, password)) {
                        System.out.println("Are you sure you want to delete " + email + "?");
                        System.out.println("Yes/No?");
                        String answer = scanner.next();

                        if (answer.toLowerCase().equals("yes")) {
                            messagingApp.deleteUser(email);
                        } else {
                            System.out.println("Ok, maybe later then");
                        }
                    }
                    break;

                case 0:     //exit
                    return;

                default:
                    break;
            }
            System.out.println("--------------------------------------------");
        }
    }


    public static void sendMessageMenu (String email, String receiverUser) {
        Scanner scanner = new Scanner(System.in);
        MessagingApp messagingApp = new MessagingApp();
        int userChoice;
        while (true) {
            System.out.println("1 - Send new message:");
            System.out.println("2 - Print all messages");
            System.out.println("3 - Print unread messages");
            System.out.println("0 - Exit messaging session");
            userChoice = scanner.nextInt();
            scanner.nextLine();
            switch (userChoice) {
                case 1:
                    System.out.println("Write new message to " + receiverUser + ": ");
                    String newMessage = scanner.nextLine();
                    messagingApp.sendMessage(email, receiverUser, newMessage);
                    break;

                case 2:
                    messagingApp.printConversationHistory(email, receiverUser);
                    break;

                case 3:
                    messagingApp.printUnreadMessages(email, receiverUser);
                    break;

                case 0:
                    Thread.interrupted();
                    return;

                default:
                    break;
            }
        }
    }
}
