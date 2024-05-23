package web;

import com.zebrunner.carina.core.IAbstractTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import web.components.*;
import web.pages.CartPage;
import web.pages.HomePage;
import web.pages.ProductCardPage;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.testng.Assert.*;
import static web.enums.Category.*;
import static web.enums.Navbar.CART;
import static web.enums.Navbar.HOME;
import static web.utils.ScreenshotUtil.takeScreenshot;

@Listeners(WebTest.class)
public class WebTest implements IAbstractTest, ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
        ProductCardPage productCardPage = product.clickCard();
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
        ProductCardPage productCardPage = product.clickCard();
        productCardPage.clickCartButton();
        NavBarMenuOption navMenu = homePage.getNavMenuComponent();
        navMenu.clickNavBar(CART);
        CartPage cartPage = new CartPage(getDriver());
        List<String> columns = new ArrayList<>(Arrays.asList("Pic", "Title", "Price", "x"));
        assertEquals(cartPage.getTableItems(), columns, "Columns are not equal");
        assertEquals(cartPage.getProductsSize(), 1, "Sizes are not equal");
        assertEquals(cartPage.getImageAttribute(1), image, "Images are not equal");
        assertEquals(cartPage.getProductName(1), name, "Names are not equal");
        assertEquals(cartPage.getProductPrice(1), price, "Prices are not equal");
        assertTrue(cartPage.isDeleteButtonPresent(0), "Delete button is not present");
        assertEquals(cartPage.getTotalPrice(), price, "Prices are not equal");
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        assertEquals(placeOrder.getTotalPrice(), price, "Prices are not equal");
    }

    @Test()
    public void testDeleteProductFromCart() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        List<Product> productList = homePage.getProducts();
        selectProduct(productList);
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
        selectProduct(productList);
        CartPage cartPage = new CartPage(getDriver());
        NavBarMenuOption navMenu = homePage.getNavMenuComponent();
        navMenu.clickNavBar(HOME);
        selectProduct(productList);
        String price = cartPage.getProductPrice(1);
        String price_ = cartPage.getProductPrice(2);
        assertEquals(cartPage.getProductsSize(), 2, "Sizes are not equal");
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
        selectProduct(productList);
        CartPage cartPage = new CartPage(getDriver());
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        placeOrder.submitPlaceOrderForm("Test", "Test", "Test", "Test", "Test", "Test");
        ConfirmOrderPopup confirmOrderPopup = cartPage.getConfirmOrderPopup();
        assertEquals(confirmOrderPopup.getTitle(), "Thank you for your purchase!", "Prices are not equal");
    }

    @Test()
    public void verifyPlaceOrderForm() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        NavBarMenuOption navMenu = homePage.getNavMenuComponent();
        navMenu.clickNavBar(CART);
        CartPage cartPage = new CartPage(getDriver());
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        assertEquals(placeOrder.getTotalPrice(), "Total:", "Total price are not equal");
        assertEquals(placeOrder.getTitle(), "Place order", "Titles are not equal");
        assertEquals(placeOrder.getName(), "", "Names are not empty");
        assertEquals(placeOrder.getCountry(), "", "Countries are not empty");
        assertEquals(placeOrder.getCity(), "", "Cities are not empty");
        assertEquals(placeOrder.getCreditCard(), "", "Credit Cards are not empty");
        assertEquals(placeOrder.getMonth(), "", "Months are not empty");
        assertEquals(placeOrder.getYear(), "", "Years are not empty");
        assertTrue(placeOrder.isCloseButtonPresent(), "Close buttons are not present");
        assertTrue(placeOrder.isPurchaseButtonPresent(), "Purchase buttons are not present");
    }

    @Test()
    public void verifyFooter() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        FooterComponent footerComponent = homePage.getFooterComponent();
        assertTrue(footerComponent.isAboutUsTitlePresent(), "Title 'About Us' is not present");
        assertTrue(footerComponent.isGetInTouchTitlePresent(), "Title 'Get in touch' is not present");
        assertEquals(footerComponent.getAboutUsText(), "We believe performance needs to be validated at every stage of the software " +
                "development cycle and our open source compatible, massively scalable platform " +
                "makes that a reality.", "Texts are not equal");
        assertTrue(footerComponent.isAddressPresent(), "Address is not present");
        assertTrue(footerComponent.isPhoneNumberPresent(), "Phone is not present");
        assertTrue(footerComponent.isEmailPresent(), "Email is not present");
        assertEquals(footerComponent.getAddressText(), "Address: 2390 El Camino Real", "Addresses are not equal");
        assertEquals(footerComponent.getPhoneNumber(), "Phone: +440 123456", "Phones are not equal");
        assertEquals(footerComponent.getEmail(), "Email: demo@blazemeter.com", "Emails are not equal");
        assertEquals(footerComponent.getFooterTextWithLogo(), "PRODUCT STORE", "Texts are not equal");
    }

    public void selectProduct(List<Product> productList) {
        HomePage homePage = new HomePage(getDriver());
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        ProductCardPage productCardPage = product.clickCard();
        productCardPage.clickCartButton();
        NavBarMenuOption navMenu = homePage.getNavMenuComponent();
        navMenu.clickNavBar(CART);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.debug("CarinaListener->onTestFailure");
        String methodName = result.getName().toString().trim() + result.id();
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                takeScreenshot(methodName, getDriver());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}