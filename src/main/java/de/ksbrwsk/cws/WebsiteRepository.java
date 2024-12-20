package de.ksbrwsk.cws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebsiteRepository {

    private static final Logger log = LoggerFactory.getLogger(WebsiteRepository.class);
    private static Map<String, Website> WEBSITES = new ConcurrentHashMap<>();

    public WebsiteRepository() {
    }

    public void reset() {
        WEBSITES = new ConcurrentHashMap<>();
    }

    public void addWebsite(Website website) {
        WEBSITES.put(website.name(), website);
    }

    public Website getWebsite(String name) {
        return WEBSITES.get(name);
    }

    public Map<String, Website> getWebsites() {
        return WEBSITES;
    }
}
