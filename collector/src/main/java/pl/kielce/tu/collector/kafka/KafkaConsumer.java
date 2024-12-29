package pl.kielce.tu.collector.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.kielce.tu.collector.model.TelemetryData;
import pl.kielce.tu.collector.repositories.TelemetryDataRepository;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final TelemetryDataRepository telemetryDataRepository;

    @KafkaListener(topics = {"${app.kafka.data.topic:data}"})
    public void consume(String message) {
        TelemetryData telemetryData = TelemetryData.fromJSON(message);

        telemetryDataRepository.save(telemetryData);
    }
}
