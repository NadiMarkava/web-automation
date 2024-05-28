package web.pages;

import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.components.Product;
import web.enums.Category;

import java.util.List;

public class HomePage extends DemoBlazeAbstractPage {

    @FindBy(css = "div[class='carousel-inner']")
    private ExtendedWebElement carousel;

    @FindBy(xpath = "//div[@class='carousel-item active']/img")
    private ExtendedWebElement carouselActive;

    @FindBy(css = "a[class='carousel-control-next']")
    private ExtendedWebElement carouselNext;

    @FindBy(css = "a[class='carousel-control-prev']")
    private ExtendedWebElement carouselBack;

    @FindBy(xpath = "//div[contains(@class, 'carousel-item')]")
    private List<ExtendedWebElement> carouselItems;

    @FindBy(xpath = "//div[@class='card h-100']")
    private List<Product> products;

    @FindBy(xpath = "//a[text()='CATEGORIES']/../a[text()='%s']")
    private ExtendedWebElement categoryLink;

    public HomePage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL(R.CONFIG.get("url"));
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(carousel);
    }

    public void clickCarouselNext() {
        carouselNext.click();
        pause(0.5);
    }

    public void clickCarouselBack() {
        carouselBack.click();
    }

    public boolean isCarouselImagePresent() {
        return carouselActive.getAttribute("src").contains(".jpg");
    }

    public String getCarouselImageAttribute() {
        return carouselActive.getAttribute("src");
    }

    public String getCarouselAttribute() {
        return carouselActive.getAttribute("alt");
    }

    public int getCarouselSize() {
        return carouselItems.size();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void waitUntilProductsLoaded() {
        waitUntil((e -> !products.isEmpty()), 5);
    }

    public void clickCategory(Category categoryName) {
        categoryLink.format(categoryName.getName()).click();
    }
}