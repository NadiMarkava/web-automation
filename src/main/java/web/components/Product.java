package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.pages.ProductCardPage;

import java.util.List;

public class Product extends AbstractUIObject {

    @FindBy(xpath = ".//a/img")
    private ExtendedWebElement productImage;

    @FindBy(className = "card-title")
    private ExtendedWebElement productName;

    @FindBy(xpath = ".//h5")
    private ExtendedWebElement productPrice;

    @FindBy(id = "article")
    private ExtendedWebElement productText;

    @FindBy(xpath = ".//h4/a")
    private ExtendedWebElement cardLinks;

    @FindBy(xpath = "//a[text()='Add to cart']")
    private ExtendedWebElement addToCartButton;

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

    public ProductCardPage clickCard() {
        cardLinks.click();
        waitUntil(e -> addToCartButton.isElementPresent(), 5);
        return new ProductCardPage(driver);
    }
}
