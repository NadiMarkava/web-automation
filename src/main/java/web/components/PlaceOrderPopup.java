package web.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PlaceOrderPopup extends AbstractModal {

    private By totalPrice = By.id("totalm");

    public PlaceOrderPopup(WebElement root) {
        super(root);
    }

    public String getTotalPrice() {
        return root.findElement(totalPrice).getText().replace("Total: ", "");
    }
}
