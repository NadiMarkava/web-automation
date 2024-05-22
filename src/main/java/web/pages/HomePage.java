package web.pages;

import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.components.Product;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends MyAbstractPage {

    public static final Logger LOGGER = LogManager.getLogger(HomePage.class.getName());
    protected final static String PRODUCTS_XPATH = "//div[@class='card h-100']";
    @FindBy(xpath = PRODUCTS_XPATH)
    protected static List<Product> products;
    @FindBy(xpath = "//li[contains(@class, 'nav-item')]/a[not(contains(@style,'display:none'))]")
    private List<ExtendedWebElement> navItems;
    @FindBy(xpath = "//a[text()='Add to cart']")
    private ExtendedWebElement addToCartButton;

    public HomePage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL(R.CONFIG.get("url"));
    }

    public List<Product> getProducts() {
        waitForUIObjectListIsNotEmpty(driver, products, 10);
        return products;
    }

    public List<String> getNavItems() {
        return navItems
                .stream()
                .map(e -> e.getText())
                .collect(Collectors.toList());
    }

    public void selectCategory(String categoryName) {
        findExtendedWebElement(By.xpath(String.format("//a[text()='CATEGORIES']/../a[text()='%s']", categoryName))).click();
    }

    public ProductCardPage clickCard(int cardNumber) {
        findExtendedWebElements(By.xpath(".//h4/a")).get(cardNumber).click();
        waitForElementIsPresent(driver, addToCartButton, 5);
        return new ProductCardPage(driver);
    }
}