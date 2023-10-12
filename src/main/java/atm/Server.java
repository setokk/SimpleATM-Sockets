package atm;

import atm.db.DBConfig;
import atm.db.DatabaseDriver;
import atm.service.ClientConnection;
import atm.service.Protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class Server {
    public static final DBConfig DB_CONFIG =
            new DBConfig("jdbc:postgresql://bankdb:5432/atm",
            "postgres", "root!238Ji*");

    public static void main(String[] args) throws IOException, SQLException
    {
        /* Initialize database */
        var db = new DatabaseDriver(Server.DB_CONFIG);
        db.init();

        /* Wait for client connections and start virtual threads */
        var serverSocket = new ServerSocket(Protocol.PORT);
        while (true) {
            var clientSocket = serverSocket.accept();
            Thread.ofVirtual().start(new ClientConnection(clientSocket));
        }
    }
}
