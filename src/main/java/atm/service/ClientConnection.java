package atm.service;

import atm.Server;
import atm.db.DatabaseDriver;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;

    public ClientConnection(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            ClientData data = Protocol.processRequest(input.readLine());
            if (data.hasError()) {
                output.println("Error status code " + StatusCode.BAD_REQUEST + ". "
                + "There is a problem with your request syntax. "
                + "Correct syntax: <code: int [0-2]>,<userID: int [positive]>,<amount: double [positive]>.");
                clientSocket.close();
            }

            // Get DB config and connect to DB (Dependency Injection)
            var db = new DatabaseDriver(Server.DB_CONFIG);

            // Check what atm.service client requested
            switch (data.getCode()) {
                case Protocol.BALANCE -> {
                    double balance = db.balance(data.getUserID());
                    output.println(balance);
                }
                case Protocol.WITHDRAW -> {
                    int status = db.withdraw(data.getUserID(), data.getAmount());
                    output.println(status);
                }
                case Protocol.DEPOSIT -> {
                    int status = db.deposit(data.getUserID(), data.getAmount());
                    output.println(status);
                }
            }

            // Close client socket (stateless)
            clientSocket.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
