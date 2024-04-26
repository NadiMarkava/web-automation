package web.pages;

import org.openqa.selenium.WebDriver;

public class BasePage extends AbstractPage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
}
