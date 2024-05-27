package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.components.Footer;
import web.components.NavBar;

public class DemoBlazeAbstractPage extends AbstractPage {

    @FindBy(id = "footc")
    private Footer footer;

    @FindBy(id = "navbarExample")
    private NavBar navMenu;

    @FindBy(xpath = "//div[@class='modal fade show']")
    private ExtendedWebElement modal;

    public DemoBlazeAbstractPage(WebDriver driver) {
        super(driver);
    }

    public Footer getFooter() {
        return footer;
    }

    public NavBar getNavBar() {
        return navMenu;
    }

    public boolean isModalPresent() {
        return modal.isElementPresent(3);
    }
}
