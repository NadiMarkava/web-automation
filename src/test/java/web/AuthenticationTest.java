package web;

import org.openqa.selenium.Alert;
import org.testng.annotations.Test;
import web.pages.HomePage;
import web.pages.LoginPage;
import web.pages.SignUpPage;

import java.util.UUID;

import static org.testng.Assert.*;
import static web.enums.AlertMessage.*;
import static web.enums.NavBarMenuOption.*;

public class AuthenticationTest extends BaseDemoBlazeTest {

    @Test()
    public void verifySignUpTest() {
        String randomText = UUID.randomUUID()
                .toString()
                .substring(0, 5);
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(SIGN_UP);
        SignUpPage signUp = new SignUpPage(getDriver());
        assertTrue(signUp.isUserNameFieldPresent(), "User name is not present");
        assertTrue(signUp.isPasswordPresent(), "Password is not present");
        assertTrue(signUp.isCloseButtonPresent(), "Close button is not present");
        assertTrue(signUp.isSignUpButtonPresent(), "Sign Up button is not present");
        signUp.signUp(randomText, randomText);
        Alert alert = getDriver().switchTo().alert();
        assertEquals(alert.getText(), SUCCESS_SIGN_UP.getText(), "Texts are not equal");
        alert.accept();
        assertFalse(homePage.isModalPresent(), "Modal is present");
    }

    @Test()
    public void verifySignUpInWithInvalidInputsTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(SIGN_UP);
        SignUpPage signUp = new SignUpPage(getDriver());
        signUp.signUp("Test", "Test");
        Alert alert = getDriver().switchTo().alert();
        assertEquals(alert.getText(), USER_EXISTS.getText(), "Texts are not equal");
        alert.accept();
        assertTrue(homePage.isModalPresent(), "Modal is not present");
        signUp.signUp("Test", "");
        assertEquals(alert.getText(), FILL_OUT_NAME_PASSWORD.getText(), "Texts are not equal");
        alert.accept();
        assertTrue(homePage.isModalPresent(), "Modal is not present");
    }

    @Test()
    public void verifyLogInTest() {
        String userName = "test";
        String password = "test";
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(LOG_IN);
        LoginPage logIn = new LoginPage(getDriver());
        assertTrue(logIn.isUserNameFieldPresent(), "User name is not present");
        assertTrue(logIn.isPasswordPresent(), "Password is not present");
        assertTrue(logIn.isCloseButtonPresent(), "Close button is not present");
        assertTrue(logIn.isLogInButtonPresent(), "Log in button is not present");
        logIn.logIn(userName, password);
        assertFalse(homePage.isModalPresent(), "Modal is present");
        assertFalse(homePage.getNavBar().isNavItemPresent(LOG_IN), "Log in is present");
        assertFalse(homePage.getNavBar().isNavItemPresent(SIGN_UP), "Sign up is present");
        assertTrue(homePage.getNavBar().isNavItemPresent(WELCOME), "Welcome is not present");
        assertTrue(homePage.getNavBar().isNavItemPresent(LOG_OUT), "Log out is not present");
    }

    @Test()
    public void verifyLogInWithInvalidInputsTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.getNavBar().clickNavBarMenuOption(LOG_IN);
        LoginPage logIn = new LoginPage(getDriver());
        logIn.logIn("teeest", "test");
        Alert alert = getDriver().switchTo().alert();
        assertEquals(alert.getText(), USER_DOES_NOT_EXIST.getText(), "Texts are not equal");
        alert.accept();
        assertTrue(homePage.isModalPresent(), "Modal is not present");
        logIn.logIn("test", "11111");
        assertEquals(alert.getText(), WRONG_PASSWORD.getText(), "Texts are not equal");
        alert.accept();
        assertTrue(homePage.isModalPresent(), "Modal is not present");
        logIn.logIn("", "");
        assertEquals(alert.getText(), FILL_OUT_NAME_PASSWORD.getText(), "Texts are not equal");
        assertTrue(homePage.isModalPresent(), "Modal is not present");
    }
}