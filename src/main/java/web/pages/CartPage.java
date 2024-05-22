package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.components.ConfirmOrderPopup;
import web.components.PlaceOrderPopup;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends MyAbstractPage {

    protected final static String PRODUCTS_CART_XPATH = "//tr[@class='success']";
    @FindBy(xpath = PRODUCTS_CART_XPATH)
    protected static List<ExtendedWebElement> cartProducts;
    @FindBy(id = "totalp")
    private ExtendedWebElement totalPrice;
    @FindBy(xpath = "//button[text()='Place Order']")
    private ExtendedWebElement button;
    @FindBy(xpath = "//h2[text()='Products']/..//thead/tr/th")
    private List<ExtendedWebElement> tableItems;
    @FindBy(xpath = PRODUCTS_CART_XPATH + "//td/img")
    private List<ExtendedWebElement> images;
    @FindBy(xpath = "//a[text()='Delete']")
    private List<ExtendedWebElement> deleteButtons;
    @FindBy(xpath = "//div[@class='sweet-alert  showSweetAlert visible']")
    private ConfirmOrderPopup confirmOrderPopup;
    @FindBy(xpath = MODAL_XPATH)
    private PlaceOrderPopup placeOrderPopup;

    public CartPage(WebDriver driver) {
        super(driver);
        waitForElementsListNotEmpty(driver, cartProducts, 10);
    }

    public List<String> getTableItems() {
        return tableItems
                .stream()
                .map(ExtendedWebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getImageAttribute() {
        return images
                .stream()
                .map(e -> e.getAttribute("src"))
                .collect(Collectors.toList());
    }

    public List<String> getTableValues(int index) {
        return findExtendedWebElements(By.xpath(String.format(PRODUCTS_CART_XPATH + "[%s]//td", index)))
                .stream()
                .map(ExtendedWebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getProductsValue(int index) {
        return findExtendedWebElements(By.xpath(String.format(PRODUCTS_CART_XPATH + "//td[%s]", index)))
                .stream()
                .map(ExtendedWebElement::getText)
                .collect(Collectors.toList());
    }

    public int getProductsSize() {
        return cartProducts.size();
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }

    public PlaceOrderPopup clickPlaceOrderButton() {
        button.click();
        waitForUIObjectIsPresent(driver, placeOrderPopup, 5);
        return placeOrderPopup;
    }

    public ConfirmOrderPopup getConfirmOrderPopup() {
        waitForUIObjectIsPresent(driver, confirmOrderPopup, 5);
        return confirmOrderPopup;
    }

    public void clickDeleteProduct(int index) {
        int expectedSize = getProductsSize() - 1;
        deleteButtons.get(index).click();
        waitForElementListSizeChanged(driver, cartProducts, expectedSize, 5);
    }
}
