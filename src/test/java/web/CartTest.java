package web;

import org.testng.annotations.Test;
import web.components.Product;
import web.pages.*;

import java.util.List;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static web.enums.NavBarMenuOption.*;

public class CartTest extends BaseDemoBlazeTest {

    @Test()
    public void verifyShoppingCartTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.waitUntilProductsLoaded();
        List<Product> productList = homePage.getProducts();
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        String image = product.getImageAttribute();
        String name = product.getNameText();
        String price = product.getPriceText();
        ProductDetailCardPage productCardPage = product.clickProductTitle();
        productCardPage.clickAddToCartButton();
        homePage.getNavBar().clickNavBarMenuOption(CART);
        CartPage cartPage = new CartPage(getDriver());
        cartPage.waitUntilProductsLoaded();
        assertTrue(cartPage.isPicColumnPresent(), "Pic column is not present");
        assertTrue(cartPage.isTitleColumnPresent(), "Title column is not present");
        assertTrue(cartPage.isPriceColumnPresent(), "Price column is not present");
        assertTrue(cartPage.isDeleteColumnPresent(), "Delete column is not present");
        assertEquals(cartPage.getProductsSize(), 1, "Sizes are not equal");
        assertEquals(cartPage.getImageAttribute(1), image, "Images are not equal");
        assertEquals(cartPage.getProductNameText(1), name, "Names are not equal");
        assertEquals(cartPage.getProductPriceText(1), price, "Prices are not equal");
        assertTrue(cartPage.isDeleteButtonPresent(1), "Delete button is not present");
        assertEquals(cartPage.getTotalPriceText(), price, "Prices are not equal");
        PlaceOrderPage placeOrder = cartPage.clickPlaceOrderButton();
        assertEquals(placeOrder.getTotalPriceText(), price, "Prices are not equal");
    }

    @Test()
    public void verifyProductCanBeDeletedFromCartTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.waitUntilProductsLoaded();
        List<Product> productList = homePage.getProducts();
        selectRandomProduct(productList);
        homePage.getNavBar().clickNavBarMenuOption(CART);
        CartPage cartPage = new CartPage(getDriver());
        cartPage.waitUntilProductsLoaded();
        assertEquals(cartPage.getProductsSize(), 1, "Sizes are not equal");
        cartPage.clickDeleteButton(1);
        assertEquals(cartPage.getProductsSize(), 0, "Sizes are not equal");
        assertEquals(cartPage.getTotalPriceText(), "", "Prices are not equal");
    }

    @Test()
    public void verifyMultipleProductsCanBeAddedTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.waitUntilProductsLoaded();
        List<Product> productList = homePage.getProducts();
        selectRandomProduct(productList);
        homePage.getNavBar().clickNavBarMenuOption(HOME);
        selectRandomProduct(productList);
        homePage.getNavBar().clickNavBarMenuOption(CART);
        CartPage cartPage = new CartPage(getDriver());
        cartPage.waitUntilProductsLoaded();
        String price = cartPage.getProductPriceText(1);
        String price_ = cartPage.getProductPriceText(2);
        assertEquals(cartPage.getProductsSize(), 2, "Sizes are not equal");
        String summ = String.valueOf(Integer.parseInt(price) + Integer.parseInt(price_));
        assertEquals(cartPage.getTotalPriceText(), summ, "Prices are not equal");
        PlaceOrderPage placeOrder = cartPage.clickPlaceOrderButton();
        assertEquals(placeOrder.getTotalPriceText(), summ, "Prices are not equal");
    }

    @Test()
    public void verifyCartAfterLogOutTest() {
        String userName = "test";
        String password = "test";
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(LOG_IN);
        LoginPage logIn = new LoginPage(getDriver());
        logIn.logIn(userName, password);
        homePage.waitUntilProductsLoaded();
        List<Product> productList = homePage.getProducts();
        selectRandomProduct(productList);
        homePage.getNavBar().clickNavBarMenuOption(CART);
        CartPage cartPage = new CartPage(getDriver());
        cartPage.waitUntilProductsLoaded();
        assertEquals(cartPage.getProductsSize(), 1, "Sizes are not equal");
        String name = cartPage.getProductNameText(1);
        String price = cartPage.getProductPriceText(1);
        homePage.getNavBar().clickNavBarMenuOption(LOG_OUT);
        homePage.getNavBar().clickNavBarMenuOption(LOG_IN);
        logIn.logIn(userName, password);
        homePage.getNavBar().clickNavBarMenuOption(CART);
        cartPage.waitUntilProductsLoaded();
        assertEquals(cartPage.getProductsSize(), 1, "Sizes are not equal");
        assertEquals(cartPage.getProductNameText(1), name, "Names are not equal");
        assertEquals(cartPage.getProductPriceText(1), price, "Prices are not equal");
        cartPage.clickDeleteButton(1);
        assertEquals(cartPage.getProductsSize(), 0, "Sizes are not equal");
    }

    public void selectRandomProduct(List<Product> productList) {
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        ProductDetailCardPage productCardPage = product.clickProductTitle();
        productCardPage.clickAddToCartButton();
    }
}
