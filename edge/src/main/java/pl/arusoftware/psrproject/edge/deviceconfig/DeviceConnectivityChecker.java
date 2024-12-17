package pl.arusoftware.psrproject.edge.deviceconfig;

import pl.arusoftware.psrproject.edge.cache.DeviceCache;
import pl.arusoftware.psrproject.edge.kafka.KafkaProducer;
import pl.arusoftware.psrproject.edge.model.Device;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class DeviceConnectivityChecker implements Runnable {
    private final KafkaProducer producer;
    private final DeviceCache deviceCache;

    public DeviceConnectivityChecker(KafkaProducer producer) {
        this.producer = producer;
        this.deviceCache = DeviceCache.getInstance();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30000);
                Map<String, Device> devices = deviceCache.getDevices();
                Instant now = Instant.now();
                for (Device device : devices.values()) {
                    Duration difference = Duration.between(device.timestamp(), now);
                    if (difference.toSeconds() > 60 && device.isConnected()) {
                        System.out.println("Device " + device.id() + " disconnected");
                        Device updatedDevice = new Device(
                                device.id(),
                                device.name(),
                                device.isActive(),
                                false,
                                device.timestamp(),
                                device.deviceTCPServers()
                        );
                        devices.put(device.id(), updatedDevice);
                        producer.send("device", updatedDevice.id() + "-" + updatedDevice.timestamp().toEpochMilli(),
                                updatedDevice.toJSON());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
