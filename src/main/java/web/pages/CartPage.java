package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.components.ConfirmOrderPopup;
import web.components.PlaceOrderPopup;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends MyAbstractPage {

    private final String PRODUCTS_CART_XPATH = "//tr[@class='success']";

    @FindBy(xpath = PRODUCTS_CART_XPATH)
    private List<ExtendedWebElement> cartProducts;

    @FindBy(id = "totalp")
    private ExtendedWebElement totalPrice;

    @FindBy(xpath = "//button[text()='Place Order']")
    private ExtendedWebElement button;

    @FindBy(xpath = "//h2[text()='Products']/..//thead/tr/th")
    private List<ExtendedWebElement> tableItems;

    @FindBy(xpath = PRODUCTS_CART_XPATH + "[%s]//td/img")
    private ExtendedWebElement productImg;

    @FindBy(xpath = PRODUCTS_CART_XPATH + "[%s]//td[3]")
    private ExtendedWebElement productPrice;

    @FindBy(xpath = PRODUCTS_CART_XPATH + "[%s]//td[2]")
    private ExtendedWebElement productName;

    @FindBy(xpath = "//a[text()='Delete']")
    private List<ExtendedWebElement> deleteButtons;

    @FindBy(xpath = "//div[@class='sweet-alert  showSweetAlert visible']")
    private ConfirmOrderPopup confirmOrderPopup;

    @FindBy(xpath = "//div[@class='modal fade show']//div[@class='modal-content']")
    private PlaceOrderPopup placeOrderPopup;

    public CartPage(WebDriver driver) {
        super(driver);
        waitUntil((e -> !cartProducts.isEmpty()), 5);
    }

    public List<String> getTableItems() {
        return tableItems
                .stream()
                .map(ExtendedWebElement::getText)
                .collect(Collectors.toList());
    }

    public String getImageAttribute(int index) {
        return productImg.format(index).getAttribute("src");
    }

    public String getProductPrice(int index) {
        waitUntil((e->productPrice.isElementPresent()), 5);
        return productPrice.format(index).getText();
    }

    public String getProductName(int index) {
        return productName.format(index).getText();
    }

    public boolean isDeleteButtonPresent(int index) {
        return deleteButtons.get(index).isElementPresent() && deleteButtons.get(index).isClickable();
    }

    public int getProductsSize() {
        return cartProducts.size();
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }

    public PlaceOrderPopup clickPlaceOrderButton() {
        button.click();
        waitUntil((e -> placeOrderPopup.isUIObjectPresent()), 5);
        return placeOrderPopup;
    }

    public ConfirmOrderPopup getConfirmOrderPopup() {
        waitUntil((e -> confirmOrderPopup.isUIObjectPresent()), 5);
        return confirmOrderPopup;
    }

    public void clickDeleteProduct(int index) {
        int expectedSize = getProductsSize() - 1;
        deleteButtons.get(index).click();
        waitUntil(e -> cartProducts.size() == expectedSize, 5);
    }
}
