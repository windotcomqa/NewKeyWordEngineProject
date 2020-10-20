package com.dotcom.keyword.base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reports {

	public ExtentReports extent;
	public ExtentTest logger;
	public String testCaseName, testCasedescription;
	public String category, author;

	public void startTest() {

		extent = new ExtentReports("./reports/testresults.html", true);
		logger = extent.startTest("---------New SMB_Internet----------");

		logger.log(LogStatus.PASS, "Print Dummy Test phrase ***************inside*************** StartTest");

	}

	public void reportStep(String status, String description) {

		// System.out.println("----ReportDebugging--Inside reportStep--Check Status =>"
		// + status);
		// System.out.println("----ReportDebugging--Inside reportStep--Check Desc =>" +
		// description);

		logger.log(LogStatus.PASS, description);

	}

	public void endTest() {
		extent.endTest(logger);
	}

	public void endReport() {
		extent.flush();
	}

}
