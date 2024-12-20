package de.ksbrwsk.cws;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(AppStateRestController.API)
public class AppStateRestController {
    public static final String API = "/api/appstate";

    private final WebsiteStateHolder websiteStateHolder;

    public AppStateRestController(WebsiteStateHolder websiteStateHolder) {
        this.websiteStateHolder = websiteStateHolder;
    }

    @GetMapping
    ResponseEntity<Collection<WebsiteState>>handleGetWebsiteStates() {
        return ResponseEntity.ok(this.websiteStateHolder.getWebsiteStates());
    }
}
