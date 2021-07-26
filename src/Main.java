import com.mysql.cj.log.Log;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
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
            System.out.println("5) Create User");
            System.out.println("6) Delete User");
            System.out.println("7) Print All Users");
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
                case 5:
                    System.out.println("Please enter First Name:");
                    String new_firstName = text_scanner.nextLine();
                    System.out.println("Please enter Last Name:");
                    String new_lastName = text_scanner.nextLine();
                    System.out.println("Please enter Email:");
                    String new_email = text_scanner.nextLine();
                    System.out.println("Please enter Password:");
                    String new_password = text_scanner.nextLine();
                    String res = logger.create(new_firstName, new_lastName, new_email, new_password);
                    if(res.equals("AlreadyExists")) {
                        System.out.println("\n\tUser ID is Already Exists, Please Try Again\n");
                    } else if(res.equals("ERROR")) {
                        System.out.println("\n\tSomething Went Wrong, Please Try Again\n");
                    } else {
                        System.out.println("\n\tUser Created, User ID is: " + res + "\n");
                    }
                    break;
                case 6:
                    if(logger.check()) logger.disconnect();

                    // getting id
                    System.out.println("\n\tPlease Enter User ID:\n");
                    int delete_id = number_scanner.nextInt();

                    // getting password
                    System.out.println("\n\tPlease Enter User Password:\n");
                    String delete_password = text_scanner.nextLine();
                    boolean deleted = logger.delete(delete_id, delete_password);
                    if(deleted) {
                        System.out.println("\n\tUser Deleted Successfully\n");
                    } else {
                        System.out.println("\n\tSomething Went Wrong, Please Try Again\n");
                    }
                    break;
                case 7:
                    if(logger.check()) logger.disconnect();

                    // getting id
                    System.out.println("\n\tPlease Enter Admin User ID:\n");
                    int admin_id = number_scanner.nextInt();

                    // getting password
                    System.out.println("\n\tPlease Enter Admin User Password:\n");
                    String admin_password = text_scanner.nextLine();

                    String[][] users = logger.printAllUsers(admin_id, admin_password);
                    for(int i = 0; i < users.length; i++) {
                        for(int j = 0; j < users[i].length; j++) {
                            System.out.print(users[i][j] + " ");
                        }
                        System.out.println();
                    }
                    break;
                default:
                    continue;
            }
        }
        number_scanner.close();
        text_scanner.close();
    }
}
