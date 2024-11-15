package pl.kielce.tu.collector.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import pl.kielce.tu.collector.config.gson.InstantSerializer;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryData {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantSerializer())
            .create();

    @Id
    private UUID id = UUID.randomUUID();

    private UUID deviceId;

    private String property;

    @Column(name = "PROPERTY_VALUE")
    private Double value;

    @Column(name = "CREATED_AT")
    private Instant timestamp;

    public static TelemetryData fromJSON(String data) {
        return gson.fromJson(data, TelemetryData.class);
    }

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
