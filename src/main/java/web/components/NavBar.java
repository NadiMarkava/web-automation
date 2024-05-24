package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.enums.NavBarMenuOption;

public class NavBar extends AbstractUIObject {

    @FindBy(xpath = "//li[contains(@class, 'nav-item')]/a[text()='%s']")
    private ExtendedWebElement navMenuItem;

    public NavBar(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void clickNavBarMenuOption(NavBarMenuOption menuOption) {
        navMenuItem.format(menuOption.getName()).click();
    }
}
