package pl.kielce.tu.apigateway.historicaldata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping({"/api/v1/historical-data", "/api/v1/historical-data/"})
@Slf4j
public class HistoricalDataRestController {

    @Value("${app.services.historical-data.base-url}")
    private String historicalDataBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<TelemetryData[]> getHistoricalData(@RequestParam Map<String, String> params) {
        StringBuilder urlParams = new StringBuilder("?");
        params.forEach((key, value) -> {
            urlParams.append(key).append("=").append(value).append("&");
        });
        try {
            return restTemplate.getForEntity(historicalDataBaseUrl + urlParams, TelemetryData[].class, params);
        } catch (Exception e) {
            log.error("Cannot get historical data", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
