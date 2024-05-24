package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductDetailCardPage extends MyAbstractPage {

    @FindBy(xpath = "//div[@class='product-image']//img")
    private ExtendedWebElement productImage;

    @FindBy(className = "name")
    private ExtendedWebElement productName;

    @FindBy(className = "price-container")
    private ExtendedWebElement productPrice;

    @FindBy(xpath = "//descendant::strong[text()='Product description']/../p")
    private ExtendedWebElement productDescription;
    
    @FindBy(xpath = "//a[text()='Add to cart']")
    private ExtendedWebElement addToCartButton;

    public ProductDetailCardPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(productName);
    }

    public String getNameText() {
        return productName.getText();
    }

    public String getProductPriceText() {
        return productPrice
                .getText()
                .replace("$", "")
                .replace(" *includes tax", "");
    }

    public String getCardDescription() {
        return productDescription.getText();
    }

    public String getImageAttribute() {
        return productImage.getAttribute("src");
    }

    public void clickAddToCartButton() {
        addToCartButton.click();
        Alert confirm = getDriver().switchTo().alert();
        confirm.accept();
    }
}
