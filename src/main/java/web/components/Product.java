package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.pages.ProductDetailCardPage;

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
    private ExtendedWebElement cardLink;

    @FindBy(xpath = ".//a[text()='Add to cart']")
    private ExtendedWebElement addToCartButton;

    public Product(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getNameText() {
        return productName.getText();
    }

    public String getCardText() {
        return productText.getText();
    }

    public String getImageAttribute() {
        return productImage.getAttribute("src");
    }

    public String getPriceText() {
        return productPrice
                .getText()
                .replace("$", "");
    }

    public ProductDetailCardPage clickCard() {
        cardLink.click();
        return new ProductDetailCardPage(driver);
    }
}
