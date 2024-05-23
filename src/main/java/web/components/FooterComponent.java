package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class FooterComponent extends AbstractUIObject {

    @FindBy(xpath = "//h4[./*[text()='About Us']]")
    private ExtendedWebElement aboutUsTitle;

    @FindBy(xpath = "//h4[./*[text()='About Us']]/../p")
    private ExtendedWebElement aboutUsText;

    @FindBy(xpath = "//h4/b[text()='Get in Touch']")
    private ExtendedWebElement getInTouchTitle;

    @FindBy(xpath = "//h4[./*[text()='Get in Touch']]/../p[1]")
    private ExtendedWebElement address;

    @FindBy(xpath = "//h4[./*[text()='Get in Touch']]/../p[2]")
    private ExtendedWebElement phoneNumber;

    @FindBy(xpath = "//h4[./*[text()='Get in Touch']]/../p[3]")
    private ExtendedWebElement email;

    @FindBy(xpath = "//h4/img/..")
    private ExtendedWebElement footerTextWithLogo;

    public FooterComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public FooterComponent(WebDriver driver) {
        super(driver);
    }

    public String getAboutUsText() {
        return aboutUsText.getText();
    }

    public boolean isAboutUsTitlePresent(){
        return aboutUsTitle.isElementPresent();
    }

    public boolean isGetInTouchTitlePresent(){
        return getInTouchTitle.isElementPresent();
    }

    public boolean isAddressPresent(){
        return address.isElementPresent();
    }

    public boolean isPhoneNumberPresent(){
        return phoneNumber.isElementPresent();
    }

    public boolean isEmailPresent(){
        return email.isElementPresent();
    }

    public String getAddress() {
        return address.getText();
    }

    public String getPhoneNumber() {
        return phoneNumber.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getFooterTextWithLogo() {
        return footerTextWithLogo.getText();
    }
}
