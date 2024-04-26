package web.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ConfirmOrderPopup {

    protected WebElement root;

    private By title = By.xpath(".//h2");

    public ConfirmOrderPopup(WebElement root) {
        this.root = root;
    }

    public String getTitle() {
        return root.findElement(title).getText();
    }
}
