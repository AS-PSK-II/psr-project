package pl.arusoftware.psrproject.edge.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
        public void sendData(String address, int port, String message) throws IOException {
            try (Socket socket = new Socket(address, port);
                 OutputStream os = socket.getOutputStream()) {
                os.write(message.getBytes());
            }
        }
}
