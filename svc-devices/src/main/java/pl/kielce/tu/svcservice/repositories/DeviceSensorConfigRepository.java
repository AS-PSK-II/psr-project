package pl.kielce.tu.svcservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kielce.tu.svcservice.model.DeviceSensorConfig;

import java.util.Optional;
import java.util.UUID;

public interface DeviceSensorConfigRepository extends JpaRepository<DeviceSensorConfig, UUID> {

    Optional<DeviceSensorConfig> findByDevice(UUID device);

    Optional<DeviceSensorConfig> findByDeviceAndProperty(UUID device, String property);
}
