package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class FooterComponent extends BaseComponent {

    @FindBy(xpath = "//div[@class='caption']")
    private List<ExtendedWebElement> footerTexts;

    public FooterComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public List<String> getFooterTexts() {
        return footerTexts
                .stream()
                .map(t -> t.getText())
                .collect(Collectors.toList());
    }
}
