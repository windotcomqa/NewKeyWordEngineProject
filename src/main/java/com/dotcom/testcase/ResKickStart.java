package com.dotcom.testcase;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.res_keywordEngine.ResNewKeywordEngin;

public class ResKickStart {
	
    public static ExtentTest test;
    public static ExtentReports extent;
    public ResNewKeywordEngin KeyWord;
    public static WebDriver driver;

    @BeforeSuite
    public void before() {
             extent = new ExtentReports("./reports/testresults.html", true);
    }

    @BeforeMethod
    public void setUp(Method method) throws Exception {

    }

    @Test
    public void SignIn() throws Throwable {
             KeyWord = new ResNewKeywordEngin();
             KeyWord.readExecution();
    }

    public void reportTestScenarios(String testScenarios) {
             test = extent.startTest(testScenarios);
    }

//    public static String getScreenhot(WebDriver driver,String Des) throws Exception {
//             TakesScreenshot sc=(TakesScreenshot)driver;
//             File source = sc.getScreenshotAs(OutputType.FILE);
//             File dest = new File("C:\\Users\\asmakhatoon.l\\eclipse-workspace\\DotComKeyword\\Screenshot.png");
//             FileUtils.copyFile(source, dest);
//             return Des;
//    }

    public void reportStep(String status, String description) throws Throwable {

             //test.log(LogStatus.INFO, description);// For extentTest HTML report
             if (status.equalsIgnoreCase("PASS")) {
                       test.log(LogStatus.PASS, description);// For extentTest HTML report
             }
             else if (status.equalsIgnoreCase("FAIL")) {
                       //getScreenhot(driver, status);
                       test.log(LogStatus.FAIL, description);// For extentTest HTML report

     

             }
             
    }
    
  
   

    @AfterSuite
    public void endTest() {
             extent.endTest(test);
             extent.flush();
    }

    



}
