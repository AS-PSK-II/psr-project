package pl.kielce.tu.svcalerts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.svcalerts.model.Alert;
import pl.kielce.tu.svcalerts.repositories.AlertRepository;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertRestController {

    private final AlertRepository alertRepository;

    @PostMapping(value = {"", "/"})
    public void createAlert(@RequestBody Alert alert) {
        alertRepository.save(alert);
    }

}
