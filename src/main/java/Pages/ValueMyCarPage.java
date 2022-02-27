package main.java.Pages;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Locale;



public class ValueMyCarPage extends PageBase {

    private WebDriver driver;
    private WebDriverWait wait;
    private NgWebDriver ngWebDriver;



    @FindBy(how=How.ID_OR_NAME, using="vrm")
    public WebElement regNumberInput;

    @FindBy(how=How.XPATH, using="//*[@id=\"__next\"]/main/div[1]/div/div/div/article/div[2]/div/form/button")
    public WebElement startValuationBtn;


    public ValueMyCarPage(WebDriver driver, NgWebDriver ngWebDriver, WebDriverWait wait) {

        this.driver=driver;
        this.wait=wait;
        this.ngWebDriver = ngWebDriver;
    }



    public ValuationResultsPage startValuation(String regNumber) throws InterruptedException {

        regNumberInput.sendKeys(regNumber);
        Thread.sleep(100);
        startValuationBtn.click();
        Thread.sleep(5000);

        return new ValuationResultsPage(driver,ngWebDriver,wait);

    }
}
