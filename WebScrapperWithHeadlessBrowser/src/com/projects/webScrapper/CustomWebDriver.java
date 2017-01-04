package com.projects.webScrapper;

import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by rbhushan on 11/1/2016.
 */
public class CustomWebDriver {
    private static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";
    private static DesiredCapabilities desiredCaps;
    private static WebDriver driver;


    public static void initPhantomJS() {
        desiredCaps = new DesiredCapabilities();
        desiredCaps.setJavascriptEnabled(true);
        desiredCaps.setCapability("takesScreenshot", false);
        //File file = new File("C:\\Users\\rbhushan\\Music\\pdoc\\Personals\\src\\com\\projects\\webScrapper\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
        File file = new File(VigiAccessScrapper.getCurrentProductionDirectory()+ File.separator + "phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
        if(!file.exists()){
            file = new File("phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
        }
        desiredCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, file.getAbsolutePath());
      //  desiredCaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent", USER_AGENT);

        ArrayList<String> cliArgsCap = new ArrayList();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        cliArgsCap.add("--webdriver-loglevel=ERROR");

        desiredCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        driver = new PhantomJSDriver(desiredCaps);
        //driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    public static WebDriver getDriver() {
        //System.setProperty("phantomjs.page.settings.userAgent", USER_AGENT);
        if (driver == null) {
            initPhantomJS();
        }
        return driver;
    }

    public static void waitForAjax(WebDriver driver) {
        new WebDriverWait(driver, 180).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                return (Boolean) js.executeScript("return jQuery.active == 0");
            }
        });
    }

    public static void waitForEnablingButton(WebDriver driver, String xpath) {

        System.out.println("waiting for button to get enabled.");
        new WebDriverWait(driver, 180).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

    }

    public static void waitForGettingSearchBoxVisible(WebDriver driver, String xpath){
        System.out.println("waiting for search box to become visible.");
        new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

    }
    public static void waitForSearchResult(WebDriver driver , WebElement element){

       // new WebDriverWait(driver, 180).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"waitMessage\"]")));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("waiting for getting the result after clicking " + element.toString() + "::" +element.getText());
        new WebDriverWait(driver, 180).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"waitMessage\"]")));
    }
}
