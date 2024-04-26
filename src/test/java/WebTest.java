import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import web.components.ConfirmOrderPopup;
import web.components.PlaceOrderPopup;
import web.components.Product;
import web.pages.CartPage;
import web.pages.HomePage;
import web.pages.ProductCardPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.testng.Assert.*;
import static web.enums.Category.*;
import static web.enums.Navbar.*;

public class WebTest extends AbstractTest {

    @Test()
    public void testProductData() {
        WebDriver driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);
        List<Product> productList = homePage.getProducts();
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        assertTrue(product.getName().matches("[a-zA-Z0-9]+\\s[a-zA-Z0-9]+\\s[a-zA-Z0-9][a-zA-Z0-9_.-]*$"), "Name does not match" + product.getName());
        assertTrue(product.getCardText().length() >=170, "Length does not match");
        assertTrue(product.getImageAttribute().matches("https:\\/\\/demoblaze\\.com\\/imgs\\/.*.jpg"), "Image does not match");
        assertTrue(product.getPrice().matches("[0-9]{3,4}"), "Price does not match");
        verifyFooter(homePage, driver);
        driver.quit();
    }

    @Test()
    public void testProductPage() {
        WebDriver driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);
        List<Product> productList = homePage.getProducts();
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        String image = product.getImageAttribute();
        String name = product.getName();
        String price = product.getPrice();
        String text = product.getCardText();
        ProductCardPage productCardPage = homePage.clickCard(randomProductIndex);
        assertEquals(productCardPage.getName(), name, "Names are not equal");
        assertEquals(productCardPage.getProductPrice(), price, "Prices are not equal");
        assertEquals(productCardPage.getImageAttribute(), image, "Image's attributes are not equal");
        assertTrue(productCardPage.getCardText().contains(text), "Texts are not equal");
        driver.quit();
    }

    @Test()
    public void testCategories() {
        WebDriver driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);
        List<Product> products = homePage.getProducts();
        homePage.selectCategory(PHONES.getName());
        assertNotEquals(homePage.getProducts(), products, "Products are the same!");
        List<Product> phones = homePage.getProducts();
        homePage.selectCategory(LAPTOPS.getName());
        List<Product> laptops = homePage.getProducts();
        assertNotEquals(laptops, phones, "Products are the same!");
        homePage.selectCategory(MONITORS.getName());
        List<Product> monitors = homePage.getProducts();
        assertNotEquals(monitors, laptops, "Products are the same!");
        driver.quit();
    }

    @Test()
    public void testShoppingCart() {
        WebDriver driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);
        List<Product> productList = homePage.getProducts();
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        String image = product.getImageAttribute();
        String name = product.getName();
        String price = product.getPrice();
        ProductCardPage productCardPage = homePage.clickCard(randomProductIndex);
        productCardPage.clickCartButton();
        driver.navigate().back();
        driver.navigate().back();
        homePage.clickNavBar(driver, CART);
        CartPage cartPage = new CartPage(driver);
        List<String> columns = new ArrayList<>(Arrays.asList("Pic", "Title", "Price", "x"));
        assertEquals(cartPage.getTableItems(), columns, "Columns are not equal");
        assertEquals(cartPage.getProductsSize(), 1, "Sizes are not equal");
        assertEquals(cartPage.getImageAttribute().get(0), image, "Images are not equal");
        assertEquals(cartPage.getTableValues(1).get(1), name, "Names are not equal");
        assertEquals(cartPage.getTableValues(1).get(2), price, "Prices are not equal");
        assertEquals(cartPage.getTableValues(1).get(3), "Delete", "Buttoms are not equal");
        assertEquals(cartPage.getTotalPrice(), price, "Prices are not equal");
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        assertEquals(placeOrder.getTotalPrice(), price, "Prices are not equal");
        driver.quit();
    }

    @Test()
    public void testDeleteProductFromCart() {
        WebDriver driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);
        List<Product> productList = homePage.getProducts();
        selectProduct(productList, homePage, driver);
        CartPage cartPage = new CartPage(driver);
        assertEquals(cartPage.getProductsSize(), 1, "Sizes are not equal");
        cartPage.clickDeleteProduct(0);
        assertEquals(cartPage.getProductsSize(), 0, "Sizes are not equal");
        assertEquals(cartPage.getTotalPrice(), "", "Prices are not equal");
        driver.quit();
    }

    @Test()
    public void testAddSomeProducts() {
        WebDriver driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);
        List<Product> productList = homePage.getProducts();
        selectProduct(productList, homePage, driver);
        CartPage cartPage = new CartPage(driver);
        cartPage.clickNavBar(driver, HOME);
        selectProduct(productList, homePage, driver);
        assertEquals(cartPage.getProductsSize(), 2, "Sizes are not equal");
        String price = cartPage.getTableValues(1).get(2);
        String price_ = cartPage.getTableValues(2).get(2);
        String summ = String.valueOf(Integer.parseInt(price) + Integer.parseInt(price_));
        assertEquals(cartPage.getTotalPrice(), summ, "Prices are not equal");
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        assertEquals(placeOrder.getTotalPrice(), summ, "Prices are not equal");
        driver.quit();
    }

    @Test()
    public void testPurchaseComplete() {
        WebDriver driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);
        List<Product> productList = homePage.getProducts();
        selectProduct(productList, homePage, driver);
        CartPage cartPage = new CartPage(driver);
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        List<String> fields = new ArrayList<>(Arrays.asList("Name:", "Country:", "City:", "Credit card:", "Month:", "Year:"));
        List<String> inputs = new ArrayList<>(Arrays.asList("Test", "Test", "Test", "Test", "Test", "Test"));
        placeOrder.submitModalForm(fields, inputs);
        ConfirmOrderPopup confirmOrderPopup = cartPage.getConfirmOrderPopup();
        assertEquals(confirmOrderPopup.getTitle(), "Thank you for your purchase!", "Prices are not equal");
        driver.quit();
    }

    @Test()
    public void verifyModalForm() {
        WebDriver driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);
        List<Product> productList = homePage.getProducts();
        selectProduct(productList, homePage,driver);
        CartPage cartPage = new CartPage(driver);
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        String title = "Place order";
        List<String> fieldNames = Arrays.asList("Name:", "Country:", "City:", "Credit card:", "Month:", "Year:", "");
        List<String> buttonsNames = Arrays.asList("Close", "Purchase");
        verifyModal(placeOrder, title, fieldNames, buttonsNames);
    }

    public void selectProduct(List<Product> productList, HomePage homePage, WebDriver driver) {
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        ProductCardPage productCardPage = homePage.clickCard(randomProductIndex);
        productCardPage.clickCartButton();
        driver.navigate().back();
        driver.navigate().back();
        homePage.clickNavBar(driver, CART);
    }
}