import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestManager {
    private BufferedReader input;
    private PrintWriter output;

    public RequestManager(Socket socket) throws IOException {
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String request) {
        output.println(request);
    }

    public Number receive() throws IOException {
        var response = input.readLine();
        if (response.contains("."))
            return Double.parseDouble(response);
        else
            return Integer.parseInt(response);
    }
}
