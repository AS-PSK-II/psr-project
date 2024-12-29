package pl.arusoftware.psrproject.edge.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    private static final Logger log = LoggerFactory.getLogger(TCPClient.class);
    private Socket socket;

    public void sendData(String address, int port, String message) {
        try {
            log.debug("Sending data to {} on port {}", address, port);
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
