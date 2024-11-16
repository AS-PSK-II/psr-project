package pl.kielce.tu.svcservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.kielce.tu.svcservice.model.DeviceConfig;
import pl.kielce.tu.svcservice.repositories.DeviceConfigRepository;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final DeviceConfigRepository deviceConfigRepository;

    @KafkaListener(topics = "device", groupId = "app-collectors")
    public void consume(String message) {
        DeviceConfig telemetryData = DeviceConfig.fromJSON(message);

        deviceConfigRepository.save(telemetryData);
    }
}
