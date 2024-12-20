package de.ksbrwsk.cws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebsiteStateHolder {

    private static final Logger log = LoggerFactory.getLogger(WebsiteStateHolder.class);
    private static Map<String, WebsiteState> WEBSITE_STATES = new ConcurrentHashMap<>();

    public WebsiteStateHolder() {
//        WEBSITE_STATES.put("HOMEPAGE",           new WebsiteState("HOMEPAGE          ", State.UP, LocalDateTime.now().toString()));
//        WEBSITE_STATES.put("QR-Code Controller", new WebsiteState("QR-Code Generator ", State.UP, LocalDateTime.now().toString()));
//        WEBSITE_STATES.put("Vocabulary Trainer", new WebsiteState("Vocabulary Trainer", State.DOWN, LocalDateTime.now().toString()));
//        WEBSITE_STATES.put("Free-TV Player",     new WebsiteState("Free-TV Player    ", State.UNKNOWN, LocalDateTime.now().toString()));
    }

    public void reset() {
        WEBSITE_STATES = new ConcurrentHashMap<>();
    }

    public void addWebsiteState(WebsiteState websiteState) {
        WEBSITE_STATES.put(websiteState.name(), websiteState);
    }

    public WebsiteState getWebsiteState(String name) {
        return WEBSITE_STATES.get(name);
    }

    public Collection<WebsiteState> getWebsiteStates() {
        return WEBSITE_STATES.values();
    }

}
