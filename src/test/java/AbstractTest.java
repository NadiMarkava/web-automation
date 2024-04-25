import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.components.AbstractModal;
import web.pages.AbstractPage;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class AbstractTest {

    private static final String FOOTER_TEXT = "About Us\n" +
            "We believe performance needs to be validated at every stage of the software development cycle and our open source compatible, massively scalable platform makes that a reality.\n" +
            "Get in Touch\n" +
            "Address: 2390 El Camino Real\n" +
            "Phone: +440 123456\n" +
            "Email: demo@blazemeter.com\n" +
            "PRODUCT STORE";

    protected void verifyFooter(WebDriver driver) {
        assertEquals(driver.findElement(By.id("fotcont")).getText(), FOOTER_TEXT, "Footer text are not equal");
    }

    protected void verifyModal(AbstractModal abstractModal, String title, List<String> fieldNames, List<String> buttonsNames) {
        assertEquals(abstractModal.getTitle(), title, "Titles are not equal");
        assertEquals(abstractModal.getFieldNames(), fieldNames, "Fields names are not equal");
        assertEquals(abstractModal.getButtonsNames(), buttonsNames, "Fields names are not equal");
        abstractModal.clickModalButton("Close");
        assertTrue(!abstractModal.isModalPresent(), "Modal is present");
    }
}
