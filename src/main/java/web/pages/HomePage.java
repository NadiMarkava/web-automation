package web.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import web.components.Product;

import java.util.List;
import java.util.stream.Collectors;

import static web.pages.ProductCardPage.addToCartButton;

public class HomePage extends BasePage {

    public static final Logger LOGGER = LogManager.getLogger(HomePage.class.getName());
    protected static String PRODUCTS_XPATH = "//div[@class='card h-100']";

    public HomePage(WebDriver driver) {
        super(driver);
        driver.get("https://demoblaze.com/");
        waitForElementsListNotEmpty(driver, PRODUCTS_XPATH, 5);
    }

    public List<Product> getProducts() {
        List<Product> products = driver.findElements(By.xpath(PRODUCTS_XPATH))
                .stream()
                .map(e -> new Product(e))
                .collect(Collectors.toList());
        printProductTitles(products.stream().map(Product::getName).collect(Collectors.toList()));
        LOGGER.info("laallalala");
        return products;
    }

    public List<String> getNavItems() {
        return driver.findElements(By.xpath("//li[contains(@class, 'nav-item')]/a[not(contains(@style,'display:none'))]"))
                .stream()
                .map(e -> e.getText())
                .collect(Collectors.toList());
    }

    public void selectCategory(String categoryName) {
        driver.findElement(By.xpath(String.format("//a[text()='CATEGORIES']/../a[text()='%s']", categoryName))).click();
    }

    public ProductCardPage clickCard(int cardNumber) {
        driver.findElements(By.xpath(".//h4/a")).get(cardNumber).click();
        waitForElementIsPresent(driver, addToCartButton, 5);
        return new ProductCardPage(driver);
    }
}
