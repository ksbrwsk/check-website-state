package de.ksbrwsk.cws;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class CheckWebsiteState {

    private static final Logger log = LoggerFactory.getLogger(CheckWebsiteState.class);
    private static final String DATE_PATTERN_FORMAT = "dd.MM.yyyy HH:mm:ss";

    private final WebsiteStateHolder websiteStateHolder;
    private final WebsiteRepository websiteRepository;
    private final RestClient restClient;

    public CheckWebsiteState(WebsiteStateHolder websiteStateHolder, WebsiteRepository websiteRepository, RestClient restClient) {
        this.websiteStateHolder = websiteStateHolder;
        this.websiteRepository = websiteRepository;
        this.restClient = restClient;
    }

    public void check() {
        log.info("Checking website states...");
        Map<String, Website> websites = websiteRepository.getWebsites();
        websites.forEach((key, website) -> {
           if(website.hasActuator()) {
               handleActuatorRequest(website);
           } else {
                handleNonActuatorRequest(website);
           }
        });
    }

    private void handleActuatorRequest(Website website) {
        log.info("Checking actuator website {}", website.name());
        JsonNode response = this.restClient
                .get()
                .uri(website.url() + "/actuator/health")
                .retrieve()
                .body(JsonNode.class);

        JsonNode status = response.get("status");
        handleCheckResult(website, status.asText());
        log.info("Website {} is {}", website.name(), status);
    }

    private void handleCheckResult(Website website, String status) {
        switch (status) {
            case "UP":
                websiteStateHolder.addWebsiteState(new WebsiteState(website.name(), State.UP, formatCurrentInstant()));
                break;
            case "DOWN":
                websiteStateHolder.addWebsiteState(new WebsiteState(website.name(), State.DOWN, formatCurrentInstant()));
                break;
            default:
                websiteStateHolder.addWebsiteState(new WebsiteState(website.name(), State.UNKNOWN, formatCurrentInstant()));
        }
    }

    private void handleNonActuatorRequest(Website website) {
        log.info("Checking non actuator website {}", website.name());
        this.restClient
                .get()
                .uri(website.url())
                .retrieve()
                .onStatus(HttpStatusCode::is2xxSuccessful, (request, response) -> {
                    websiteStateHolder.addWebsiteState(new WebsiteState(website.name(), State.UP, formatCurrentInstant()));
                    log.info("Website {} is UP", website.name());
                })
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    websiteStateHolder.addWebsiteState(new WebsiteState(website.name(), State.DOWN, formatCurrentInstant()));
                    log.info("Website {} is DOWN", website.name());
                })
                .toBodilessEntity();
    }

    private String formatCurrentInstant() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(DATE_PATTERN_FORMAT));
    }
}
