package pl.kielce.tu.svcalerts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.svcalerts.model.Alert;
import pl.kielce.tu.svcalerts.repositories.AlertRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertRestController {

    private final AlertRepository alertRepository;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Alert>> getAlerts(@RequestParam(required = false) boolean acknowledged) {
        if (acknowledged) {
            List<Alert> alerts = alertRepository.findAll();
            return ResponseEntity.ok(alerts);
        }

        return ResponseEntity.ok(alertRepository.findAllByAcknowledged(false));
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<Alert> createAlert(@RequestBody Alert alert) {
        alertRepository.save(alert);
        return ResponseEntity.created().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alert> updateAlert(@PathVariable UUID id, @RequestBody Alert alert) {
        Optional<Alert> alertOptional = alertRepository.findById(id);
        if (alertOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Alert existingAlert = alertOptional.get();
        existingAlert.setAcknowledged(alert.isAcknowledged());
        alertRepository.save(existingAlert);

        return ResponseEntity.ok().build();
    }
}
