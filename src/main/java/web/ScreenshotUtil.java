package web;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

import static web.GetPropertyUtil.getValue;

public class ScreenshotUtil {

    public static void takeScreenshot(String methodName, WebDriver driver) throws IOException, InterruptedException {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(getValue("filePath") + methodName + ".jpg");
        FileUtils.copyFile(file, destFile);
    }
}
