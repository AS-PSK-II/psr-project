package pl.kielce.tu.svcservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kielce.tu.svcservice.cache.DeviceConfigCache;
import pl.kielce.tu.svcservice.model.Alert;
import pl.kielce.tu.svcservice.model.DeviceConfig;
import pl.kielce.tu.svcservice.repositories.DeviceConfigRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    @Value("${app.alerts.create.endpoint}")
    private String alertCreateEndpoint;

    private final DeviceConfigRepository deviceConfigRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @KafkaListener(topics = "${app.kafka.device.topic:device}")
    public void consume(String message) {
        DeviceConfig deviceConfig = DeviceConfig.fromJSON(message);

        deviceConfigRepository.save(deviceConfig);

        DeviceConfigCache deviceConfigCache = DeviceConfigCache.getInstance();
        boolean isAlertGenerated = false;

        if (!deviceConfigCache.containsDeviceConfig(deviceConfig.getId().toString())) {
            generateAlert(deviceConfig);
            isAlertGenerated = true;
        }
        if (!isAlertGenerated && !deviceConfig.isConnected()) {
            generateAlert(deviceConfig);
        }

        deviceConfigCache.addDeviceConfig(deviceConfig);
    }

    private void generateAlert(DeviceConfig deviceConfig) {
        Alert alert = new Alert("Device connected", DeviceConfig.toJSON(deviceConfig));

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Alert> request = new HttpEntity<>(alert, headers);
            restTemplate.postForEntity(alertCreateEndpoint, request, Alert.class);
        } catch (Exception e) {
            log.warn("Cannot create alert", e);
        }
    }
}
