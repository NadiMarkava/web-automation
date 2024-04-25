package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.enums.Navbar;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import static web.pages.CartPage.PRODUCTS_CART_XPATH;
import static web.pages.HomePage.PRODUCTS_XPATH;

public class AbstractPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    protected static String MODAL_XPATH = "//div[@class='modal fade show']//div[@class='modal-content']";

    public void waitForElementsListNotEmpty(final WebDriver driver, String xpath, int timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(d -> !driver.findElements(By.xpath(xpath)).isEmpty());
        } catch (TimeoutException e) {
            throw new RuntimeException("Elements list is Empty");
        }
    }

    public void waitForElementIsPresent(final WebDriver driver, String xpath, int timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(d -> driver.findElement(By.xpath(xpath)).isDisplayed());
        } catch (TimeoutException e) {
            throw new RuntimeException("Element is not disable");
        }
    }

    public void waitForElementListSizeChanged(final WebDriver driver, String xpath, int expectedSize, int timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(d -> driver.findElements(By.xpath(xpath)).size() == expectedSize);
        } catch (TimeoutException e) {
            throw new RuntimeException("Element has the same size");
        }
    }

    public void submitModalForm(final WebDriver driver, List<String> fields, List<String> inputs) {
        IntStream.range(0, inputs.size()).forEach(i -> {
            driver.findElement(By.xpath(String.format("//label[text()='%s']/../input", fields.get(i)))).sendKeys(inputs.get(i));
        });
        driver.findElement(By.xpath(MODAL_XPATH + "//button[2]")).click();
    }

    public void clickNavBar(final WebDriver driver, Navbar name) {
        driver.findElement(By.xpath(String.format("//li[contains(@class, 'nav-item')]/a[text()='%s']", name.getName()))).click();
        switch (name) {
            case CART:
                waitForElementIsPresent(driver, PRODUCTS_CART_XPATH, 5);
                break;
            case HOME:
                waitForElementIsPresent(driver, PRODUCTS_XPATH, 5);
                break;
        }
    }
}
