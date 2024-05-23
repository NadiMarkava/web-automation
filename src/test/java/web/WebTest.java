package web;

import org.openqa.selenium.WebDriver;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.testng.Assert.*;
import static web.enums.Category.*;
import static web.enums.Navbar.CART;
import static web.enums.Navbar.HOME;


public class WebTest extends AbstractTest {

    public static final Logger LOGGER = Logger.getLogger(String.valueOf(WebTest.class));

    @Test()
    public void testProductData() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        List<Product> productList = homePage.getProducts();
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        assertTrue(product.getName().matches("[a-zA-Z0-9]+\\s[a-zA-Z0-9]+\\s[a-zA-Z0-9][a-zA-Z0-9_.-]*$"), "Name does not match" + product.getName());
        assertTrue(product.getCardText().length() >= 170, "Length does not match");
        assertTrue(product.getImageAttribute().matches("https:\\/\\/demoblaze\\.com\\/imgs\\/.*.jpg"), "Image does not match");
        assertTrue(product.getPrice().matches("[0-9]{3,4}"), "Price does not match");
        verifyFooter(homePage);
    }

    @Test()
    public void testProductPage() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
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
    }

    @Test()
    public void testCategories() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        List<String> allProducts = homePage.getProducts().stream().map(p->p.getName()).collect(Collectors.toList());
        homePage.selectCategory(PHONES.getName());
        List<String> phoneProducts = homePage.getProducts().stream().map(p->p.getName()).collect(Collectors.toList());
        assertNotEquals(phoneProducts, allProducts, "Products are the same!");
        homePage.selectCategory(LAPTOPS.getName());
        List<String> laptopProducts = homePage.getProducts().stream().map(p->p.getName()).collect(Collectors.toList());
        assertNotEquals(laptopProducts, phoneProducts, "Products are the same!");
        homePage.selectCategory(MONITORS.getName());
        List<String> monitorProducts = homePage.getProducts().stream().map(p->p.getName()).collect(Collectors.toList());
        assertNotEquals(monitorProducts, laptopProducts, "Products are the same!");
    }

    @Test()
    public void testShoppingCart() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        List<Product> productList = homePage.getProducts();
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        String image = product.getImageAttribute();
        String name = product.getName();
        String price = product.getPrice();
        ProductCardPage productCardPage = homePage.clickCard(randomProductIndex);
        productCardPage.clickCartButton();
        homePage.clickNavBar(getDriver(), CART);
        CartPage cartPage = new CartPage(getDriver());
        List<String> columns = new ArrayList<>(Arrays.asList("Pic", "Title", "Price", "x"));
        assertEquals(cartPage.getTableItems(), columns, "Columns are not equal");
        assertEquals(cartPage.getProductsSize(), 1, "Sizes are not equal");
        assertEquals(cartPage.getImageAttribute(1), image, "Images are not equal");
        assertEquals(cartPage.getProductName(1), name, "Names are not equal");
        assertEquals(cartPage.getProductPrice(1), price, "Prices are not equal");
        assertTrue(cartPage.isDeleteButtonPresent(1), "Delete button is not present");
        assertEquals(cartPage.getTotalPrice(), price, "Prices are not equal");
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        assertEquals(placeOrder.getTotalPrice(), price, "Prices are not equal");
    }

    @Test()
    public void testDeleteProductFromCart() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        List<Product> productList = homePage.getProducts();
        selectProduct(productList, homePage, getDriver());
        CartPage cartPage = new CartPage(getDriver());
        assertEquals(cartPage.getProductsSize(), 1, "Sizes are not equal");
        cartPage.clickDeleteProduct(0);
        assertEquals(cartPage.getProductsSize(), 0, "Sizes are not equal");
        assertEquals(cartPage.getTotalPrice(), "", "Prices are not equal");
    }

    @Test()
    public void testAddSomeProducts() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        List<Product> productList = homePage.getProducts();
        selectProduct(productList, homePage, getDriver());
        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickNavBar(getDriver(), HOME);
        selectProduct(productList, homePage, getDriver());
        assertEquals(cartPage.getProductsSize(), 2, "Sizes are not equal");
        String price = cartPage.getProductPrice(1);
        String price_ = cartPage.getProductPrice(2);
        String summ = String.valueOf(Integer.parseInt(price) + Integer.parseInt(price_));
        assertEquals(cartPage.getTotalPrice(), summ, "Prices are not equal");
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        assertEquals(placeOrder.getTotalPrice(), summ, "Prices are not equal");
    }

    @Test()
    public void testPurchaseComplete() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        List<Product> productList = homePage.getProducts();
        selectProduct(productList, homePage, getDriver());
        CartPage cartPage = new CartPage(getDriver());
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        List<String> fields = new ArrayList<>(Arrays.asList("Name:", "Country:", "City:", "Credit card:", "Month:", "Year:"));
        List<String> inputs = new ArrayList<>(Arrays.asList("Test", "Test", "Test", "Test", "Test", "Test"));
        placeOrder.submitModalForm(fields, inputs);
        ConfirmOrderPopup confirmOrderPopup = cartPage.getConfirmOrderPopup();
        assertEquals(confirmOrderPopup.getTitle(), "Thank you for your purchase!", "Prices are not equal");
    }

    @Test()
    public void verifyModalForm() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        List<Product> productList = homePage.getProducts();
        selectProduct(productList, homePage, getDriver());
        CartPage cartPage = new CartPage(getDriver());
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
        homePage.clickNavBar(driver, CART);
    }
}