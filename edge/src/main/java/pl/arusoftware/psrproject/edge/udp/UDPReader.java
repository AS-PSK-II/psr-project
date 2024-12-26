package pl.arusoftware.psrproject.edge.udp;

import pl.arusoftware.psrproject.edge.cache.DeviceCache;
import pl.arusoftware.psrproject.edge.kafka.KafkaProducer;
import pl.arusoftware.psrproject.edge.model.Device;
import pl.arusoftware.psrproject.edge.model.Telemetry;
import pl.arusoftware.psrproject.edge.udp.observer.Observer;
import pl.arusoftware.psrproject.edge.udp.observer.Subject;

public class UDPReader implements Observer {
    private final KafkaProducer producer;

    public UDPReader(Subject subject, KafkaProducer producer) {
        subject.registerObserver(this);
        this.producer = producer;
    }

    @Override
    public void update(String message) {
        if (message != null) {
            try {
                if (message.startsWith("{")) {
                    System.out.println(message);
                    Device deviceConnectionInfo = Device.fromJSON(message);
                    producer.send("device", deviceConnectionInfo.id() + "-" + deviceConnectionInfo.timestamp().toEpochMilli(),
                            deviceConnectionInfo.toJSON());
                    DeviceCache cache = DeviceCache.getInstance();
                    cache.addDevice(deviceConnectionInfo.id(), deviceConnectionInfo);
                } else {
                    Telemetry telemetry = Telemetry.fromCSV(message);
                    producer.send("data", telemetry.deviceId() + "-" + telemetry.timestamp().toEpochMilli(), telemetry.toJSON());
                }
            } catch (Exception e) {
                System.out.println("Message wrong format");
                e.printStackTrace();
            }
        }
    }
}
