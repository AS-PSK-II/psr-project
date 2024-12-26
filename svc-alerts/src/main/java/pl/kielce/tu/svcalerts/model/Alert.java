package pl.kielce.tu.svcalerts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.Instant;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Alert {

    @Id
    private UUID id = UUID.randomUUID();

    private String message;

    @ColumnTransformer(write = "?::jsonb")
    @Column(name = "DATA", columnDefinition = "jsonb")
    private String data;

    private boolean acknowledged = false;

    private Instant createdAt = Instant.now();

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
