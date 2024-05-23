package web.pages;

import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.components.Product;

import java.util.List;

public class HomePage extends MyAbstractPage {

    public static final Logger LOGGER = LogManager.getLogger(HomePage.class.getName());

    @FindBy(xpath = "//div[@class='card h-100']")
    private List<Product> products;

    @FindBy(xpath = "//a[text()='CATEGORIES']/../a[text()='%s']")
    private ExtendedWebElement categoryLink;

    public HomePage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL(R.CONFIG.get("url"));
    }

    public List<Product> getProducts() {
        waitUntil((e ->!products.isEmpty()), 10);
        return products;
    }

    public void selectCategory(String categoryName) {
        categoryLink.format(categoryName).click();
    }
}