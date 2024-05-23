package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AbstractModal extends AbstractUIObject {

    private final String MODAL = "//div[@class='modal fade show']//div[@class='modal-content']";

    @FindBy(xpath = MODAL)
    private ExtendedWebElement modalForm;

    @FindBy(xpath = MODAL + "//h5")
    private ExtendedWebElement title;

    @FindBy(xpath = MODAL + "//div[@class='form-group']/label")
    private List<ExtendedWebElement> fieldsNames;

    @FindBy(xpath = "//label[text()='%s']/../input")
    private ExtendedWebElement input;

    @FindBy(xpath = MODAL + "//button[text()='%s']")
    private ExtendedWebElement modalButton;

    @FindBy(xpath = MODAL + "//div[@class='modal-footer']//button")
    private List<ExtendedWebElement> buttonsNames;

    @FindBy(xpath = MODAL + "//button[2]")
    private ExtendedWebElement submitButton;

    public AbstractModal(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getTitle() {
        return title.getText();
    }

    public void typeFields(List<String> fields, List<String> inputs) {
        IntStream.range(0, inputs.size()).forEach(i -> {
            input.format(fields.get(i)).type(inputs.get(i));
        });
    }

    public List<String> getFieldNames() {
        return fieldsNames
                .stream()
                .map(e -> e.getText())
                .collect(Collectors.toList());
    }

    public List<String> getButtonsNames() {
        return buttonsNames
                .stream()
                .map(e -> e.getText())
                .collect(Collectors.toList());
    }

    public void submitModalForm(List<String> fields, List<String> inputs) {
        typeFields(fields, inputs);
        submitButton.click();
    }

    public void clickModalButton(String button) {
        modalButton.format(button).click();
    }

    public boolean isModalPresent() {
        return modalForm.isPresent();
    }
}
