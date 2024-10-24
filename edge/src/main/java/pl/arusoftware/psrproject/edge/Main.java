package pl.arusoftware.psrproject.edge;

import pl.arusoftware.psrproject.edge.udp.UDPReader;
import pl.arusoftware.psrproject.edge.udp.UDPServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        UDPServer udpServer = new UDPServer();
        new UDPReader(udpServer);
        // TODO: Read values in another Thread
        udpServer.readMessagesFromSocket();
    }
}
