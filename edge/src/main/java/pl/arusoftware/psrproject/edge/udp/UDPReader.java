package pl.arusoftware.psrproject.edge.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.arusoftware.psrproject.edge.cache.DeviceCache;
import pl.arusoftware.psrproject.edge.config.AppConfig;
import pl.arusoftware.psrproject.edge.kafka.KafkaProducer;
import pl.arusoftware.psrproject.edge.model.Device;
import pl.arusoftware.psrproject.edge.model.Telemetry;
import pl.arusoftware.psrproject.edge.udp.observer.Observer;
import pl.arusoftware.psrproject.edge.udp.observer.Subject;

public class UDPReader implements Observer {
    private final static Logger log = LoggerFactory.getLogger(UDPReader.class);
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
                    Device deviceConnectionInfo = Device.fromJSON(message);
                    producer.send(AppConfig.KAFKA_DEVICE_TOPIC, deviceConnectionInfo.id() + "-" + deviceConnectionInfo.timestamp().toEpochMilli(),
                            deviceConnectionInfo.toJSON());
                    DeviceCache cache = DeviceCache.getInstance();
                    cache.addDevice(deviceConnectionInfo.id(), deviceConnectionInfo);
                } else {
                    Telemetry telemetry = Telemetry.fromCSV(message);
                    producer.send(AppConfig.KAFKA_DATA_TOPIC, telemetry.deviceId() + "-" + telemetry.timestamp().toEpochMilli(), telemetry.toJSON());
                }
            } catch (Exception e) {
                log.error("Message wrong format: {}", e.getMessage(), e);
            }
        }
    }
}
