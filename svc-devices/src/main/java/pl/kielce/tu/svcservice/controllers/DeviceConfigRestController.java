package pl.kielce.tu.svcservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.svcservice.kafka.KafkaProducer;
import pl.kielce.tu.svcservice.model.DeviceConfig;
import pl.kielce.tu.svcservice.model.DeviceSensorConfig;
import pl.kielce.tu.svcservice.repositories.DeviceConfigRepository;
import pl.kielce.tu.svcservice.repositories.DeviceSensorConfigRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
@Slf4j
public class DeviceConfigRestController {

    @Value("${app.kafka.device-config.topic}")
    private String DEVICE_CONFIG_TOPIC;

    private final DeviceConfigRepository deviceConfigRepository;
    private final DeviceSensorConfigRepository deviceSensorConfigRepository;
    private final KafkaProducer kafkaProducer;

    @GetMapping
    public ResponseEntity<List<DeviceConfig>> getAllDeviceConfigs() {
        List<DeviceConfig> deviceConfigs = deviceConfigRepository.findAll();

        return ResponseEntity.ok(deviceConfigs);
    }

    @GetMapping("/{id}/config")
    public ResponseEntity<List<DeviceSensorConfigDTO>> getDeviceSensorsConfig(@PathVariable UUID id) {
        List<DeviceSensorConfig> deviceSensorsConfig = deviceSensorConfigRepository.findAllByDevice(id);

        return ResponseEntity.ok(deviceSensorsConfig.stream().map(DeviceSensorConfigDTO::fromDeviceSensorConfiguration).toList());
    }

    @PostMapping("/{id}/config")
    @Transactional
    public ResponseEntity<DeviceConfig> createDeviceConfig(@PathVariable UUID id, @RequestBody DeviceSensorConfigDTO deviceSensorConfigDTO) {
        if (!id.equals(deviceSensorConfigDTO.device())) {
            return ResponseEntity.badRequest().build();
        }

        Optional<DeviceConfig> foundDeviceConfig = deviceConfigRepository.findById(id);

        if (foundDeviceConfig.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<DeviceSensorConfig> deviceSensorConfig = deviceSensorConfigRepository.findByDeviceAndProperty(deviceSensorConfigDTO.device(), deviceSensorConfigDTO.property());
        DeviceSensorConfig newDeviceSensorConfig = deviceSensorConfigDTO.toDeviceSensorConfiguration();

        newDeviceSensorConfig.setId(deviceSensorConfig.isPresent() ? deviceSensorConfig.get().getId() : UUID.randomUUID());

        kafkaProducer.sendMessage(DEVICE_CONFIG_TOPIC, deviceSensorConfigDTO.toJson());
        deviceSensorConfigRepository.save(newDeviceSensorConfig);

        return ResponseEntity.ok().build();
    }
}
