package main.java.Pages;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ValuationResultsPage extends PageBase {
    private WebDriver driver;
    private WebDriverWait wait;
    private NgWebDriver ngWebDriver;





    public ValuationResultsPage(WebDriver driver, NgWebDriver ngWebDriver, WebDriverWait wait) {

        this.driver=driver;
        this.wait=wait;
        this.ngWebDriver=ngWebDriver;
        ngWebDriver.waitForAngularRequestsToFinish();

    }


}
