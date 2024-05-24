package web.pages;

import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.components.Footer;
import web.components.NavBar;

public class MyAbstractPage extends AbstractPage {

    @FindBy(id = "footc")
    private Footer footer;

    @FindBy(id = "navbarExample")
    private NavBar navMenu;

    public MyAbstractPage(WebDriver driver) {
        super(driver);
    }

    public Footer getFooter() {
        return footer;
    }

    public NavBar getNavBar() {
        return navMenu;
    }
}
