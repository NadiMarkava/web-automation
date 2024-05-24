package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.components.PlaceOrderPopup;

import java.util.List;

public class CartPage extends MyAbstractPage {

    private final String PRODUCTS_CART_XPATH = "//tr[@class='success']";

    @FindBy(xpath = "//h2[text()='Products'")
    private ExtendedWebElement title;

    @FindBy(id = "totalp")
    private ExtendedWebElement totalPrice;

    @FindBy(xpath = "//button[text()='Place Order']")
    private ExtendedWebElement placeOrderButton;

    @FindBy(xpath = "//h2[text()='Products']/..//thead/tr/th[%s]")
    private ExtendedWebElement tableTitle;

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
        return tableTitle.format(1).getText().equals("Pic");
    }

    public boolean isTitleColumnPresent() {
        return tableTitle.format(2).getText().equals("Title");
    }

    public boolean isPriceColumnPresent() {
        return tableTitle.format(3).getText().equals("Price");
    }

    public boolean isDeleteColumnPresent() {
        return tableTitle.format(4).getText().equals("x");
    }

    public String getImageAttribute(int index) {
        return productImage.format(index).getAttribute("src");
    }

    public String getProductPriceText(int index) {
//        waitUntil((e->productPrice.isElementPresent()), 5);
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

    public PlaceOrderPopup clickPlaceOrderButton() {
        placeOrderButton.click();
        return new PlaceOrderPopup(driver);
    }

    public void clickDeleteButton(int index) {
        int expectedSize = getProductsSize() - 1;
        deleteButton.format(index).click();
        waitUntil(e -> cartProducts.size() == expectedSize, 5);
    }
}
