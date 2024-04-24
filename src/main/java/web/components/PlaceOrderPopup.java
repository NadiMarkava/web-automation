package web.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PlaceOrderPopup extends BaseComponent {

    private final By totalPrice = By.id("totalm");
    private final By closeButton = By.className("close");
    private final By purchaseButton = By.xpath("//button[text()='Purchase']");

    public PlaceOrderPopup(WebElement root) {
        super(root);
    }

    public String getTotalPrice() {
        return root.findElement(totalPrice).getText().replace("Total: ", "");
    }

    public void type(String fieldName, String input) {
        root.findElement(By.xpath(String.format("//label[text()='%s']/../input", fieldName))).sendKeys(input);
    }

    public void closePlaceOrderPopup() {
        root.findElement(closeButton).click();
    }

    public void clickPurchaseButton() {
        root.findElement(purchaseButton).click();
    }
}
