import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        while (true) {
            var socket = new Socket("bank", Protocol.PORT);
            var rm = new RequestManager(socket);

            // Show menu and get code
            int code = menu();
            clear_terminal();

            var request = "";
            if (code == Protocol.BALANCE) {
                request = code + ",1,1" + "\n";
            }
            else {
                // Get amount if code is not balance
                double amount;
                do {
                    System.out.print("Enter a non negative amount: ");
                    Scanner in = new Scanner(System.in);
                    amount = in.nextDouble();
                } while (amount < 0);
                request = code + ",1," + amount + "\n";
            }
            rm.send(request);

            // Wait for server response
            var status = rm.receive();
            var message = "";

            if (code == Protocol.BALANCE)
                message = "Balance is " + status.doubleValue();
            else
                message = switch (status.intValue()) {
                    case StatusCode.OK -> "Successful!";
                    case StatusCode.BAD_REQUEST -> "Request body is invalid...";
                    case StatusCode.USER_NOT_FOUND -> "User was not found...";
                    case StatusCode.INSUFFICIENT_BALANCE -> "Insufficient balance...";
                    default -> "Unrecognized status code";
                };

            System.out.println("Status: " + message);
            System.out.println("Enter c to continue, otherwise enter any other key if you wish to exit...");
            var answer = new Scanner(System.in).nextLine();
            if (!answer.equalsIgnoreCase("c"))
                break;
        }
    }

    public static int menu() {
        int code;
        do {
            System.out.println("+--------------------+");
            System.out.println("|(0)----Withdraw-----|");
            System.out.println("|(1)----Deposit------|");
            System.out.println("|(2)----Balance------|");
            System.out.println("+--------------------+");
            System.out.print("Please select an option[0-2]: ");

            Scanner in = new Scanner(System.in);
            var input = in.nextLine();
            try {
                code = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                code = -1; // Set an invalid value
            }
        } while (code < 0 || code > 2);

        return code;
    }

    public static void clear_terminal() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}