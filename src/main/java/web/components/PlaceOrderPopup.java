package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class PlaceOrderPopup extends AbstractUIObject {

    @FindBy(xpath = ".//h5")
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

    @FindBy(xpath = ".//button[text()='Close']")
    private ExtendedWebElement closeButton;

    public PlaceOrderPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getTitle() {
        return title.getText();
    }

    public String getName(){
        return nameInput.getText();
    }

    public String getCountry(){
        return countryInput.getText();
    }

    public String getCity(){
        return cityInput.getText();
    }

    public String getCreditCard(){
        return creditCardInput.getText();
    }

    public String getMonth(){
        return monthInput.getText();
    }

    public String getYear(){
        return yearInput.getText();
    }

    public boolean isCloseButtonPresent(){
        return closeButton.isElementPresent();
    }

    public boolean isPurchaseButtonPresent(){
        return purchaseButton.isElementPresent();
    }

    public String getTotalPrice() {
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

    public void submitPlaceOrderForm(String name, String country, String city, String creditСard, String month, String year) {
        typeName(name);
        typeCountry(country);
        typeCity(city);
        typeCreditСard(creditСard);
        typeMonth(month);
        typeYear(year);
        purchaseButton.click();
    }
}
