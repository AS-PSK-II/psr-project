package pl.arusoftware.psrproject.edge;

import pl.arusoftware.psrproject.edge.deviceconfig.DeviceConfigMessageProcessor;
import pl.arusoftware.psrproject.edge.kafka.KafkaConsumer;
import pl.arusoftware.psrproject.edge.tcp.TCPClient;
import pl.arusoftware.psrproject.edge.udp.UDPReader;
import pl.arusoftware.psrproject.edge.udp.UDPServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TCPClient tcpClient = new TCPClient();
        KafkaConsumer consumer = new KafkaConsumer("localhost:9092");
        DeviceConfigMessageProcessor deviceConfigMessageProcessor = new DeviceConfigMessageProcessor(consumer, tcpClient);
        Thread thread = new Thread(deviceConfigMessageProcessor);
        thread.start();
        UDPServer udpServer = new UDPServer();
        new UDPReader(udpServer);
        // TODO: Read values in another Thread
        udpServer.readMessagesFromSocket();
    }
}
