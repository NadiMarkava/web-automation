import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.components.AbstractModal;
import web.components.FooterComponent;
import web.pages.AbstractPage;
import web.pages.BasePage;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class AbstractTest {

    protected void verifyFooter(BasePage page, WebDriver driver) {
        FooterComponent footerComponent = page.getFooterComponent(driver);
        List<String> footerTexts = footerComponent.getFooterTexts();
        assertTrue(footerTexts.size() == 3, "Footers sizes are not equal");
        assertEquals(footerTexts.get(0), "About Us\n" +
                        "We believe performance needs to be validated at every stage of the software " +
                        "development cycle and our open source compatible, massively scalable platform " +
                        "makes that a reality.", "Texts are not equal");
        assertEquals(footerTexts.get(1), "Get in Touch\n" + "Address: 2390 El Camino Real\n" +
                "Phone: +440 123456\n" + "Email: demo@blazemeter.com", "Texts are not equal");
        assertEquals(footerTexts.get(2), "PRODUCT STORE", "Texts are not equal");
    }

    protected void verifyModal(AbstractModal abstractModal, String title, List<String> fieldNames, List<String> buttonsNames) {
        assertEquals(abstractModal.getTitle(), title, "Titles are not equal");
        assertEquals(abstractModal.getFieldNames(), fieldNames, "Fields names are not equal");
        assertEquals(abstractModal.getButtonsNames(), buttonsNames, "Fields names are not equal");
        abstractModal.clickModalButton("Close");
        assertTrue(!abstractModal.isModalPresent(), "Modal is present");
    }
}
