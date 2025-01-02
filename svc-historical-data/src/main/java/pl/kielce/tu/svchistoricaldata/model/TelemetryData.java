package pl.kielce.tu.svchistoricaldata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryData {

    @Id
    private UUID id = UUID.randomUUID();

    private UUID deviceId;

    private String property;

    @Column(name = "PROPERTY_VALUE")
    private Double value;

    @Column(name = "CREATED_AT")
    private Instant timestamp;

    @Override
    public String toString() {
        return "TelemetryData{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", property='" + property + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}

