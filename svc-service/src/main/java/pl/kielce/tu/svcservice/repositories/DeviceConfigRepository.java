package pl.kielce.tu.svcservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kielce.tu.svcservice.model.DeviceConfig;

import java.util.UUID;

public interface DeviceConfigRepository extends JpaRepository<DeviceConfig, UUID> {
}
