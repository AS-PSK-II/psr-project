package pl.arusoftware.psrproject.edge.deviceconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.arusoftware.psrproject.edge.cache.DeviceCache;
import pl.arusoftware.psrproject.edge.kafka.KafkaConsumer;
import pl.arusoftware.psrproject.edge.model.Device;
import pl.arusoftware.psrproject.edge.model.DeviceConfiguration;
import pl.arusoftware.psrproject.edge.model.DeviceTCPServer;
import pl.arusoftware.psrproject.edge.tcp.TCPClient;

public class DeviceConfigMessageProcessor implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(DeviceConfigMessageProcessor.class);
    private final KafkaConsumer consumer;
    private final TCPClient tcpClient;
    private final DeviceCache deviceCache;

    public DeviceConfigMessageProcessor(KafkaConsumer consumer, TCPClient tcpClient) {
        this.consumer = consumer;
        this.tcpClient = tcpClient;
        this.deviceCache = DeviceCache.getInstance();
    }

    @Override
    public void run() {
        consumer.connect();
        while (true) {
            consumer.receive(data -> {
                try {
                    if (data != null) {
                        log.debug("Received device config message: {}", data);
                        DeviceConfiguration deviceConfiguration = DeviceConfiguration.fromJSON(data);
                        Device device = deviceCache.getDevice(deviceConfiguration.device());
                        if (device != null) {
                            DeviceTCPServer deviceTCPServer = device.deviceTCPServers().get(0);
                            tcpClient.sendData(deviceTCPServer.address(), deviceTCPServer.port(), deviceConfiguration.toJSON());
                        }
                    }
                    Thread.sleep(1000);
                    tcpClient.closeSocket();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
