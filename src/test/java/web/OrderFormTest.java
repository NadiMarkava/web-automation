package web;

import com.zebrunner.agent.core.annotation.TestCaseKey;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;
import web.components.Product;
import web.pages.*;

import java.util.List;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static web.enums.NavBarMenuOption.CART;


public class OrderFormTest extends BaseDemoBlazeTest {

    @Test()
    @TestCaseKey("ANDK-333")
    public void verifyPlaceOrderFormTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(CART);
        CartPage cartPage = new CartPage(getDriver());
        PlaceOrderPage placeOrder = cartPage.clickPlaceOrderButton();
        assertEquals(placeOrder.getTotalPriceText(), "Total:", "Total price are not equal");
        assertTrue(placeOrder.isNameFieldPresent(), "Name is not present");
        assertTrue(placeOrder.isCountryFieldPresent(), "Country is not present");
        assertTrue(placeOrder.isCityFieldPresent(), "City is not present");
        assertTrue(placeOrder.isCreditCardFieldPresent(), "Credit Card is not present");
        assertTrue(placeOrder.isMonthFieldPresent(), "Month is not present");
        assertTrue(placeOrder.isYearFieldPresent(), "Year is not present");
        assertTrue(placeOrder.isCloseButtonPresent(), "Close button is not present");
        assertTrue(placeOrder.isPurchaseButtonPresent(), "Purchase button is not present");
    }

    @Test()
    @TestCaseKey("ANDK-279")
    public void verifyConfirmOrderTest() {
        String name = "Test";
        String creditCard = "Test";
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.waitUntilProductsLoaded();
        List<Product> productList = homePage.getProducts();
        Product product = productList.get(new Random().nextInt(productList.size()));
        ProductDetailCardPage productCardPage = product.clickProductTitle();
        productCardPage.clickAddToCartButton();
        homePage.getNavBar().clickNavBarMenuOption(CART);
        CartPage cartPage = new CartPage(getDriver());
        cartPage.waitUntilProductsLoaded();
        String price = cartPage.getProductPriceText(1);
        PlaceOrderPage placeOrder = cartPage.clickPlaceOrderButton();
        ConfirmOrderPage confirmOrderPopup = placeOrder.submitPlaceOrderForm(name, "Test", "Test", creditCard, "Test", "Test");
        assertTrue(confirmOrderPopup.isSuccessIconPresent(), "Success icon is not present");
        String text = confirmOrderPopup.getConfirmText();
        String id = StringUtils.substringBetween(text, "Id: ", "\n");
        String amount = StringUtils.substringBetween(text, "Amount: ", "\n");
        String cardNumber = StringUtils.substringBetween(text, "Card Number: ", "\n");
        String cardName = StringUtils.substringBetween(text, "Name: ", "\n");
        String date = StringUtils.substringAfter(text, "Date: ");
        assertTrue(id.matches("[0-9]{7}"), "Id does not match");
        assertEquals(amount, price + " USD", "Prices are not equal");
        assertEquals(cardName, name, "Names are not equal");
        assertEquals(cardNumber, creditCard, "Credit cards are not equal");
        assertTrue(date.matches("[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4}"), "Date does not match");
        assertTrue(confirmOrderPopup.isOkButtonPresent(), "Success icon is not present");
    }
}