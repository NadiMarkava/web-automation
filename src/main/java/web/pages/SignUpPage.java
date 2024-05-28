package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SignUpPage extends AbstractPage {

    @FindBy(xpath = "//h2[text()='Sign up']")
    private ExtendedWebElement title;

    @FindBy(xpath = "//div[@class='modal fade show']//label[text()='Username:']//../input")
    private ExtendedWebElement userNameInput;

    @FindBy(xpath = "//div[@class='modal fade show']//label[text()='Password:']//../input")
    private ExtendedWebElement passwordInput;

    @FindBy(xpath = "//div[@class='modal fade show']//button[text()='Close']")
    private ExtendedWebElement closeButton;

    @FindBy(xpath = "//button[text()='Sign up']")
    private ExtendedWebElement signUpButton;

    public SignUpPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(title);
    }

    public boolean isUserNameFieldPresent() {
        return userNameInput.isElementPresent();
    }

    public boolean isPasswordPresent() {
        return passwordInput.isElementPresent();
    }

    public boolean isCloseButtonPresent() {
        return closeButton.isElementPresent();
    }

    public boolean isSignUpButtonPresent() {
        return signUpButton.isElementPresent();
    }

    public void typeUserName(String userName){
        userNameInput.type(userName);
    }

    public void typePassword(String password){
        passwordInput.type(password);
    }

    public void clickSignUpButton() {
        signUpButton.click();
    }

    public void signUp(String userName, String password){
        typeUserName(userName);
        typePassword(password);
        clickSignUpButton();
    }
}
