package de.ksbrwsk.cws;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public record ApplicationProperties(String title, String appInfo, String dataFileUrl, String dataRefreshInterval) {

    @ConstructorBinding
    public ApplicationProperties {
    }
}

