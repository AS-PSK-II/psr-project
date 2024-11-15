package pl.kielce.tu.collector.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.collector.model.TelemetryData;

import java.util.UUID;

@Repository
public interface TelemetryDataRepository extends JpaRepository<TelemetryData, UUID> {
}
