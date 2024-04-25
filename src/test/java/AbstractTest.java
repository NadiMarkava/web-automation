import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;


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
}
