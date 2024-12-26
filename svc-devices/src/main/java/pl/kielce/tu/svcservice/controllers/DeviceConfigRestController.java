package pl.kielce.tu.svcservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.svcservice.kafka.KafkaProducer;
import pl.kielce.tu.svcservice.model.DeviceConfig;
import pl.kielce.tu.svcservice.repositories.DeviceConfigRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
@Slf4j
public class DeviceConfigRestController {

    private final static String DEVICE_CONFIG_TOPIC = "device-config";

    private final DeviceConfigRepository deviceConfigRepository;
    private final KafkaProducer kafkaProducer;

    @GetMapping
    public ResponseEntity<List<DeviceConfig>> getAllDeviceConfigs() {
        List<DeviceConfig> deviceConfigs = deviceConfigRepository.findAll();

        return ResponseEntity.ok(deviceConfigs);
    }

    @PostMapping("/{id}/config")
    public ResponseEntity<DeviceConfig> createDeviceConfig(@PathVariable String id, @RequestBody DeviceSensorConfigurationDTO deviceSensorConfig) {
        Optional<DeviceConfig> foundDeviceConfig = deviceConfigRepository.findById(UUID.fromString(id));

        if (foundDeviceConfig.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        kafkaProducer.sendMessage(DEVICE_CONFIG_TOPIC, deviceSensorConfig.toJson());

        return ResponseEntity.ok().build();
    }
}
