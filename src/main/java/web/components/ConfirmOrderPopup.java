package web.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ConfirmOrderPopup extends BaseComponent {

    private final By title = By.xpath(".//h2");

    public ConfirmOrderPopup(WebElement root) {
        super(root);
    }

    public String getTitle() {
        return root.findElement(title).getText();
    }
}
