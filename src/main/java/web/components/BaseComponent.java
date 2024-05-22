package web.components;

import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public class BaseComponent extends AbstractUIObject {

    public BaseComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
