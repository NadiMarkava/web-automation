package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Footer extends AbstractUIObject {

    @FindBy(xpath = "//h4[./*[text()='About Us']]")
    private ExtendedWebElement aboutUsTitle;

    @FindBy(xpath = "//h4[./*[text()='About Us']]/../p")
    private ExtendedWebElement aboutUsText;

    @FindBy(xpath = "//h4/b[text()='Get in Touch']")
    private ExtendedWebElement getInTouchTitle;

    @FindBy(xpath = "//h4[./*[text()='Get in Touch']]/../p[contains(text(), 'Address')]")
    private ExtendedWebElement address;

    @FindBy(xpath = "//h4[./*[text()='Get in Touch']]/../p[contains(text(), 'Phone')]")
    private ExtendedWebElement phoneNumber;

    @FindBy(xpath = "//h4[./*[text()='Get in Touch']]/../p[contains(text(), 'Email')]")
    private ExtendedWebElement email;

    @FindBy(xpath = "//h4/img/..")
    private ExtendedWebElement footerSectionWithLogo;

    public Footer(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public Footer(WebDriver driver) {
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

    public String getAddressText() {
        return address.getText();
    }

    public String getPhoneNumberText() {
        return phoneNumber.getText();
    }

    public String getEmailText() {
        return email.getText();
    }

    public String getFooterSectionWithLogoText() {
        return footerSectionWithLogo.getText();
    }
}
