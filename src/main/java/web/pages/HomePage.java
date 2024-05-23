package web.pages;

import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.components.Product;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends MyAbstractPage {

    public static final Logger LOGGER = LogManager.getLogger(HomePage.class.getName());

    @FindBy(xpath = "//div[@class='card h-100']")
    private List<Product> products;

    @FindBy(xpath = "//a[text()='CATEGORIES']/../a[text()='%s']")
    private ExtendedWebElement category;

    @FindBy(xpath = ".//h4/a")
    private List<ExtendedWebElement> cardLinks;

    @FindBy(xpath = "//a[text()='Add to cart']")
    private ExtendedWebElement addToCartButton;

    public HomePage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL(R.CONFIG.get("url"));
    }

    public List<Product> getProducts() {
        waitUntil((e ->!products.isEmpty()), 10);
        return products;
    }

    public void selectCategory(String categoryName) {
        category.format(categoryName).click();
    }

    public ProductCardPage clickCard(int cardNumber) {
        cardLinks.get(cardNumber).click();
        waitUntil(e -> addToCartButton.isElementPresent(), 5);
        return new ProductCardPage(driver);
    }
}