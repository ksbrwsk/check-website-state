package de.ksbrwsk.cws;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class AppStateController {

    private final static String PAGE_INDEX = "index";

    private final ApplicationProperties applicationProperties;
    private final WebsiteStateHolder websiteStateHolder;

    public AppStateController(ApplicationProperties applicationProperties, WebsiteStateHolder websiteStateHolder) {
        this.applicationProperties = applicationProperties;
        this.websiteStateHolder = websiteStateHolder;
    }

    @GetMapping("/")
    public String index(@NotNull Model model) {
        addCommonModelAttributes(model);
        Collection<WebsiteState> websiteStates = this.websiteStateHolder.getWebsiteStates();
        model.addAttribute("websiteStates", websiteStates);
        return PAGE_INDEX;
    }

    private void addCommonModelAttributes(@NotNull Model model) {
        model.addAttribute("titleMessage", this.applicationProperties.title());
        model.addAttribute("appInfo", this.applicationProperties.appInfo());
    }
}
