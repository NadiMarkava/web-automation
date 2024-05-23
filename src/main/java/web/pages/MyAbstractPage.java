package web.pages;

import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.components.FooterComponent;
import web.components.NavBarMenuOption;

import java.lang.invoke.MethodHandles;

public class MyAbstractPage extends AbstractPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(id = "footc")
    private FooterComponent footerComponent;

    @FindBy(id = "navbarExample")
    private NavBarMenuOption navMenuComponent;

    public MyAbstractPage(WebDriver driver) {
        super(driver);
    }

    public FooterComponent getFooterComponent() {
        return footerComponent;
    }

    public NavBarMenuOption getNavMenuComponent() {
        return navMenuComponent;
    }
}
