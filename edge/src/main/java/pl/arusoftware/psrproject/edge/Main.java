package pl.arusoftware.psrproject.edge;

import pl.arusoftware.psrproject.edge.config.AppConfig;
import pl.arusoftware.psrproject.edge.deviceconfig.DeviceConfigMessageProcessor;
import pl.arusoftware.psrproject.edge.deviceconfig.DeviceConnectivityChecker;
import pl.arusoftware.psrproject.edge.kafka.KafkaConsumer;
import pl.arusoftware.psrproject.edge.kafka.KafkaProducer;
import pl.arusoftware.psrproject.edge.tcp.TCPClient;
import pl.arusoftware.psrproject.edge.udp.UDPReader;
import pl.arusoftware.psrproject.edge.udp.UDPServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        KafkaProducer producer = new KafkaProducer(AppConfig.KAFKA_HOST);
        producer.connect();

        runDeviceConfigMessageProcessor();
        runDeviceConnectivityChecker(producer);
        UDPServer udpServer = new UDPServer();
        new UDPReader(udpServer, producer);
        udpServer.readMessagesFromSocket();
    }

    private static void runDeviceConfigMessageProcessor() {
        TCPClient tcpClient = new TCPClient();
        KafkaConsumer consumer = new KafkaConsumer(AppConfig.KAFKA_HOST);
        DeviceConfigMessageProcessor deviceConfigMessageProcessor = new DeviceConfigMessageProcessor(consumer, tcpClient);

        Thread thread = new Thread(deviceConfigMessageProcessor);
        thread.start();
    }

    private static void runDeviceConnectivityChecker(KafkaProducer producer) {
        DeviceConnectivityChecker deviceConnectivityChecker = new DeviceConnectivityChecker(producer);

        Thread thread = new Thread(deviceConnectivityChecker);
        thread.start();
    }
}
