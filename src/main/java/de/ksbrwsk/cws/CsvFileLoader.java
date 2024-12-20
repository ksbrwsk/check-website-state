package de.ksbrwsk.cws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvFileLoader {

    private static final Logger log = LoggerFactory.getLogger(CsvFileLoader.class);

    private final ApplicationProperties applicationProperties;
    private final WebsiteRepository websiteRepository;
    private final CheckWebsiteState checkWebsiteState;

    public CsvFileLoader(ApplicationProperties applicationProperties, WebsiteRepository websiteRepository, CheckWebsiteState checkWebsiteState) {
        this.applicationProperties = applicationProperties;
        this.websiteRepository = websiteRepository;
        this.checkWebsiteState = checkWebsiteState;
    }

    @Scheduled(fixedRateString = "${application.dataRefreshInterval}")
//    @EventListener(ApplicationStartedEvent.class)
    public void process() throws IOException {
        this.processData();
    }

    private void processData() throws IOException {
        String dataFileUrl = applicationProperties.dataFileUrl();
        log.info("Read website url parameters from source: {}", dataFileUrl);
        Resource resource = new ClassPathResource(applicationProperties.dataFileUrl());
        String str = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        String[] tmp = str.split("\n");
        List<String> strings = List.of(tmp);
        List<Website> websites = strings
                .stream()
                .skip(1) // headers
                .map(this::splitCsvTuple)
                .map(this::createWebsite).toList();
        this.websiteRepository.reset();
        for (Website website : websites) {
            log.info("Name -> {}, URL -> {}", website.name(), website.url());
            this.websiteRepository.addWebsite(website);
        }
        log.info("Website urls successfully processed.");

        this.checkWebsiteState.check();
    }

    private Website createWebsite(String[] vTuple) {
        return new Website(vTuple[0], vTuple[1], Boolean.parseBoolean(vTuple[2]));
    }

    private String[] splitCsvTuple(String csvString) {
        csvString = csvString.replaceAll("(\r\n|\r)", "");
        return csvString.split(";");
    }
}
