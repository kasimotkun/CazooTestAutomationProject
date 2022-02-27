package main.java.Listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Parameters;


public class TestListener implements ITestListener {



    @Override
    public void onFinish(ITestContext arg0) {


    }


    @Override
    public void onStart(ITestContext arg0) {
        // TODO Auto-generated method stub


    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub
        //takeScreenshot(arg0);
    }


    @Override
    public void onTestFailure(ITestResult arg0 ) {
        // TODO Auto-generated method stub
        ITestContext context = arg0.getTestContext();
        String  takeScreenshotOnTestFailure = (String) context.getAttribute("takeScreenshotOnTestFailure");

        if (takeScreenshotOnTestFailure.equals("true"))
            takeScreenshot(arg0);

    }

    @Override
    public void onTestSkipped(ITestResult arg0) {
        // TODO Auto-generated method stub
        //takeScreenshot(arg0);
    }

    @Override
    public void onTestStart(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        //	takeScreenshot(arg0);
    }

    private void takeScreenshot(ITestResult arg0) {



        ITestContext context = arg0.getTestContext();
        WebDriver  driver = (WebDriver) context.getAttribute("driver");
        String  screenshotsDir = (String) context.getAttribute("screenshotsDir");

        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //The below method will save the screen shot in d drive with test method name
        try {
            File destFile = new File((String) screenshotsDir+arg0.getMethod().getMethodName()+"_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".png");

            FileUtils.copyFile(scrFile, destFile);
            Reporter.log("<a href='"+ destFile.getAbsolutePath() + "'> <img src='"+ destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");


        } catch (IOException e) {
            e.printStackTrace();
        }





    }



}