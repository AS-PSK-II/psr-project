package pl.kielce.tu.svcalerts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kielce.tu.svcalerts.model.Alert;

import java.util.UUID;

public interface AlertRepository extends JpaRepository<Alert, UUID> {
}
