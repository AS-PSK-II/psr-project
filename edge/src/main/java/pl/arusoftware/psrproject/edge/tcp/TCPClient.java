package pl.arusoftware.psrproject.edge.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    private Socket socket;

    public void sendData(String address, int port, String message) {
        try {
            this.socket = new Socket(address, port);
            OutputStream os = socket.getOutputStream();
            os.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
