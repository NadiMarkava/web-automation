package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductCardPage extends MyAbstractPage {

    @FindBy(xpath = "//div[@class='product-image']//img")
    private ExtendedWebElement productImage;

    @FindBy(className = "name")
    private ExtendedWebElement productName;

    @FindBy(className = "price-container")
    private ExtendedWebElement productPrice;

    @FindBy(xpath = "//descendant::strong[text()='Product description']/../p")
    private ExtendedWebElement productText;
    
    @FindBy(xpath = "//a[text()='Add to cart']")
    private ExtendedWebElement addToCartButton;

    public ProductCardPage(WebDriver driver) {
        super(driver);
    }

    public String getName() {
        return productName.getText();
    }

    public String getProductPrice() {
        return productPrice
                .getText()
                .replace("$", "")
                .replace(" *includes tax", "");
    }

    public String getCardText() {
        return productText.getText();
    }

    public String getImageAttribute() {
        return productImage.getAttribute("src");
    }

    public void clickCartButton() {
        addToCartButton.click();
        Alert confirm = getDriver().switchTo().alert();
        confirm.accept();
    }
}
