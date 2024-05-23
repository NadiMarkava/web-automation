package web;

import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import web.components.AbstractModal;
import web.components.FooterComponent;
import web.pages.MyAbstractPage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static org.testng.Assert.*;
import static web.utils.ScreenshotUtil.takeScreenshot;


public class AbstractTest implements ITestListener, IAbstractTest {

    public static final Logger LOGGER = Logger.getLogger(String.valueOf(AbstractTest.class));

    protected void verifyFooter(MyAbstractPage page) {
        FooterComponent footerComponent = page.getFooterComponent();
        List<String> footerTexts = footerComponent.getFooterTexts();
        assertTrue(footerComponent.isFooterTitlePresent("About Us"), "Title is not present");
        assertTrue(footerComponent.isFooterTitlePresent("Get in Touch"), "Title is not present");
        assertEquals(footerTexts.get(0), "We believe performance needs to be validated at every stage of the software " +
                "development cycle and our open source compatible, massively scalable platform " +
                "makes that a reality.", "Texts are not equal");
        assertEquals(footerTexts.get(1), "Address: 2390 El Camino Real", "Texts are not equal");
        assertEquals(footerTexts.get(2), "Phone: +440 123456", "Texts are not equal");
        assertEquals(footerTexts.get(3), "Email: demo@blazemeter.com", "Texts are not equal");
        assertEquals(footerComponent.getFooterTextWithLogo(), "PRODUCT STORE", "Texts are not equal");
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
