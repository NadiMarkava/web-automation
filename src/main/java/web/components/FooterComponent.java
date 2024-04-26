package web.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class FooterComponent extends BaseComponent {

    public FooterComponent(WebElement root) {
        super(root);
    }

    public List<String> getFooterTexts() {
        return root.findElements(By.xpath("//div[@class='caption']"))
                .stream()
                .map(t -> t.getText())
                .collect(Collectors.toList());
    }
}
