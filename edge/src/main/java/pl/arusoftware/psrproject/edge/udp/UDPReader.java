package pl.arusoftware.psrproject.edge.udp;

import pl.arusoftware.psrproject.edge.kafka.KafkaProducer;
import pl.arusoftware.psrproject.edge.model.Device;
import pl.arusoftware.psrproject.edge.model.Telemetry;
import pl.arusoftware.psrproject.edge.udp.observer.Observer;
import pl.arusoftware.psrproject.edge.udp.observer.Subject;

public class UDPReader implements Observer {
    private final KafkaProducer producer;

    public UDPReader(Subject subject) {
        subject.registerObserver(this);
        this.producer = new KafkaProducer("localhost:9092");
        this.producer.connect();
    }

    @Override
    public void update(String message) {
        if (message != null) {
            if (message.startsWith("{")) {
                Device deviceConnectionInfo = Device.fromJSON(message);
                producer.send("device", deviceConnectionInfo.id() + "-" + deviceConnectionInfo.timestamp().toEpochMilli(),
                        deviceConnectionInfo.toJSON());
            } else {
                Telemetry telemetry = Telemetry.fromCSV(message);
                producer.send("data", telemetry.deviceId() + "-" + telemetry.timestamp().toEpochMilli(), telemetry.toJSON());
            }
        }
    }
}
