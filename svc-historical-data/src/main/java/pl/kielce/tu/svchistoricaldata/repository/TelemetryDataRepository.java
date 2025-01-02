package pl.kielce.tu.svchistoricaldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kielce.tu.svchistoricaldata.model.TelemetryData;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TelemetryDataRepository extends JpaRepository<TelemetryData, UUID> {
    List<TelemetryData> findAllByDeviceIdInAndTimestampBetween(List<UUID> deviceId, Instant from, Instant to);
    List<TelemetryData> findAllByTimestampBetween(Instant from, Instant to);
}
