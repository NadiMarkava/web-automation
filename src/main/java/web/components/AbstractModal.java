package web.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AbstractModal {

    protected WebElement root;
    private final String modal = "//div[@class='modal fade show']//div[@class='modal-content']";
    private final By title = By.xpath(modal + "//h5");

    public AbstractModal(WebElement root) {
        this.root = root;
    }

    public String getTitle() {
        return root.findElement(title).getText();
    }

    public void typeFields(List<String> fields, List<String> inputs) {
        IntStream.range(0, inputs.size()).forEach(i -> {
            root.findElement(By.xpath(String.format("//label[text()='%s']/../input", fields.get(i)))).sendKeys(inputs.get(i));
        });
    }

    public String getFieldText(String fieldName) {
        return root.findElement(By.xpath(String.format("//label[text()='%s']/../input", fieldName))).getText();
    }

    public List<String> getFieldNames() {
        return root.findElements(By.xpath(modal + "//div[@class='form-group']/label"))
                .stream()
                .map(e -> e.getText())
                .collect(Collectors.toList());
    }

    public List<String> getButtonsNames() {
        return root.findElements(By.xpath(modal + "//div[@class='modal-footer']//button"))
                .stream()
                .map(e -> e.getText())
                .collect(Collectors.toList());
    }

    public void typeField(List<String> fields, List<String> inputs) {
        IntStream.range(0, inputs.size()).forEach(i -> {
            root.findElement(By.xpath(String.format("//label[text()='%s']/../input", fields.get(i))))
                    .sendKeys(inputs.get(i));
        });
    }

    public void submitModalForm(List<String> fields, List<String> inputs) {
        typeFields(fields, inputs);
        root.findElement(By.xpath(modal + "//button[2]")).click();
    }

    public void clickModalButton(String button) {
        root.findElement(By.xpath(String.format(modal + "//button[text()='%s']", button))).click();
    }

    public boolean isModalPresent() {
        return root.findElement(By.xpath(modal)).isDisplayed();
    }
}
