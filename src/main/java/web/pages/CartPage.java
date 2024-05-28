package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends DemoBlazeAbstractPage {

    private final String PRODUCTS_CART_XPATH = "//tr[@class='success']";

    @FindBy(xpath = "//h2[text()='Products'")
    private ExtendedWebElement title;

    @FindBy(id = "totalp")
    private ExtendedWebElement totalPrice;

    @FindBy(xpath = "//button[text()='Place Order']")
    private ExtendedWebElement placeOrderButton;

    @FindBy(xpath = "//thead/tr/th[text()='Title']")
    private ExtendedWebElement titleColumn;

    @FindBy(xpath = "//thead/tr/th[text()='Pic']")
    private ExtendedWebElement picColumn;

    @FindBy(xpath = "//thead/tr/th[text()='Price']")
    private ExtendedWebElement priceColumn;

    @FindBy(xpath = "//thead/tr/th[text()='x']")
    private ExtendedWebElement deleteColumn;

    @FindBy(xpath = PRODUCTS_CART_XPATH)
    private List<ExtendedWebElement> cartProducts;

    @FindBy(xpath = PRODUCTS_CART_XPATH + "[%s]//td/img")
    private ExtendedWebElement productImage;

    @FindBy(xpath = PRODUCTS_CART_XPATH + "[%s]//td[3]")
    private ExtendedWebElement productPrice;

    @FindBy(xpath = PRODUCTS_CART_XPATH + "[%s]//td[2]")
    private ExtendedWebElement productName;

    @FindBy(xpath = PRODUCTS_CART_XPATH + "[%s]//a[text()='Delete']")
    private ExtendedWebElement deleteButton;

    public CartPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(title);
    }

    public void waitUntilProductsLoaded() {
        waitUntil((e -> !cartProducts.isEmpty()), 5);
    }

    public boolean isPicColumnPresent() {
        return picColumn.isElementPresent();
    }

    public boolean isTitleColumnPresent() {
        return titleColumn.isElementPresent();
    }

    public boolean isPriceColumnPresent() {
        return priceColumn.isElementPresent();
    }

    public boolean isDeleteColumnPresent() {
        return deleteColumn.isElementPresent();
    }

    public String getImageAttribute(int index) {
        return productImage.format(index).getAttribute("src");
    }

    public String getProductPriceText(int index) {
        return productPrice.format(index).getText();
    }

    public String getProductNameText(int index) {
        return productName.format(index).getText();
    }

    public boolean isDeleteButtonPresent(int index) {
        return deleteButton.format(index).isElementPresent();
    }

    public int getProductsSize() {
        return cartProducts.size();
    }

    public String getTotalPriceText() {
        return totalPrice.getText();
    }

    public PlaceOrderPage clickPlaceOrderButton() {
        placeOrderButton.click();
        return new PlaceOrderPage(driver);
    }

    public void clickDeleteButton(int index) {
        deleteButton.format(index).click();
    }
}
