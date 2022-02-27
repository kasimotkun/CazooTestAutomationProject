package test.java.WebTests;

import main.java.DataProviders.TextFileDataProvider;
import main.java.Pages.ValuationResultsPage;
import main.java.Pages.ValueMyCarPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.HashMap;

public class SampleTest extends WebTestBase{



    @Test(priority = 0, dataProvider = "getRegistrationNumbers", dataProviderClass=main.java.DataProviders.TextFileDataProvider.class, enabled=true)
    public void testCarValuation(ITestContext context, HashMap<String,String> hashMapValue) throws InterruptedException {


        ValueMyCarPage valueMyCarPage = new ValueMyCarPage(driver,ngWebDriver,wait);
        PageFactory.initElements(driver,valueMyCarPage);

        Thread.sleep(10000);
        Assert.assertEquals(valueMyCarPage.startValuationBtn.getText(),"Start valuation");

        ValuationResultsPage valuationResultsPage = valueMyCarPage.startValuation(hashMapValue.get("RegNumber"));


        if(driver.findElements(By.xpath("//*[@id=\"your-registration-number-form\"]/div/div[1]/span")).size() == 0){

            String regValue = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div/div[2]/div/div/div[1]/div[1]/div/div/div[1]/div[2]/p[1]")).getText().split(":")[1];
            String make = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div/div[2]/div/div/div[1]/div[1]/div/div/div[1]/div[2]/p[2]")).getText().split(":")[1].substring(0,2);
            String model = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div/div[2]/div/div/div[1]/div[1]/div/div/div[1]/div[2]/p[2]")).getText().split(":")[1].substring(3);

            Object[][] expectedCarValuationData =TextFileDataProvider.getExpectedCarOutputs(Thread.currentThread().getStackTrace()[1].getMethodName());

            for (int i=0; i<expectedCarValuationData.length-1; i++
                 ) {

                HashMap<String,String> expectedValues = (HashMap<String, String>) expectedCarValuationData[i][0];

                if(regValue.equals(expectedValues.get("REGISTRATION"))){
                    if (make.equals(expectedValues.get("MAKE"))){
                        if(model.equals(expectedValues.get("MODEL")))
                            Assert.assertTrue(true);
                        else
                            Assert.fail("Expected Model:"+expectedValues.get("MODEL")+" Actual Model:"+model );
                    }
                    else
                        Assert.fail("Expected Make:"+expectedValues.get("MAKE")+" Actual Make:"+make );

                }
                else
                   continue;

                Assert.fail(regValue +" is found in the page and does NOT exist in the expected output list");


            }

        }else{

            Assert.fail(driver.findElement(By.xpath("//*[@id=\"your-registration-number-form\"]/div/div[1]/span")).getText().replace("registration","registration["+hashMapValue.get("RegNumber")+"]"));
        }

    }

}
