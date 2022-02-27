package test.java.WebTests;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.lang.reflect.Method;

import com.paulhammant.ngwebdriver.NgWebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import main.java.Utils.Helper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class WebTestBase {

    protected NgWebDriver ngWebDriver;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String driverType = "chrome";

    private static Logger logger = Logger.getLogger(WebTestBase.class);

    static StringWriter requestWriter;
    static PrintStream requestCapture;
    static ExtentTest test;
    static ExtentReports extentReports;
    static Response response;
    static RequestSpecification requestSpec;

    @BeforeSuite
    public void beforeSuitFunc(ITestContext d) {

        extentReports = new ExtentReports(System.getProperty("user.dir") + File.separator + "reports" + File.separator
                +this.getClass().getSimpleName().toString() + ".html");


    }

    @Parameters({"driverType","url", "headless", "takeScreenshotOnTestFailure","screenshotsDir"})
    @BeforeTest
    public void beforeTestFunc(ITestContext d, String driverType, String url, String headless, String takeScreenshotOnTestFailure, String screenshotsDir) {
        this.driverType = driverType;

        d.setAttribute("driverType", driverType);
        d.setAttribute("url", url);
        d.setAttribute("headless", headless);
        d.setAttribute("takeScreenshotOnTestFailure", takeScreenshotOnTestFailure);
        d.setAttribute("screenshotsDir", screenshotsDir);
    }

    @BeforeClass
    public void beforeClassFunc(ITestContext d) {

    }

    @BeforeMethod
    public void beforeMethodFunc(ITestContext d, Method method, Object[] testData) {

        test = extentReports.startTest(method.getName() + "_" + testData[1]);

        if (Helper.getOS() == Helper.OS.WINDOWS)
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        else if (Helper.getOS() == Helper.OS.LINUX)
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();

        if (d.getAttribute("headless").toString() == "true") {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
        }

        chromeOptions.addArguments("--lang=en-GB");


        driver = new ChromeDriver(chromeOptions);
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        ngWebDriver = new NgWebDriver(jsDriver);
        wait = new WebDriverWait(driver, 10, 50);


        driver.get(d.getAttribute("url").toString());
        wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//*[@id=\"__next\"]/div/div[1]/div[2]/div/div/button")))).click();

        ngWebDriver.waitForAngularRequestsToFinish();


        d.setAttribute("driver", driver);
        d.setAttribute("ngWebDriver", ngWebDriver);

    }


    @AfterMethod
    public void testStop(ITestResult result, ITestContext d) throws IOException  {

        extentReports.endTest(test);

        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(LogStatus.FAIL, result.getThrowable());
        }
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(LogStatus.PASS, result.toString());
        }
        if (result.getStatus() == ITestResult.SKIP) {
            test.log(LogStatus.SKIP, result.toString());
        }


        WebDriver driver = (WebDriver) d.getAttribute("driver");

        driver.close();
        driver.quit();


    }


    @AfterClass
    public void afterClassFunc(ITestContext d) {


    }

    @AfterTest
    public void afterTestFunc(ITestContext d) {


    }

    @Parameters("killAllChromeAndDriverProcessesAfterSuite")
    @AfterSuite
    public void afterSuitFunc(ITestContext d, String killAllChromeAndDriverProcessesAfterSuite) {

        extentReports.flush();
        extentReports.close();

        Reporter.log("killAllChromeAndDriverProcessesAfterSuite:" + killAllChromeAndDriverProcessesAfterSuite, true);

        String driverType = (String) d.getAttribute("driverType");

        if (killAllChromeAndDriverProcessesAfterSuite.equals("true")) {
            killProcesses(driverType);
        }


    }

    private void killProcesses(String driverType) {

        try {
            if (driverType.equals("chrome")) {
                Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
                //   Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            }
            if (driverType.equals("firefox")) {
                Runtime.getRuntime().exec("taskkill /F /IM geckoDriver.exe");
                //  Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");

            }
            if (driverType.equals("ie")) {
                Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
                // Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");

            }
        } catch (IOException
                e) {

        }
    }

}
