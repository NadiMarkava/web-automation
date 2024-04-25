package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.components.ConfirmOrderPopup;
import web.components.PlaceOrderPopup;
import web.components.Product;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    protected static String PRODUCTS_CART_XPATH = "//tr[@class='success']";
    private By totalPrice = By.id("totalp");
    private By button = By.xpath("//button[text()='Place Order']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getTableItems() {
        return driver.findElements(By.xpath("//h2[text()='Products']/..//thead/tr/th"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getImageAttribute() {
        return driver.findElements(By.xpath(PRODUCTS_CART_XPATH + "//td/img"))
                .stream()
                .map(e -> e.getAttribute("src"))
                .collect(Collectors.toList());
    }

    public List<String> getTableValues(int index) {
        return driver.findElements(By.xpath(String.format(PRODUCTS_CART_XPATH + "[%s]//td", index)))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getProductsValue(int index) {
        return driver.findElements(By.xpath(String.format(PRODUCTS_CART_XPATH + "//td[%s]", index)))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getProductsSize() {
        return driver.findElements(By.xpath(PRODUCTS_CART_XPATH)).size();
    }

    public String getTotalPrice() {
        return driver.findElement(totalPrice).getText();
    }

    public PlaceOrderPopup clickPlaceOrderButton() {
        driver.findElement(button).click();
        waitForElementIsPresent(driver, MODAL_XPATH, 5);
        return new PlaceOrderPopup(driver.findElement(By.xpath(MODAL_XPATH)));
    }

    public ConfirmOrderPopup getConfirmOrderPopup() {
        waitForElementIsPresent(driver, "//div[@class='sweet-alert  showSweetAlert visible']", 5);
        return new ConfirmOrderPopup(driver.findElement(By.cssSelector("div[class='sweet-alert  showSweetAlert visible']")));
    }

    public void clickDeleteProduct(int index) {
        int expectedSize = getProductsSize()-1;
        driver.findElements(By.xpath("//a[text()='Delete']")).get(index).click();
        waitForElementListSizeChanged(driver, PRODUCTS_CART_XPATH, expectedSize, 5);
    }
}
