package atm.service;

import atm.util.Validator;

public class Protocol {
    public static final int PORT = 8888;

    // Codes
    public static final int WITHDRAW = 0;
    public static final int DEPOSIT = 1;
    public static final int BALANCE = 2;

    public static ClientData processRequest(String request) {
        if (!Validator.isValidSyntax(request))
            return ClientData.error();

        // Convert to integers and double
        String[] values = request.split(",");
        int code = Integer.parseInt(values[0]);
        int userID = Integer.parseInt(values[1]);
        double amount = Double.parseDouble(values[2]);

        return new ClientData(code, userID, amount);
    }
}
