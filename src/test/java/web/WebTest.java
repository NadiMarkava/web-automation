package web;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.testng.annotations.Test;
import web.components.*;
import web.pages.CartPage;
import web.pages.HomePage;
import web.pages.ProductDetailCardPage;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.testng.Assert.*;
import static web.enums.Category.*;
import static web.enums.NavBarMenuOption.*;


public class WebTest extends BaseDemoBlazeTest {

    @Test()
    public void verifyCarouselTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        List<String> sliders = Arrays.asList("First", "Second", "Third");
        homePage.clickCarouselBack();
        String carouselPreviousImageAttribute = homePage.getCarouselImageAttribute();
        for (int i = 0; i < homePage.getCarouselSize(); i++) {
            homePage.clickCarouselNext();
            assertTrue(homePage.isCarouselImagePresent(), "Image is not present");
            assertEquals(homePage.getCarouselAttribute(), sliders.get(i) + " slide", "Number of slider is not equal");
            assertNotEquals(carouselPreviousImageAttribute, homePage.getCarouselImageAttribute(), "Images are equal");
            carouselPreviousImageAttribute = homePage.getCarouselImageAttribute();
        }
        homePage.clickCarouselNext();
        for (int i = homePage.getCarouselSize() - 1; i > 0; i--) {
            homePage.clickCarouselBack();
            assertEquals(homePage.getCarouselAttribute(), sliders.get(i) + " slide", "Number of slider is not equal");
        }
    }

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

    @Test()
    public void verifyCategoriesTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.waitUntilProductsLoaded();
        List<String> allProducts = homePage.getProducts().stream().map(p -> p.getNameText()).collect(Collectors.toList());
        homePage.clickCategory(PHONES);
        List<String> phoneProducts = homePage.getProducts().stream().map(p -> p.getNameText()).collect(Collectors.toList());
        assertNotEquals(phoneProducts, allProducts, "Products are the same!");
        homePage.clickCategory(LAPTOPS);
        List<String> laptopProducts = homePage.getProducts().stream().map(p -> p.getNameText()).collect(Collectors.toList());
        assertNotEquals(laptopProducts, phoneProducts, "Products are the same!");
        homePage.clickCategory(MONITORS);
        List<String> monitorProducts = homePage.getProducts().stream().map(p -> p.getNameText()).collect(Collectors.toList());
        assertNotEquals(monitorProducts, laptopProducts, "Products are the same!");
    }

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
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
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
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        assertEquals(placeOrder.getTotalPriceText(), summ, "Prices are not equal");
    }

    @Test()
    public void verifyConfirmOrderTest() {
        String name = "Test";
        String creditCard = "Test";
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.waitUntilProductsLoaded();
        List<Product> productList = homePage.getProducts();
        selectRandomProduct(productList);
        homePage.getNavBar().clickNavBarMenuOption(CART);
        CartPage cartPage = new CartPage(getDriver());
        cartPage.waitUntilProductsLoaded();
        String price = cartPage.getProductPriceText(1);
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
        ConfirmOrderPopup confirmOrderPopup = placeOrder.submitPlaceOrderForm(name, "Test", "Test", creditCard, "Test", "Test");
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

    @Test()
    public void verifyPlaceOrderFormTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(CART);
        CartPage cartPage = new CartPage(getDriver());
        PlaceOrderPopup placeOrder = cartPage.clickPlaceOrderButton();
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
    public void verifyFooterTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Footer footerComponent = homePage.getFooter();
        assertTrue(footerComponent.isAboutUsTitlePresent(), "Title 'About Us' is not present");
        assertTrue(footerComponent.isGetInTouchTitlePresent(), "Title 'Get in touch' is not present");
        assertEquals(footerComponent.getAboutUsText(), "We believe performance needs to be validated at every stage of the software " +
                "development cycle and our open source compatible, massively scalable platform " +
                "makes that a reality.", "Texts are not equal");
        assertTrue(footerComponent.isAddressPresent(), "Address is not present");
        assertTrue(footerComponent.isPhoneNumberPresent(), "Phone is not present");
        assertTrue(footerComponent.isEmailPresent(), "Email is not present");
        assertEquals(footerComponent.getAddressText(), "Address: 2390 El Camino Real", "Addresses are not equal");
        assertEquals(footerComponent.getPhoneNumberText(), "Phone: +440 123456", "Phones are not equal");
        assertEquals(footerComponent.getEmailText(), "Email: demo@blazemeter.com", "Emails are not equal");
        assertEquals(footerComponent.getFooterSectionWithLogoText(), "PRODUCT STORE", "Texts are not equal");
    }

    @Test()
    public void verifySignUpTest() {
        String randomText = UUID.randomUUID()
                .toString()
                .substring(0, 5);
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(SIGN_UP);
        SignUp signUp = new SignUp(getDriver());
        assertTrue(signUp.isUserNameFieldPresent(), "User name is not present");
        assertTrue(signUp.isPasswordPresent(), "Password is not present");
        signUp.signUp(randomText, randomText);
        Alert alert = getDriver().switchTo().alert();
        assertEquals(alert.getText(), "Sign up successful.", "Texts are not equal");
        alert.accept();
        assertFalse(homePage.isModalPresent(), "Modal is present");
        homePage.getNavBar().clickNavBarMenuOption(SIGN_UP);
        signUp.clickSignUpButton();
        assertEquals(alert.getText(), "This user already exist.", "Texts are not equal");
        alert.accept();
        assertTrue(homePage.isModalPresent(), "Modal is present");
    }

    @Test()
    public void verifyLogInTest() {
        String userName = "test";
        String password = "test";
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(LOG_IN);
        LogIn logIn = new LogIn(getDriver());
        assertTrue(logIn.isUserNameFieldPresent(), "User name is not present");
        assertTrue(logIn.isPasswordPresent(), "Password is not present");
        logIn.logIn(userName, password);
        assertFalse(homePage.getNavBar().isNavItemPresent(LOG_IN), "Log in is present");
        assertFalse(homePage.getNavBar().isNavItemPresent(SIGN_UP), "Sign up is present");
        assertTrue(homePage.getNavBar().isNavItemPresent(WELCOME), "Welcome is not present");
        assertTrue(homePage.getNavBar().isNavItemPresent(LOG_OUT), "Log out is not present");
        homePage.waitUntilProductsLoaded();
    }

    @Test()
    public void verifyLogInWithErrorTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(LOG_IN);
        LogIn logIn = new LogIn(getDriver());
        logIn.logIn("teeest", "test");
        Alert alert = getDriver().switchTo().alert();
        assertEquals(alert.getText(), "User does not exist.", "Texts are not equal");
        alert.accept();
        logIn.logIn("test", "11111");
        assertEquals(alert.getText(), "Wrong password.", "Texts are not equal");
        alert.accept();
        logIn.logIn("", "");
        assertEquals(alert.getText(), "Please fill out Username and Password.", "Texts are not equal");
    }

    @Test()
    public void verifyCartAfterLogOutTest() {
        String userName = "test";
        String password = "test";
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(LOG_IN);
        LogIn logIn = new LogIn(getDriver());
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

    @Test()
    public void verifyContactTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(CONTACT);
        Contact contact = new Contact(getDriver());
        assertTrue(homePage.isModalPresent(), "Modal is present");
        assertTrue(contact.isEmailFieldPresent(), "Email is not present");
        assertTrue(contact.isNameFieldPresent(), "Name is not present");
        assertTrue(contact.isMessageFieldPresent(), "Message is not present");
        contact.sendMessage("Test", "Test", "Test");
        assertFalse(homePage.isModalPresent(), "Modal is present");
    }

    public void selectRandomProduct(List<Product> productList) {
        int randomProductIndex = new Random().nextInt(productList.size());
        Product product = productList.get(randomProductIndex);
        ProductDetailCardPage productCardPage = product.clickProductTitle();
        productCardPage.clickAddToCartButton();
    }
}