package pl.kielce.tu.svcservice.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kielce.tu.svcservice.gson.InstantSerializer;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceConfig {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantSerializer())
            .create();

    @Id
    private UUID id;

    private String name;

    private boolean isActive;

    private boolean isConnected;

    @Column(name = "CREATED_AT")
    private Instant timestamp;

    public static DeviceConfig fromJSON(String json) {
        return gson.fromJson(json, DeviceConfig.class);
    }

    @Override
    public String toString() {
        return "DeviceConfig{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                ", isConnected=" + isConnected +
                ", timestamp=" + timestamp +
                '}';
    }
}
