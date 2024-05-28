package web;

import org.testng.annotations.Test;
import web.components.Product;
import web.pages.HomePage;
import web.pages.ProductDetailCardPage;

import java.util.List;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ProductsTest extends BaseDemoBlazeTest {

    @Test()
    public void verifyProductDataTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.waitUntilProductsLoaded();
        List<Product> productList = homePage.getProducts();
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        assertTrue(product.getNameText().matches("[a-zA-Z0-9]+\\s[a-zA-Z0-9]+\\s[a-zA-Z0-9][a-zA-Z0-9_.-]*$"), "Name does not match" + product.getNameText());
        assertTrue(product.getCardText().length() >= 170, "Length does not match");
        assertTrue(product.getImageAttribute().matches("https:\\/\\/demoblaze\\.com\\/imgs\\/.*.jpg"), "Image does not match");
        assertTrue(product.getPriceText().matches("[0-9]{3,4}"), "Price does not match");
    }

    @Test()
    public void verifyProductPageTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.waitUntilProductsLoaded();
        List<Product> productList = homePage.getProducts();
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        String image = product.getImageAttribute();
        String name = product.getNameText();
        String price = product.getPriceText();
        String text = product.getCardText();
        ProductDetailCardPage productCardPage = product.clickProductTitle();
        assertEquals(productCardPage.getNameText(), name, "Names are not equal");
        assertEquals(productCardPage.getProductPriceText(), price, "Prices are not equal");
        assertEquals(productCardPage.getImageAttribute(), image, "Image's attributes are not equal");
        assertTrue(productCardPage.getCardDescription().contains(text), "Texts are not equal");
    }
}