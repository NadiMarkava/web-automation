package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductCardPage extends BasePage {

    private final By productImage = By.xpath("//div[@class='product-image']//img");
    private final By productName = By.className("name");
    private final By productPrice = By.className("price-container");
    private final By productText = By.xpath("//descendant::strong[text()='Product description']/../p");
    private final By addToCartButton = By.xpath("//a[text()='Add to cart']");

    public ProductCardPage(WebDriver driver) {
        super(driver);
    }

    public String getName() {
        return driver.findElement(productName).getText();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice)
                .getText()
                .replace("$", "")
                .replace(" *includes tax", "");
    }

    public String getCardText() {
        return driver.findElement(productText).getText();
    }

    public String getImageAttribute() {
        return driver.findElement(productImage).getAttribute("src");
    }

    public void clickCartButton() {
        driver.findElement(addToCartButton).click();
    }
}
