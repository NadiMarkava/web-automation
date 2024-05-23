package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.components.AbstractModal;
import web.components.FooterComponent;
import web.enums.Navbar;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.List;

public class MyAbstractPage extends AbstractPage {

    private final String MODAL_XPATH = "//div[@class='modal fade show']//div[@class='modal-content']";

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = MODAL_XPATH)
    private AbstractModal abstractModal;

    @FindBy(xpath = "//li[contains(@class, 'nav-item')]")
    private List<ExtendedWebElement> navMenuItems;

    @FindBy(xpath = "//li[contains(@class, 'nav-item')]/a[text()='%s']")
    private ExtendedWebElement navMenuItem;

    @FindBy(id = "footc")
    private FooterComponent footerComponent;

    public MyAbstractPage(WebDriver driver) {
        super(driver);
    }

    public FooterComponent getFooterComponent() {
        return footerComponent;
    }

    public void clickNavBar(final WebDriver driver, Navbar name) {
        waitUntil((e -> !navMenuItems.isEmpty()), 5);
        navMenuItem.format(name.getName()).click();
    }
}
