package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.enums.Navbar;

import java.util.List;

public class NavMenu extends AbstractUIObject {

    @FindBy(xpath = "//li[contains(@class, 'nav-item')]")
    private List<ExtendedWebElement> navMenuItems;

    @FindBy(xpath = "//li[contains(@class, 'nav-item')]/a[text()='%s']")
    private ExtendedWebElement navMenuItem;

    public NavMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickNavBar(final WebDriver driver, Navbar name) {
        waitUntil((e -> !navMenuItems.isEmpty()), 5);
        navMenuItem.format(name.getName()).click();
    }
}
