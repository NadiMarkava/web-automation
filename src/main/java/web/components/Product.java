package web.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Product extends BaseComponent {

    private By productImage = By.xpath(".//a/img");
    private By productName = By.className("card-title");
    private By productPrice = By.xpath(".//h5");
    private By productText = By.id("article");

    public Product(WebElement root) {
        super(root);
    }

    public String getName() {
        return root.findElement(productName).getText();
    }

    public String getCardText() {
        return root.findElement(productText).getText();
    }

    public String getImageAttribute() {
        return root.findElement(productImage).getAttribute("src");
    }

    public String getPrice() {
        return root.findElement(productPrice)
                .getText()
                .replace("$", "");
    }
}
