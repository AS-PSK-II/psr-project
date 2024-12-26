package pl.kielce.tu.svcservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kielce.tu.svcservice.model.Alert;
import pl.kielce.tu.svcservice.model.DeviceConfig;
import pl.kielce.tu.svcservice.repositories.DeviceConfigRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final DeviceConfigRepository deviceConfigRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @KafkaListener(topics = "device", groupId = "svc-devices")
    public void consume(String message) {
        DeviceConfig deviceConfig = DeviceConfig.fromJSON(message);

        deviceConfigRepository.save(deviceConfig);

        generateAlert(deviceConfig);
    }

    private void generateAlert(DeviceConfig deviceConfig) {
        Alert alert = new Alert("Device connected", DeviceConfig.toJSON(deviceConfig));

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Alert> request = new HttpEntity<>(alert, headers);
            restTemplate.postForEntity("http://localhost:8082/api/v1/alerts", request, Alert.class);
        } catch (Exception e) {
            log.warn("Cannot create alert", e);
        }
    }
}
