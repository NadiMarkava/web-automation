package web;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import web.components.AbstractModal;
import web.components.FooterComponent;
import web.pages.HomePage;
import web.pages.MyAbstractPage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static web.utils.ScreenshotUtil.takeScreenshot;
import static org.testng.Assert.*;


public class AbstractTest implements ITestListener, IAbstractTest {

    public static final Logger LOGGER = Logger.getLogger(String.valueOf(AbstractTest.class));

    protected void verifyFooter(MyAbstractPage page) {
        FooterComponent footerComponent = page.getFooterComponent();
        List<String> footerTexts = footerComponent.getFooterTexts();
        assertEquals(footerTexts.size(), 3, "Footers sizes are not equal");
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
        assertFalse(abstractModal.isModalPresent(), "Modal is present");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getName().trim() + result.id();
        LOGGER.warning(result.getName() + " test is failed");
        ITestContext context = result.getTestContext();
        WebDriver driverContext = (WebDriver) context.getAttribute("WebDriver");
        try {
            takeScreenshot(methodName, driverContext);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
