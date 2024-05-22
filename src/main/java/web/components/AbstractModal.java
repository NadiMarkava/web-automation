package web.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.By;
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
            findExtendedWebElement(By.xpath(String.format("//label[text()='%s']/../input", fields.get(i)))).type(inputs.get(i));
        });
    }

    public String getFieldText(String fieldName) {
        return findExtendedWebElement(By.xpath(String.format("//label[text()='%s']/../input", fieldName))).getText();
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

    public void typeField(List<String> fields, List<String> inputs) {
        IntStream.range(0, inputs.size()).forEach(i -> {
            findExtendedWebElement(By.xpath(String.format("//label[text()='%s']/../input", fields.get(i))))
                    .type(inputs.get(i));
        });
    }

    public void submitModalForm(List<String> fields, List<String> inputs) {
        typeFields(fields, inputs);
        submitButton.click();
    }

    public void clickModalButton(String button) {
        findExtendedWebElement(By.xpath(String.format(MODAL + "//button[text()='%s']", button))).click();
    }

    public boolean isModalPresent() {
        return modalForm.isPresent();
    }
}
