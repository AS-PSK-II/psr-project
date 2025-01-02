package pl.kielce.tu.svchistoricaldata.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.svchistoricaldata.model.TelemetryData;
import pl.kielce.tu.svchistoricaldata.repository.TelemetryDataRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/historical-data")
@RequiredArgsConstructor
@Slf4j
public class TelemetryDataRestController {

    private final TelemetryDataRepository telemetryDataRepository;

    @GetMapping
    public ResponseEntity<List<TelemetryData>> getTelemetryData(
            @RequestParam(required = false) List<UUID> deviceIds,
            @RequestParam(required = false) Instant from,
            @RequestParam(required = false) Instant to) {
        Instant toTimestamp = to != null ? to : Instant.now();
        Instant fromTimestamp = from != null ? from : Instant.EPOCH;
        List<TelemetryData> result;

        if (deviceIds != null && !deviceIds.isEmpty()) {
            result = telemetryDataRepository.findAllByDeviceIdInAndTimestampBetween(deviceIds, fromTimestamp, toTimestamp);
        } else {
            result = telemetryDataRepository.findAllByTimestampBetween(fromTimestamp, toTimestamp);
        }

        return ResponseEntity.ok(result);
    }
}
