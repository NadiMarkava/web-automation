package web;

import com.zebrunner.agent.core.annotation.TestCaseKey;
import org.openqa.selenium.Alert;
import org.testng.annotations.Test;
import web.components.Footer;
import web.pages.ContactPage;
import web.pages.HomePage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;
import static web.enums.AlertMessage.MESSAGE;
import static web.enums.Category.*;
import static web.enums.NavBarMenuOption.CONTACT;

public class HomeTest extends BaseDemoBlazeTest {

    @Test()
    @TestCaseKey("ANDK-328")
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
        for (int i = homePage.getCarouselSize() - 1; i >= 0; i--) {
            homePage.clickCarouselBack();
            assertEquals(homePage.getCarouselAttribute(), sliders.get(i) + " slide", "Number of slider is not equal");
        }

    }

    @Test()
    @TestCaseKey("ANDK-277")
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
    @TestCaseKey("ANDK-329")
    public void verifyContactTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(CONTACT);
        ContactPage contact = new ContactPage(getDriver());
        assertTrue(contact.isEmailFieldPresent(), "Email is not present");
        assertTrue(contact.isNameFieldPresent(), "Name is not present");
        assertTrue(contact.isMessageFieldPresent(), "Message is not present");
        assertTrue(contact.isCloseButtonPresent(), "Close button is not present");
        assertTrue(contact.isSendMessageButtonPresent(), "Send message button is not present");
        contact.sendMessage("Test", "Test", "Test");
        Alert alert = getDriver().switchTo().alert();
        assertEquals(alert.getText(), MESSAGE.getText(), "Texts are not equal");
        alert.accept();
        assertFalse(homePage.isModalPresent(), "Modal is present");
    }

    @Test()
    @TestCaseKey("ANDK-330")
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
}
