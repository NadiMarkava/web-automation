package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class FooterComponent extends AbstractUIObject {

    @FindBy(xpath = "//div[@class='caption']/p")
    private List<ExtendedWebElement> footerTexts;

    @FindBy(xpath = "//h4/b[text()='%s']")
    private ExtendedWebElement footerTitle;

    @FindBy(xpath = "//h4/img/..")
    private ExtendedWebElement footerTextWithLogo;

    public FooterComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public List<String> getFooterTexts() {
        return footerTexts
                .stream()
                .map(t -> t.getText())
                .collect(Collectors.toList());
    }

    public boolean isFooterTitlePresent(String title){
        return footerTitle.format(title).isElementPresent();
    }

    public String getFooterTextWithLogo() {
        return footerTextWithLogo.getText();
    }
}
