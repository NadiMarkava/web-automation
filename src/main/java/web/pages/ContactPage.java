package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ContactPage extends AbstractPage {

    @FindBy(xpath = "//h2[text()='New message']")
    private ExtendedWebElement title;

    @FindBy(xpath = "//label[text()='ContactPage Email:']//../input")
    private ExtendedWebElement emailInput;

    @FindBy(xpath = "//label[text()='ContactPage Name:']//../input")
    private ExtendedWebElement nameInput;

    @FindBy(xpath = "//label[text()='Message:']//../textarea")
    private ExtendedWebElement messageInput;

    @FindBy(xpath = "//div[@class='modal fade show']//button[text()='Close']")
    private ExtendedWebElement closeButton;

    @FindBy(xpath = "//button[text()='Send message']")
    private ExtendedWebElement sendMessageButton;

    public ContactPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(title);
    }

    public boolean isEmailFieldPresent() {
        return emailInput.isElementPresent();
    }

    public boolean isNameFieldPresent() {
        return nameInput.isElementPresent();
    }

    public boolean isMessageFieldPresent() {
        return messageInput.isElementPresent();
    }

    public boolean isCloseButtonPresent() {
        return closeButton.isElementPresent();
    }

    public boolean isSendMessageButtonPresent() {
        return sendMessageButton.isElementPresent();
    }

    public void typeEmail(String email){
        emailInput.type(email);
    }

    public void typeName(String name){
        nameInput.type(name);
    }

    public void typeMessage(String message){
        messageInput.type(message);
    }

    public void clickSendMessageButton() {
        sendMessageButton.click();
    }

    public void sendMessage(String email, String name, String message){
        typeEmail(email);
        typeName(name);
        typeMessage(message);
        clickSendMessageButton();
    }
}
