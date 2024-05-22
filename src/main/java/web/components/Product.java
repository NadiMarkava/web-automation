package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Product extends BaseComponent {

    @FindBy(xpath = ".//a/img")
    private ExtendedWebElement productImage;

    @FindBy(className = "card-title")
    private ExtendedWebElement productName;

    @FindBy(xpath = ".//h5")
    private ExtendedWebElement productPrice;

    @FindBy(id = "article")
    private ExtendedWebElement productText;

    public Product(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getName() {
        return productName.getText();
    }

    public String getCardText() {
        return productText.getText();
    }

    public String getImageAttribute() {
        return productImage.getAttribute("src");
    }

    public String getPrice() {
        return productPrice
                .getText()
                .replace("$", "");
    }
}
