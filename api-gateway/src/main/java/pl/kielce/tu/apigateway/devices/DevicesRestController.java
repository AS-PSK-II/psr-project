package pl.kielce.tu.apigateway.devices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping({"/api/v1/devices", "/api/v1/devices/"})
@Slf4j
public class DevicesRestController {

    @Value("${app.services.devices.base-url}")
    private String devicesBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<DeviceConfig[]> getDevices() {
        try {
            return restTemplate.getForEntity(devicesBaseUrl, DeviceConfig[].class);
        } catch (Exception e) {
            log.error("Cannot get devices configs", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/config")
    public ResponseEntity<DeviceSensorConfig[]> getDeviceSensorsConfig(@PathVariable UUID id) {
        final String url = devicesBaseUrl + "/" + id + "/config";
        try {
            return restTemplate.getForEntity(url, DeviceSensorConfig[].class);
        } catch (Exception e) {
            log.error("Cannot get device sensors config", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{id}/config")
    public ResponseEntity<DeviceConfig> createDeviceSensorConfig(@PathVariable UUID id, @RequestBody DeviceSensorConfig config) {
        final String url = devicesBaseUrl + "/" + id + "/config";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DeviceSensorConfig> request = new HttpEntity<>(config, headers);
            return restTemplate.postForEntity(url, request, DeviceConfig.class);
        } catch (HttpClientErrorException e) {
            log.warn("Http client error: {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            log.error("Cannot create device sensor config", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
