package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.components.ConfirmOrderPopup;
import web.components.PlaceOrderPopup;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private final By totalPrice = By.id("totalp");

    private final By button = By.xpath("//button[text()='Place Order']");

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
        return driver.findElements(By.xpath("//tr[@class='success']//td/img"))
                .stream()
                .map(e -> e.getAttribute("src"))
                .collect(Collectors.toList());
    }

    public List<String> getTableValues(int index) {
        return driver.findElements(By.xpath(String.format("//tr[@class='success'][%s]//td", index)))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getProductsSize() {
        return driver.findElements(By.className("success")).size();
    }

    public String getTotalPrice() {
        return driver.findElement(totalPrice).getText();
    }

    public PlaceOrderPopup getPlaceOrderPopup() {
        return new PlaceOrderPopup(driver.findElement(By.cssSelector("div[class='modal fade show']")));
    }

    public PlaceOrderPopup clickPlaceOrderButton() {
        driver.findElement(button).click();
        new WebDriverWait(driver, Duration.ofSeconds(7))
                .until(d -> d.findElement(By.cssSelector("div[class='modal fade show']")));
        return new PlaceOrderPopup(driver.findElement(By.cssSelector("div[class='modal fade show']")));
    }

    public ConfirmOrderPopup getConfirmOrderPopup() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(d -> d.findElement(By.cssSelector("div[class='sweet-alert  showSweetAlert visible']")));
        return new ConfirmOrderPopup(driver.findElement(By.cssSelector("div[class='sweet-alert  showSweetAlert visible']")));
    }

    public void clickDeleteProduct(int index) {
        int size = getProductsSize()-1;
        driver.findElements(By.xpath("//a[text()='Delete']")).get(index).click();
        new WebDriverWait(driver, Duration.ofSeconds(7))
                .until(d -> getProductsSize() == size);
    }
}
