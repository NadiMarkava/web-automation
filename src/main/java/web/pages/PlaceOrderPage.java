package web.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class PlaceOrderPage extends AbstractPage {

    @FindBy(xpath = ".//h5[text()='Place order']")
    private ExtendedWebElement title;

    @FindBy(id = "totalm")
    private ExtendedWebElement totalPrice;

    @FindBy(xpath = "//label[text()='Name:']//../input")
    private ExtendedWebElement nameInput;

    @FindBy(xpath = "//label[text()='Country:']//../input")
    private ExtendedWebElement countryInput;

    @FindBy(xpath = "//label[text()='City:']//../input")
    private ExtendedWebElement cityInput;

    @FindBy(xpath = "//label[text()='Credit card:']//../input")
    private ExtendedWebElement creditCardInput;

    @FindBy(xpath = "//label[text()='Month:']//../input")
    private ExtendedWebElement monthInput;

    @FindBy(xpath = "//label[text()='Year:']//../input")
    private ExtendedWebElement yearInput;

    @FindBy(xpath = ".//button[text()='Purchase']")
    private ExtendedWebElement purchaseButton;

    @FindBy(xpath = "//div[@class='modal fade show']//button[text()='Close']")
    private ExtendedWebElement closeButton;

    public PlaceOrderPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(title);
    }

    public boolean isNameFieldPresent() {
        return nameInput.isElementPresent();
    }

    public boolean isCountryFieldPresent() {
        return countryInput.isElementPresent();
    }

    public boolean isCityFieldPresent() {
        return cityInput.isElementPresent();
    }

    public boolean isCreditCardFieldPresent() {
        return creditCardInput.isElementPresent();
    }

    public boolean isMonthFieldPresent() {
        return monthInput.isElementPresent();
    }

    public boolean isYearFieldPresent() {
        return yearInput.isElementPresent();
    }

    public boolean isCloseButtonPresent(){
        return closeButton.isElementPresent();
    }

    public boolean isPurchaseButtonPresent(){
        return purchaseButton.isElementPresent();
    }

    public String getTotalPriceText() {
        return totalPrice.getText().replace("Total: ", "");
    }

    public void typeName(String name){
        nameInput.type(name);
    }

    public void typeCountry(String country){
        countryInput.type(country);
    }

    public void typeCity(String city){
        cityInput.type(city);
    }

    public void typeCreditСard(String creditСard){
        creditCardInput.type(creditСard);
    }

    public void typeMonth(String month){
        monthInput.type(month);
    }

    public void typeYear(String year){
        yearInput.type(year);
    }

    public void clickPurchaseButton() {
        purchaseButton.click();
    }

    public ConfirmOrderPage submitPlaceOrderForm(String name, String country, String city, String creditСard, String month, String year) {
        typeName(name);
        typeCountry(country);
        typeCity(city);
        typeCreditСard(creditСard);
        typeMonth(month);
        typeYear(year);
        clickPurchaseButton();
        return new ConfirmOrderPage(driver);
    }
}
