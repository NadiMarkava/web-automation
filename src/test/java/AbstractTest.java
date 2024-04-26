import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import web.components.AbstractModal;
import web.components.FooterComponent;
import web.pages.BasePage;
import web.pages.HomePage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class AbstractTest implements ITestListener {

    public static final Logger LOGGER = Logger.getLogger(String.valueOf(AbstractTest.class));
    protected WebDriver driver;
    String filePath = "/Users/solvd/IdeaProjects/web-automation/src/test/screenshots/";

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

    @BeforeTest
    public void setUp(ITestContext context) throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
        context.setAttribute("WebDriver", driver);
        HomePage homePage = new HomePage(driver);
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

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }

    public void takeScreenshot(String methodName, WebDriver driver) throws IOException, InterruptedException {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(filePath + methodName + ".jpg");
        FileUtils.copyFile(file, destFile);
    }
}
