package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class PlaceOrderPopup extends AbstractModal {

    @FindBy(id = "totalm")
    private ExtendedWebElement totalPrice;

    public PlaceOrderPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getTotalPrice() {
        return totalPrice.getText().replace("Total: ", "");
    }
}
