import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Logger logger = new Logger();
        Scanner number_scanner = new Scanner(System.in);
        Scanner text_scanner = new Scanner(System.in);
        boolean running = true;
        while(running) {
            System.out.println("0) Exit");
            System.out.println("1) Check if a user is logged in");
            System.out.println("2) Log-in");
            System.out.println("3) Log-out");
            System.out.println("4) User Details");
            int op = number_scanner.nextInt();
            switch (op) {
                case 0:
                    logger.disconnect();
                    running = false;
                    break;
                case 1:
                    if(logger.check()) {
                        String name = logger.getUserName();
                        int id = logger.getUserId();
                        System.out.println("\n\tUser " + name + "#" + id + " is connected\n");
                    } else System.out.println("\n\tNo User is connected\n");
                    break;
                case 2:
                    if(logger.check()) logger.disconnect();

                    // getting id
                    System.out.println("\n\tPlease Enter User ID:\n");
                    int id = number_scanner.nextInt();

                    // getting password
                    System.out.println("\n\tPlease Enter User Password:\n");
                    String password = text_scanner.nextLine();
                    logger.connect(id, password);

                    if(logger.check()) System.out.println("\n\tSuccessfully Logged-in\n");
                    else System.out.println("\n\tFailed Logged-in\n");

                    break;
                case 3:
                    logger.disconnect();
                    System.out.println("\n\tSuccessfully Log-out\n");
                    break;
                case 4:
                    if(logger.check()) System.out.println(logger);
                    else System.out.println("\n\tNo User is connected\n");
                    break;
                default:
                    continue;
            }
        }
        number_scanner.close();
        text_scanner.close();
    }
}
