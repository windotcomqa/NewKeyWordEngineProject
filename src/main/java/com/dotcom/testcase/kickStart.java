package com.dotcom.testcase;

import java.lang.reflect.Method;

import javax.sound.midi.MidiDevice.Info;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.dotcom.keyword.base.Base;
import com.dotcom.keyword.engine.NewKeyWordEngine;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class kickStart extends Base {

	public static ExtentTest test;
	public static ExtentReports extent;
	public NewKeyWordEngine KeyWord;

	@BeforeSuite
	public void before() {
		extent = new ExtentReports("./reports/testresults.html", true);
	}

	@BeforeMethod
	public void setUp(Method method) throws Exception {

	}

	@Test
	public void SignIn() throws Throwable {
		KeyWord = new NewKeyWordEngine();
		KeyWord.readExecution();
	}

	public void reportTestScenarios(String testScenarios) {
		test = extent.startTest(testScenarios);
	}

	public void reportStep(String status, String description) {
    if (status.equalsIgnoreCase("INFO")) {
    	test.log(LogStatus.INFO, description);// For extentTest HTML report
	}else if (status.equalsIgnoreCase("PASS")) {
		test.log(LogStatus.PASS, description);// For extentTest HTML report
	}else if (status.equalsIgnoreCase("FAIL")) {
		test.log(LogStatus.FAIL, description);// For extentTest HTML report
	}
		test.log(LogStatus.PASS, "Print Dummy Test phrase ***************Ouside*************** StartTest");
	}

	@AfterSuite
	public void endTest() {
		extent.endTest(test);
		extent.flush();
	}

}
