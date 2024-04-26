package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductCardPage extends BasePage {

    private By productImage = By.xpath("//div[@class='product-image']//img");
    private By productName = By.className("name");
    private By productPrice = By.className("price-container");
    private By productText = By.xpath("//descendant::strong[text()='Product description']/../p");
    protected static String addToCartButton = "//a[text()='Add to cart']";

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
        driver.findElement(By.xpath(addToCartButton)).click();
    }
}
