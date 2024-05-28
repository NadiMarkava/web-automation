package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ConfirmOrderPage extends AbstractPage {

    @FindBy(css = "div[class='sa-icon sa-success animate']")
    private ExtendedWebElement greenCheckmarkIcon;

    @FindBy(xpath = "//h2[text()='Thank you for your purchase!']")
    private ExtendedWebElement title;

    @FindBy(css = "p[class='lead text-muted ']")
    private ExtendedWebElement text;

    @FindBy(xpath = "//button[text()='OK']")
    private ExtendedWebElement okButton;

    public ConfirmOrderPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(title);
    }

    public String getConfirmText() {
        return text.getText();
    }

    public boolean isSuccessIconPresent() {
        return greenCheckmarkIcon.isElementPresent();
    }

    public boolean isOkButtonPresent() {
        return okButton.isElementPresent();
    }
}
