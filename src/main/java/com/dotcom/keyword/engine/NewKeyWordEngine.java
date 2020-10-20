package com.dotcom.keyword.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.dotcom.keyword.base.Base;
import com.dotcom.keyword.base.Reports;
import com.dotcom.testcase.kickStart;

public class NewKeyWordEngine extends kickStart {

	public WebDriver driver;
	public String TestCaseID;
	public Properties prop;
	public Base base;
	public WriteExcel wc;
	public WebElement element;
	public SmbLinksRepository smblink;

	public static Workbook book;
	public static org.apache.poi.ss.usermodel.Sheet sheet;
	// reports
	public final String SCENARIO_SHEET_PATH = "C:\\Users\\roobini.bu\\Desktop\\mydocs\\Automaiton updated smb and res\\NewKeyWordEngineProject-master\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Test_Scenarios.xlsx";

	// Master sheet test plan
	public void readExecution() throws Throwable {

		File f = new File(
				"C:\\Users\\roobini.bu\\Desktop\\mydocs\\Automaiton updated smb and res\\NewKeyWordEngineProject-master\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Test_Scenarios.xlsx");
		FileInputStream fin = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fin);
		Sheet sheet = wb.getSheet("MasterSheet");
		int TotalRowCount = sheet.getPhysicalNumberOfRows();
		String TC = String.valueOf(TotalRowCount);
		System.out.println("Total Plan Count is:- " + TC);
		for (int i = 1; i <= sheet.getPhysicalNumberOfRows() - 1; i++) {
			System.out.println("MasterSheet Value of i is:- " + i);
			Row headrow = sheet.getRow(i);
			String headname = headrow.getCell(3).getStringCellValue();
			System.out.println(headname);
			System.out.println("MasterSheet Value of Flag is:- " + headname);
			if (headname.equals("Yes")) {
				TestCaseID = headrow.getCell(1).getStringCellValue();
				startExecution(TestCaseID);
				/*
				 * base.wait(driver, "//p[text()='Order Number']/following-sibling::p");
				 * WebElement orderID =
				 * driver.findElement(By.xpath("//p[text()='OrderNumber']/following-sibling::p")
				 * ); wc.updateExcel(i, 4, orderID.getText());
				 * System.out.println("Your order has been placed");
				 * System.out.print("Your order ID is " + orderID.getText());
				 * System.out.println(i); System.out.println(TestCaseID);
				 */
				TestCaseID = "";
			}
			System.out.println("MasterSheet Value of SheetName is:- " + TestCaseID);
		}

	}

	// Start Execution
	public void startExecution(String sheetName) throws Throwable

	{
		reportTestScenarios(sheetName);
		FileInputStream file = null;
		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);

		int k = 0;
		for (int i = 1; i <= sheet.getPhysicalNumberOfRows() - 1; i++) {

			System.out.println("Value of the (Index) is- " + i);

			String LocatorName = sheet.getRow(i).getCell(k + 1).toString().trim();
			String action = sheet.getRow(i).getCell(k + 2).toString().trim();
			String value = sheet.getRow(i).getCell(k + 3).toString().trim();

			System.out.println("Value of the (LocatorName) is- " + LocatorName);
			System.out.println("Value of the (action) is- " + action);
			System.out.println("Value of the (value) is- " + value);

			if (LocatorName.isEmpty() || LocatorName.equals(null)) {
				String val = base.getMapData(LocatorName);
				System.out.println("Value of the keyword (Key Data) is- " + val);

			}
			/*
			 * //SMBMy account if (action.equalsIgnoreCase("MyWinMenu")) { String val =
			 * base.getMapData(LocatorName); //base.mousehover(driver, linkData);
			 * base.smbMyAccount(LocatorName, value);
			 * 
			 * 
			 * 
			 * }
			 */
			// open browser

			if (action.equalsIgnoreCase("openBrowser")) {
				try

				{
					base = new Base();
					prop = base.init_properties();
					if (value.isEmpty() || value.equals("NA")) {
						driver = base.init_driver(prop.getProperty("browser"));

					} else {
						driver = base.init_driver(value);

					}
					reportStep("PASS", "Browser has been launched");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", "Browser hasn't been launched");

				}
			}

			else if (action.equalsIgnoreCase("navigateURL")) {
				try {
					base.navigateUrl(driver, value);
					System.out.println("URL launched");
					reportStep("FAIL", "Browser hasn't been launched");

				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", "Browser hasn't been launched");
				}
			}

			// Click element

			else if (action != null && action.equalsIgnoreCase("click_element")) {

				try {
					String val = base.getMapData(LocatorName);

					base.click_element(driver, val);

					System.out.println(value);
					reportStep("PASS", "Click function working good");
				} catch (Exception e) {
					reportStep("FAIL", "Click function not working good");

					System.out.println(e.getMessage());
				}
			}

			// provideAddress

			else if (action != null && action.equalsIgnoreCase("provideAddress")) {
				try {

					String val10 = base.getMapData(LocatorName);
					String[] arr_locator = val10.split("%");
					String[] arr_value = value.split(",");
					int loc = 0;
					int data = 0;

					do {

						if (loc == 0 && data == 0) {
							System.out.println("PROVIDE ADDRESS");
							base.SendKeys(driver, arr_locator[loc], arr_value[data]);
							loc++;
							System.out.println("Street address" + arr_value[data]);
							base.mousehover(driver, arr_locator[loc]);
							loc++;
							data++;
							base.SendKeys(driver, arr_locator[loc], arr_value[data]);
							System.out.println("City" + arr_value[data]);
							loc++;
							data++;
							base.click_element(driver, arr_locator[loc]);
							String stateLoc = "//a[text()='" + arr_value[data] + "']";
							base.scroll(driver, stateLoc);
							base.click_element(driver, stateLoc);
							System.out.println("State" + arr_value[data]);
							loc++;
							data++;
							base.SendKeys(driver, arr_locator[loc], arr_value[data]);
							loc++;
							System.out.println("Zip Code" + arr_value[data]);
							base.mousehover(driver, arr_locator[loc]);
							Thread.sleep(5000);
							System.out.println("Address has been Entered successfully");
							System.out.println(
									"----ReportDebugging--Calling reportStep--Check Desc =>" + "Just Address Entered");
							reportStep("PASS", "Address is entered");
							break;

						} else {
							System.out.println("Please enter the Valid address");
							reportStep("FAIL", "Address is not entered");
							break;
						}
					} while (loc == 6);

				} catch (Exception e) {
					System.out.println(e.getMessage());

				}

			}

			// selectCoreProduct
			else if (action != null && action.equalsIgnoreCase("selectCoreProduct")) {
				try {
					String val11 = base.getMapData(LocatorName);
					String[] arr_locator = val11.split("%");
					String Page_Title = driver.getTitle();
					System.out.println("PAGE TITLE" + Page_Title);
					int loc1 = 0;
					do {

						System.out.println("SELECTING CORE PRODUCT");
						if (loc1 == 0 && value.equalsIgnoreCase("Internet + Voice"))

						{
							try {
								String Title = driver.getTitle();
								System.out.println(Title);
								base.wait(driver, arr_locator[loc1]);
								System.out.println("You have selected Internet + Voice Product");
								base.wait(driver, arr_locator[loc1]);
								base.click_element(driver, arr_locator[loc1]);
								base.wait(driver, arr_locator[loc1]);
								loc1 = 3;
								base.click_element(driver, arr_locator[loc1]);
								reportStep("PASS", "Internet and voice has been selected");
								break;
							} catch (Exception e) {
								reportStep("FAIL", "Internet and voice hasn't been selected");

								e.printStackTrace();
							}
						}

						// Internet
						else if (value.equalsIgnoreCase("Internet")) {
							try {
								loc1 = 1;
								base.wait(driver, arr_locator[loc1]);
								System.out.println("You have selected Internet");
								base.wait(driver, arr_locator[loc1]);
								base.click_element(driver, arr_locator[loc1]);
								base.wait(driver, arr_locator[loc1]);
								loc1 = 3;
								base.click_element(driver, arr_locator[loc1]);
								reportStep("PASS", "Internet has been selected");
								break;
							} catch (Exception e) {
								reportStep("FAIL", "Internet has been selected");
								e.printStackTrace();
							}
						}
						// Voice
						else if (value.equalsIgnoreCase("Voice")) {
							try {
								loc1 = 2;
								base.wait(driver, arr_locator[loc1]);
								System.out.println("You have selected Voice");
								base.wait(driver, arr_locator[loc1]);
								base.click_element(driver, arr_locator[loc1]);
								base.wait(driver, arr_locator[loc1]);
								loc1 = 3;
								base.click_element(driver, arr_locator[loc1]);
								reportStep("PASS", "Voice has been selected");
								break;
							} catch (Exception e) {
								reportStep("FAIL", "Voice hasn't been selected");
								e.printStackTrace();
							}
						}

					} while (loc1 == 3);
				} catch (Exception e) {
					System.out.println(e.getMessage());

				}
			}

			// Speed tile
			else if (action != null && action.equalsIgnoreCase("selectInternetSpeed")) {

				try {
					String Page_Title = driver.getTitle();
					System.out.println("PAGE TITLE" + Page_Title);
					System.out.println("SELECTING INTERNET SPEED");
					String val12 = base.getMapData(LocatorName);
					String[] arr_locator = val12.split("%");
					String[] arr_value = value.split(",");

					int loc1 = 0;
					int data1 = 0;
					data1++;
					Double.toString(data1);
					base.wait(driver, arr_locator[loc1]);
					String tile200 = "//span[text()='" + arr_value[data1] + "']";
					base.wait(driver, arr_locator[loc1]);
					base.click_element(driver, tile200);
					base.wait(driver, arr_locator[loc1]);
					base.click_element(driver, arr_locator[loc1]);
					System.out.println("You have selected " + arr_value[data1] + "speed");
					reportStep("PASS", "You have selected the internet speed");
				} catch (Exception e) {
					reportStep("FAIL", "You haven't selected the internet speed");
					System.out.println(e.getMessage());

				}
			}

			// selectInternetModem
			else if (action != null && action.equalsIgnoreCase("selectInternetModem")) {
				try {
					String Page_Title = driver.getTitle();
					System.out.println("PAGE TITLE" + Page_Title);
					String val13 = base.getMapData(LocatorName);
					String[] arr_locator = val13.split("%");

					int loc1 = 0;
					do {

						System.out.println("SELECTING INTERNET MODEM");
						if (loc1 == 0 && value.equalsIgnoreCase("Modem Rental"))

						{
							try {
								base.wait(driver, arr_locator[loc1]);
								base.wait(driver, arr_locator[loc1]);

								base.click_element(driver, arr_locator[loc1]);
								base.wait(driver, arr_locator[loc1]);
								loc1 = 2;
								base.click_element(driver, arr_locator[loc1]);
								System.out.println("You have selected rental modem");
								reportStep("PASS", "You have selected rental modem");
								break;
							} catch (Exception e) {
								reportStep("FAIL", "You haven't selected rental modem");
								e.printStackTrace();
							}
						} else if (value.equalsIgnoreCase("Use Your Own Modem")) {
							try {
								base.wait(driver, arr_locator[loc1]);
								loc1 = 1;
								base.wait(driver, arr_locator[loc1]);
								base.click_element(driver, arr_locator[loc1]);
								base.wait(driver, arr_locator[loc1]);
								loc1 = 2;
								base.click_element(driver, arr_locator[loc1]);
								System.out.println("You have selected own modem");
								reportStep("PASS", "You have selected own modem");
								break;
							} catch (Exception e) {
								reportStep("FAIL", "You haven't selected own modem");
								e.printStackTrace();
							}
						}

					} while (loc1 == 2);
				} catch (Exception e) {
					System.out.println(e.getMessage());

				}
			}

			// Voice tile

			else if (action != null && action.equalsIgnoreCase("selectVoiceTile")) {
				try {
					String Page_Title = driver.getTitle();
					System.out.println("PAGE TITLE" + Page_Title);
					String val14 = base.getMapData(LocatorName);
					String[] arr_locator = val14.split("%");

					int loc1 = 0;

					do {

						System.out.println("SELECTING VOICE");
						if (loc1 == 0 && value.equalsIgnoreCase("Business lines"))

						{
							try {
								base.wait(driver, arr_locator[loc1]);
								base.wait(driver, arr_locator[loc1]);
								base.click_element(driver, arr_locator[loc1]);
								base.wait(driver, arr_locator[loc1]);
								loc1++;
								base.click_element(driver, arr_locator[loc1]);
								System.out.println("You have selected Voice Business lines");
								reportStep("PASS", "You have selected Voice Business lines");
								break;
							} catch (Exception e) {
								reportStep("FAIL", "You have selected Voice Business lines");
								e.printStackTrace();
							}
						}

					} while (loc1 == 1);
				} catch (Exception e) {
					System.out.println(e.getMessage());

				}
			}

			// Phone number

			else if (action != null && action.equalsIgnoreCase("selectVoicePhoneNumber")) {
				try {
					String Page_Title = driver.getTitle();
					System.out.println("PAGE TITLE" + Page_Title);
					String val15 = base.getMapData(LocatorName);
					String[] arr_locator = val15.split("%");

					int loc1 = 0;
					do {

						System.out.println("SELECTING PHONE NUMBER");
						if (loc1 == 0 && value.equalsIgnoreCase("New Phone Number"))

						{
							try {
								base.wait(driver, arr_locator[loc1]);
								base.wait(driver, arr_locator[loc1]);

								base.click_element(driver, arr_locator[loc1]);
								base.wait(driver, arr_locator[loc1]);
								loc1 = 2;
								base.click_element(driver, arr_locator[loc1]);
								System.out.println("You have selected new phone number");
								reportStep("PASS", "You have selected new phone number");
								break;
							} catch (Exception e) {
								reportStep("FAIL", "You haven't selected new phone number");
								e.printStackTrace();
							}
						} else if (value.equalsIgnoreCase("Existing phone number")) {
							try {
								base.wait(driver, arr_locator[loc1]);
								loc1 = 1;
								base.wait(driver, arr_locator[loc1]);
								base.click_element(driver, arr_locator[loc1]);
								base.wait(driver, arr_locator[loc1]);
								loc1 = 2;
								base.click_element(driver, arr_locator[loc1]);
								System.out.println("You have selected Existing Phone Number");
								reportStep("PASS", "You have selected Existing Phone Number");
								break;
							} catch (Exception e) {
								reportStep("FAIL", "You have selected Existing Phone Number");
								e.printStackTrace();
							}
						}

					} while (loc1 == 2);
				} catch (Exception e) {
					System.out.println(e.getMessage());

				}
			}

			// Checkout

			else if (action != null && action.equalsIgnoreCase("CheckOutPageWithInstallationDate")) {
				try {
					String Page_Title = driver.getTitle();
					System.out.println("PAGE TITLE" + Page_Title);
					String val16 = base.getMapData(LocatorName);
					String[] arr_locator = val16.split("%");
					String[] arr_value = value.split(",");

					int loc = 0;
					int data = 0;

					do {
						System.out.println("You have been moved to checkout page");
						base.wait(driver, arr_locator[loc]);

						if (loc == 0 && data == 0) {
							try {
								System.out.println("Entering Details");
								// first name
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("First Name:" + arr_value[data]);
								loc++;
								data++;
								// last name
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("Last Name:" + arr_value[data]);
								loc++;
								data++;
								// phone number
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("Phone Number:" + arr_value[data]);
								loc++;
								data++;
								// email id
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("Email Id:" + arr_value[data]);
								loc++;
								data++;
								// business name
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("Business Name:" + arr_value[data]);
								loc++;
								data++;
								// tax id
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("Tax Id:" + arr_value[data]);
								loc++;

								// installation date
								base.click_element(driver, arr_locator[loc]);
								loc++;

								// select date
								base.click_element(driver, arr_locator[loc]);
								loc++;
								data++;
								// Driving Directions
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								loc++;
								// scroll terms and conditions
								base.scroll(driver, arr_locator[loc]);
								base.click_element(driver, arr_locator[loc]);
								loc++;
								// Place order
								// base.click_element(driver, arr_locator[loc]);
								// loc++;
								Thread.sleep(10000);
								System.out.println("Completed your Purchase");
								reportStep("PASS", "Completed your Purchase");
								break;
							} catch (Exception e) {
								reportStep("FAIL", "Completed your Purchase");
								e.printStackTrace();
							}

						}
					} while (loc == 10);

				} catch (Exception e) {
					System.out.println(e.getMessage());

				}

			}
			
			// Checkout

						else if (action != null && action.equalsIgnoreCase("CheckOutPageWithInstallationDateWithVOIP")) {
							try {
								String Page_Title = driver.getTitle();
								System.out.println("PAGE TITLE" + Page_Title);
								String val16 = base.getMapData(LocatorName);
								String[] arr_locator = val16.split("%");
								String[] arr_value = value.split("%");

								int loc = 0;
								int data = 0;

								do {
									System.out.println("You have been moved to checkout page");
									base.wait(driver, arr_locator[loc]);

									if (loc == 0 && data == 0) {
										try {
											System.out.println("Entering Details");
											// first name
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("First Name:" + arr_value[data]);
											loc++;
											data++;
											// last name
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("Last Name:" + arr_value[data]);
											loc++;
											data++;
											// phone number
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("Phone Number:" + arr_value[data]);
											loc++;
											data++;
											// email id
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("Email Id:" + arr_value[data]);
											loc++;
											data++;
											// business name
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("Business Name:" + arr_value[data]);
											loc++;
											data++;
											// tax id
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("Tax Id:" + arr_value[data]);
											loc++;

											// installation date
											base.click_element(driver, arr_locator[loc]);
											loc++;

											// select date
											base.click_element(driver, arr_locator[loc]);
											loc++;
											data++;
											// Driving Directions
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											loc++;
											// scroll terms and conditions
											base.scroll(driver, arr_locator[loc]);
											base.click_element(driver, arr_locator[loc]);
											loc++;data++;
											WebElement expected = driver.findElement(By.xpath(arr_locator[loc]));
									        String expectedtext = expected.getText();
									        System.out.println("Search result got from site " + expectedtext);
									        if (expectedtext.equalsIgnoreCase(arr_value[data])) {
									                  System.out.println("verification text matched " +arr_value[data]+  "and"  +expectedtext);
									                  System.out.println("---------------------------------------------------------------------");
									                  //reportStep("PASS", "ENTER YOUR USE MODEM RENTAL");
									        } else {
									                  System.out.println("verification text not matched " +arr_value[data]+ "and"  +expectedtext);
									                  System.out.println("---------------------------------------------------------------------");
									        }
											
											loc++;
											// Place order
											// base.click_element(driver, arr_locator[loc]);
											// loc++;
											Thread.sleep(10000);
											System.out.println("Completed your Purchase");
											reportStep("PASS", "Completed your Purchase");
											break;
										} catch (Exception e) {
											reportStep("FAIL", "Completed your Purchase");
											e.printStackTrace();
										}

									}
								} while (loc == 10);

							} catch (Exception e) {
								System.out.println(e.getMessage());

							}

						}
			
			

			// Summary checkout

			else if (action != null && action.equalsIgnoreCase("SummaryCheckout")) {
				try {
					String Page_Title = driver.getTitle();
					System.out.println("PAGE TITLE" + Page_Title);
					System.out.println("You've entered sumarry page");
					String val17 = base.getMapData(LocatorName);
					base.wait(driver, val17);
					System.out.println("You have moving to checkout");
					base.click_element(driver, val17);
					reportStep("PASS", "You have moving to checkout");
				} catch (Exception e) {
					reportStep("FAIL", "You have moving to checkout");
					System.out.println(e.getMessage());

				}

			}

			// Checkout without Installation date

			else if (action != null && action.equalsIgnoreCase("CheckOutPageWithOutInstallationDate")) {
				try {
					String Page_Title = driver.getTitle();
					System.out.println("PAGE TITLE" + Page_Title);
					String val16 = base.getMapData(LocatorName);
					String[] arr_locator = val16.split("%");
					String[] arr_value = value.split(",");

					int loc = 0;
					int data = 0;

					do {
						System.out.println("You have been moved to checkout page");
						base.wait(driver, arr_locator[loc]);

						if (loc == 0 && data == 0) {
							try {
								System.out.println("Entering Details");
								// first name
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("First Name:" + arr_value[data]);

								loc++;
								data++;
								// last name
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("Last Name:" + arr_value[data]);
								loc++;
								data++;
								// phone number
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("Phone Number:" + arr_value[data]);
								loc++;
								data++;
								// email id
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("Email Id:" + arr_value[data]);
								loc++;
								data++;
								// business name
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("Business Name:" + arr_value[data]);
								loc++;
								data++;
								// tax id
								base.SendKeys(driver, arr_locator[loc], arr_value[data]);
								System.out.println("Tax Id:" + arr_value[data]);
								loc++;
								// data++;
								// scroll terms and conditions
								base.scroll(driver, arr_locator[loc]);
								base.click_element(driver, arr_locator[loc]);
								loc++;
								// Place order mandatory
								// base.click_element(driver, arr_locator[loc]);
								// loc++;
								Thread.sleep(10000);
								System.out.println("Completed your Purchase");
								reportStep("PASS", "Completed your Purchase");
								break;
							} catch (Exception e) {
								reportStep("FAIL", "Not Completed your Purchase");
								e.printStackTrace();
							}

						}
					} while (loc == 10);

				} catch (Exception e) {
					reportStep("FAIL", "Not Completed your Purchase");
					System.out.println(e.getMessage());

				}

			}

			
			// Checkout without Installation date

						else if (action != null && action.equalsIgnoreCase("CheckOutPageWithOutInstallationDateWithoutVOIP")) {
							try {
								String Page_Title = driver.getTitle();
								System.out.println("PAGE TITLE" + Page_Title);
								String val16 = base.getMapData(LocatorName);
								String[] arr_locator = val16.split("%");
								String[] arr_value = value.split(",");

								int loc = 0;
								int data = 0;

								do {
									System.out.println("You have been moved to checkout page");
									base.wait(driver, arr_locator[loc]);

									if (loc == 0 && data == 0) {
										try {
											System.out.println("Entering Details");
											// first name
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("First Name:" + arr_value[data]);

											loc++;
											data++;
											// last name
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("Last Name:" + arr_value[data]);
											loc++;
											data++;
											// phone number
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("Phone Number:" + arr_value[data]);
											loc++;
											data++;
											// email id
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("Email Id:" + arr_value[data]);
											loc++;
											data++;
											// business name
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("Business Name:" + arr_value[data]);
											loc++;
											data++;
											// tax id
											base.SendKeys(driver, arr_locator[loc], arr_value[data]);
											System.out.println("Tax Id:" + arr_value[data]);
											loc++;
											// data++;
											// scroll terms and conditions
											base.scroll(driver, arr_locator[loc]);
											base.click_element(driver, arr_locator[loc]);
											loc++;
											WebElement expected = driver.findElement(By.xpath(arr_locator[loc]));
									        String expectedtext = expected.getText();
									        System.out.println("Search result got from site " + expectedtext);
									        if (expectedtext.equalsIgnoreCase(arr_value[data])) {
									                  System.out.println("verification text matched " +arr_value[data]+  "and"  +expectedtext);
									                  System.out.println("---------------------------------------------------------------------");
									                  //reportStep("PASS", "ENTER YOUR USE MODEM RENTAL");
									        } else {
									                  System.out.println("verification text not matched " +arr_value[data]+ "and"  +expectedtext);
									                  System.out.println("---------------------------------------------------------------------");
									        }
											loc++;
											// Place order mandatory
											// base.click_element(driver, arr_locator[loc]);
											// loc++;
											Thread.sleep(10000);
											System.out.println("Completed your Purchase");
											reportStep("PASS", "Completed your Purchase");
											break;
										} catch (Exception e) {
											reportStep("FAIL", "Not Completed your Purchase");
											e.printStackTrace();
										}

									}
								} while (loc == 10);

							} catch (Exception e) {
								reportStep("FAIL", "Not Completed your Purchase");
								System.out.println(e.getMessage());

							}

						}
			// Close browser

			else if (action != null && action.equalsIgnoreCase("closeBrowser")) {
				try {

					driver.quit();
				} catch (Exception e) {
					System.out.println(e.getMessage());

				}
			}

			// Geolocation

			else if (action != null && action.equalsIgnoreCase("geoLocation")) {
				try {
					String Page_Title = driver.getTitle();
					System.out.println("PAGE TITLE" + Page_Title);
					String val17 = base.getMapData(LocatorName);
					String[] arr_locator = val17.split("%");
					int loc = 0;
					do {
						try {
							base.wait(driver, arr_locator[loc]);
							base.click_element(driver, arr_locator[loc]);
							// base.wait(driver, arr_locator[loc]);
							loc++;
							base.click_element(driver, arr_locator[loc]);
							reportStep("PASS", "CLICK HERE TO ENTER YOUR ADDRESS");
							break;
						} catch (Exception e) {
							reportStep("FAIL", "CLICK HERE NOT ENTER YOUR ADDRESS");

							e.printStackTrace();
						}
					} while (loc == 1);

				} catch (Exception e) {
					reportStep("FAIL", "CLICK HERE NOT ENTER YOUR ADDRESS");
					System.out.println(e.getMessage());

				}
			}

			// summary voice page
			else if (action != null && action.equalsIgnoreCase("SummaryVoiceCheckout")) {
				try {
					System.out.println("You've entered sumarry page");
					String val17 = base.getMapData(LocatorName);
					String[] arr_locator = val17.split("%");
					String[] arr_value = value.split(",");
					int loc = 0;
					int data = 0;
					do {

						if (loc == 0 && data == 0) {

							System.out.println("Excel" + arr_value[data]);
							base.wait(driver, (arr_locator[loc]));
							WebElement bus = driver.findElement(By.xpath(arr_locator[loc]));
							String Busvalue = bus.getText();

							System.out.println(Busvalue);
							// Business line

							if (Busvalue.equals(arr_value[data])) {
								loc++;
								data++;
								WebElement busPrice = driver.findElement(By.xpath(arr_locator[loc]));
								String busPriceText = busPrice.getText();
								if (busPriceText.equals(arr_value[data])) {
									System.out.println("Price for Business Phone line is validated as given by User"
											+ arr_value[data]);
									reportStep("PASS", "ENTER YOUR BUSINESSLINE");

								} else {
									System.out.println("Price for Business Phone lines is not validated");
									reportStep("FAIL", "ENTER YOUR BUSINESSLINE");
								}

								data++;
								// Use Existing Phone Numbers
								if ((arr_value[data]).equals("Addon1")) {
									try {
										loc++;
										data++;
										WebElement exist = driver.findElement(By.xpath(arr_locator[loc]));
										String existvalue = exist.getText();
										if (existvalue.equals(arr_value[data])) {
											System.out.println("Use Existing Phone Numbers validated");
											reportStep("PASS", "ENTER YOUR USE EXISTING PHONE NUMBERS");
										} else {
											System.out.println("not validated");
											reportStep("FAIL", "NOT ENTER YOUR USE EXISTING PHONE NUMBERS");
										}
										loc++;
										loc++;
										base.click_element(driver, arr_locator[loc]);
									} catch (Exception e) {
										reportStep("FAIL", "NOT ENTER YOUR USE EXISTING PHONE NUMBERS");
										e.printStackTrace();
									}
								}

								// New Phone Numbers
								else if ((arr_value[data]).equals("Addon2")) {

									try {
										loc++;
										loc++;
										data++;
										WebElement newPhone = driver.findElement(By.xpath(arr_locator[loc]));
										String newPhonevalue = newPhone.getText();
										if (newPhonevalue.equals(arr_value[data])) {
											System.out.println("Use New Phone Numbers validated");
											reportStep("PASS", "ENTER YOUR NEW PHONE NUMBERS");
										} else {
											reportStep("FAIL", "NOT ENTER YOUR NEW PHONE NUMBERS");
										}
										loc++;
										base.click_element(driver, arr_locator[loc]);
									} catch (Exception e) {
										reportStep("FAIL", "Addons 2 Not Selected");
										e.printStackTrace();
									}
								}

								break;
							}
						}

					} while (loc == 4);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", "Addons 2 Not Selected");

				}

			}
			// SummaryInternetCheckout
			else if (action != null && action.equalsIgnoreCase("SummaryInternetCheckout")) {
				try {
					System.out.println("You've entered sumarry page");
					String val17 = base.getMapData(LocatorName);
					String[] arr_locator = val17.split("%");
					String[] arr_value = value.split(",");
					int loc = 0;
					int data = 0;
					do {

						if (loc == 0 && data == 0) {

							System.out.println("Excel" + arr_value[data]);
							// Internet speed
							WebElement internetSpeed = driver
									.findElement(By.xpath("(//span[text()='" + arr_value[data] + "'])[2]"));
							System.out.println(internetSpeed);
							String internetSpeedText = internetSpeed.getText();
							System.out.println(internetSpeedText);
							data++;
							if (internetSpeedText.equals(arr_value[data])) {
								data++;
								WebElement SpeedPrice = driver
										.findElement(By.xpath("(//p[text()='" + arr_value[data] + "'])[2]"));
								String SpeedPriceText = SpeedPrice.getText();

								if (SpeedPriceText.equals(arr_value[data])) {
									System.out.println("Speed and pricing has been validated");
									reportStep("PASS", "ENTER YOUR USE EXISTING PHONE NUMBERS");
								} else {
									System.out.println("Speed and pricing  is not validated");

								}
							}

							// Use Modem Rental
							data++;
							if (arr_value[data].equals("Addon1")) {

								data++;
								WebElement modemRental = driver.findElement(By.xpath(arr_locator[loc]));
								String modemRentalText = modemRental.getText();
								if (modemRentalText.equals(arr_value[data])) {
									data++;
									loc++;
									WebElement modemRentalPrice = driver.findElement(By.xpath(arr_locator[loc]));
									String modemRentalPriceText = modemRentalPrice.getText();
									System.out.println("Modem Rental pricing given by user" + modemRentalPriceText);
									if (modemRentalPriceText.equals(arr_value[data])) {
										System.out.println("Modem Rental pricing has been validated");
										reportStep("PASS", "ENTER YOUR USE MODEM RENTAL");
									} else {
										System.out.println("Modem Rental pricing is not validated");
										reportStep("FAIL", "NOT ENTER YOUR USE MODEM RENTAL");
									}
								}

								loc++;
								loc++;
								base.click_element(driver, arr_locator[loc]);
								break;
							}
							// OWN Modem
							else if (arr_value[data].equals("Addon2")) {

								loc++;
								loc++;

								data++;
								WebElement ownModem = driver.findElement(By.xpath(arr_locator[loc]));
								String ownModemText = ownModem.getText();
								if (ownModemText.equals(arr_value[data])) {

									System.out.println("Own modem has been validated");
									reportStep("PASS", "ENTER YOUR USE OWN MODEM");
								} else {
									System.out.println("Own modem is not validated");
									reportStep("FAIL", "NOT ENTER YOUR USE OWN MODEM");
								}

								loc++;
								base.click_element(driver, arr_locator[loc]);
								break;
							}

						}
					} while (loc == 3);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", "SummaryInternetCheckout not entered");

				}

			}

			// SummaryBundleCheckout

			else if (action != null && action.equalsIgnoreCase("SummaryBundleCheckout")) {
				try {
					System.out.println("You've entered sumarry page");
					String val17 = base.getMapData(LocatorName);
					String[] arr_locator = val17.split("%");
					String[] arr_value = value.split(",");
					int loc = 0;
					int data = 0;
					do {

						if (loc == 0 && data == 0) {

							System.out.println("Excel" + arr_value[data]);

							// Internet speed
							WebElement internetSpeed = driver
									.findElement(By.xpath("(//span[text()='" + arr_value[data] + "'])[2]"));
							System.out.println(internetSpeed);
							String internetSpeedText = internetSpeed.getText();
							System.out.println(internetSpeedText);
							data++;
							// Internet

							if (internetSpeedText.equals(arr_value[data])) {
								data++;
								WebElement SpeedPrice = driver
										.findElement(By.xpath("(//p[text()='" + arr_value[data] + "'])[2]"));
								String SpeedPriceText = SpeedPrice.getText();

								if (SpeedPriceText.equals(arr_value[data])) {
									System.out.println("Speed and pricing has been validated" + internetSpeedText
											+ SpeedPriceText);
									reportStep("PASS", "ENTER YOUR SUMMARYBUNDLECHECKOUT");
								} else {
									System.out.println("Speed and pricing  is not validated");
									reportStep("FAIL", "NOT ENTER YOUR SUMMARYBUNDLECHECKOUT");
								}
							}

							// Use Modem Rental
							data++;
							if (arr_value[data].equals("Addon1")) {

								data++;
								WebElement modemRental = driver.findElement(By.xpath(arr_locator[loc]));
								String modemRentalText = modemRental.getText();
								if (modemRentalText.equals(arr_value[data])) {
									data++;
									loc++;
									WebElement modemRentalPrice = driver.findElement(By.xpath(arr_locator[loc]));
									String modemRentalPriceText = modemRentalPrice.getText();
									System.out.println("Modem Rental pricing given by user" + modemRentalPriceText);
									if (modemRentalPriceText.equals(arr_value[data])) {
										System.out.println("Modem Rental pricing has been validated");
										base.scroll(driver, arr_locator[loc]);
										reportStep("PASS", "ENTER YOUR USE MODEM RENTAL");
									} else {
										System.out.println("Modem Rental pricing is not validated");
										reportStep("FAIL", "NOT ENTER YOUR USE MODEM RENTAL");
									}

								}

							}
							// OWN Modem
							else if (arr_value[data].equals("Addon2")) {

								loc++;

								data++;
								WebElement ownModem = driver.findElement(By.xpath(arr_locator[loc]));
								String ownModemText = ownModem.getText();
								System.out.println(ownModemText);
								if (ownModemText.equals(arr_value[data])) {

									System.out.println("Own modem has been validated");
									reportStep("PASS", "ENTER YOUR USE OWN MODEM");

								} else {
									System.out.println("Own modem is not validated");
									reportStep("FAIL", "ENTER YOUR USE OWN MODEM");
								}
							}
							loc++;
						
							System.out.println(loc);
							System.out.println("hai");
							data++;
							System.out.println(data);
							// phone
							if (loc == 2 && data == 6) {
								System.out.println("welcome");
								Thread.sleep(5000);
								base.scroll(driver, arr_locator[loc]);
								System.out.println("Excel" + arr_value[data]);
								base.wait(driver, (arr_locator[loc]));
								WebElement bus = driver.findElement(By.xpath(arr_locator[loc]));
								String Busvalue = bus.getText();
								System.out.println(Busvalue);

								if (Busvalue.equals(arr_value[data])) {
									loc++;
									data++;
									Thread.sleep(6000);
									WebElement busPrice = driver.findElement(By.xpath(arr_locator[loc]));
									String busPriceText = busPrice.getText();
									if (busPriceText.equals(arr_value[data])) {
										System.out.println("Price for Business Phone line is validated as given by User"
												+ arr_value[data]);
										reportStep("PASS", "ENTER YOUR BUSINESS PHONE LINE");

									} else {
										System.out.println("Price for Business Phone lines is not validated");
										reportStep("FAIL", "NOT ENTER YOUR BUSINESS PHONE LINE");

									}

									data++;
									// Use Existing Phone Numbers
									if ((arr_value[data]).equals("Addon1")) {
										loc++;
										data++;
										WebElement exist = driver.findElement(By.xpath(arr_locator[loc]));
										String existvalue = exist.getText();
										if (existvalue.equals(arr_value[data])) {
											System.out.println("Use Existing Phone Numbers validated");
											reportStep("PASS", "ENTER YOUR USE EXISTING PHONE NUMBERS");

										} else {
											System.out.println("not validated");
											reportStep("FAIL", "ENTER YOUR USE EXISTING PHONE NUMBERS");

										}
										System.out.println("loc value" +loc);
										loc++;
										loc++;
										base.wait(driver, arr_locator[loc]);
										Thread.sleep(2000);
										base.click_element(driver,arr_locator[loc]);
										System.out.println("checkout clicked");
									}
									// New Phone Numbers
									else if ((arr_value[data]).equals("Addon2")) {

										try {
											loc++;
											loc++;
											data++;
											WebElement newPhone = driver.findElement(By.xpath(arr_locator[loc]));
											String newPhonevalue = newPhone.getText();
											if (newPhonevalue.equals(arr_value[data])) {
												System.out.println("Use New Phone Numbers validated");
												reportStep("PASS", "ENTER YOUR USE NEW PHONE NUMBERS");

											} else {
												System.out.println("not validated");
												reportStep("FAIL", "NOT ENTER YOUR USE NEW PHONE NUMBERS");

											}
											loc++;
											base.wait(driver, arr_locator[loc]);
											Thread.sleep(2000);
											base.click_element(driver, arr_locator[loc]);
										} catch (Exception e) {
											reportStep("FAIL", "ADDONS 2 NOT ENTERED");
											e.printStackTrace();
										}
									}
								}

								break;

							}

						}
					} while (loc == 7);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", "NOT ENTER YOUR USE NEW PHONE NUMBERS");
				}

			}

			// VoiceCartContainer
			else if (action != null && action.equalsIgnoreCase("VoiceCartContainer")) {
				try {
					System.out.println("You've entered voice cart container");
					String val17 = base.getMapData(LocatorName);
					String[] arr_locator = val17.split("%");
					String[] arr_value = value.split(",");
					int loc = 0;
					int data = 0;
					do {

						if (loc == 0 && data == 0) {
							base.wait(driver, arr_locator[loc]);
							base.mousehover(driver, arr_locator[loc]);
							loc++;
							System.out.println("Value given by user " + arr_value[data]);
							base.wait(driver, (arr_locator[loc]));
							WebElement bus = driver.findElement(By.xpath(arr_locator[loc]));
							String Busvalue = bus.getText();

							System.out.println(Busvalue);
							// Business line

							if (Busvalue.equals(arr_value[data])) {
								loc++;
								data++;
								WebElement busPrice = driver.findElement(By.xpath(arr_locator[loc]));
								String busPriceText = busPrice.getText();
								if (busPriceText.equals(arr_value[data])) {
									System.out.println("Price for Business Phone line is validated as given by User"
											+ arr_value[data]);
									reportStep("PASS", "ENTER YOUR USE BUSINESS LINE");

								} else {
									System.out.println("Price for Business Phone lines is not validated");
									reportStep("FAIL", "BUSINESS LINE PRICE MISMATCH BETWEEN GIVEN AND ACTUAL");

								}

								data++;
								// Use Existing Phone Numbers
								if ((arr_value[data]).equals("Addon1")) {
									loc++;
									data++;
									WebElement exist = driver.findElement(By.xpath(arr_locator[loc]));
									String existvalue = exist.getText();
									if (existvalue.equals(arr_value[data])) {
										System.out.println("Use Existing Phone Numbers validated");
										reportStep("PASS", "ENTER YOUR USE EXISTING PHONE NUMBERS");

									} else {
										System.out.println("not validated");
										reportStep("FAIL", "NOT ENTER YOUR USE EXISTING PHONE NUMBERS");
									}
									loc++;
									loc++;
									base.mousehover(driver, arr_locator[loc]);
								}

								// New Phone Numbers
								else if ((arr_value[data]).equals("Addon2")) {

									loc++;
									loc++;
									data++;
									WebElement newPhone = driver.findElement(By.xpath(arr_locator[loc]));
									String newPhonevalue = newPhone.getText();
									if (newPhonevalue.equals(arr_value[data])) {
										System.out.println("Use New Phone Numbers validated");
										reportStep("PASS", "ENTER YOUR USE NEW PHONE NUMBERS");
									} else {
										System.out.println("not validated");
										reportStep("FAIL", "ENTER YOUR USE NEW PHONE NUMBERS");
									}
									loc++;
									base.mousehover(driver, arr_locator[loc]);
								}

								break;
							}
						}

					} while (loc == 5);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", " YOUR USE NEW PHONE NUMBERS " + e);

				}

			}

			// InternetCartContainer
			else if (action != null && action.equalsIgnoreCase("InternetCartContainer")) {
				try {
					System.out.println("You've entered Internet Cart Container");
					String val17 = base.getMapData(LocatorName);
					String[] arr_locator = val17.split("%");
					String[] arr_value = value.split(",");
					int loc = 0;
					int data = 0;
					do {

						if (loc == 0 && data == 0) {
							base.wait(driver, arr_locator[loc]);
							base.mousehover(driver, arr_locator[loc]);
							loc++;
							System.out.println("Value given by user " + arr_value[data]);

							// Internet speed
							WebElement internetSpeed = driver
									.findElement(By.xpath("(//span[text()='" + arr_value[data] + "'])[1]"));
							System.out.println(internetSpeed);
							String internetSpeedText = internetSpeed.getText();
							System.out.println(internetSpeedText);
							data++;
							if (internetSpeedText.equals(arr_value[data])) {
								data++;
								WebElement SpeedPrice = driver
										.findElement(By.xpath("(//p[text()='" + arr_value[data] + "'])[1]"));
								String SpeedPriceText = SpeedPrice.getText();

								if (SpeedPriceText.equals(arr_value[data])) {
									System.out.println("Speed and pricing has been validated");
									reportStep("PASS", "ENTER YOUR INTERNETCARTCONTAINER");
								} else {
									System.out.println("Speed and pricing  is not validated");
									reportStep("FAIL", "NOT ENTER YOUR INTERNETCARTCONTAINER");

								}
							}

							// Use Modem Rental
							data++;
							if (arr_value[data].equals("Addon1")) {

								data++;
								WebElement modemRental = driver.findElement(By.xpath(arr_locator[loc]));
								String modemRentalText = modemRental.getText();
								if (modemRentalText.equals(arr_value[data])) {
									data++;
									loc++;
									WebElement modemRentalPrice = driver.findElement(By.xpath(arr_locator[loc]));
									String modemRentalPriceText = modemRentalPrice.getText();
									System.out.println("Modem Rental pricing given by user" + modemRentalPriceText);
									if (modemRentalPriceText.equals(arr_value[data])) {
										System.out.println("Modem Rental pricing has been validated");
										reportStep("PASS", "ENTER YOUR USE MODEM RENTAL");
									} else {
										System.out.println("Modem Rental pricing is not validated");
										reportStep("FAIL", "NOT ENTER YOUR USE MODEM RENTAL");

									}
								}

								loc++;
								loc++;
								base.mousehover(driver, arr_locator[loc]);
								break;
							}
							// OWN Modem
							else if (arr_value[data].equals("Addon2")) {

								loc++;
								loc++;

								data++;
								WebElement ownModem = driver.findElement(By.xpath(arr_locator[loc]));
								String ownModemText = ownModem.getText();
								if (ownModemText.equals(arr_value[data])) {

									System.out.println("Own modem has been validated");
									reportStep("PASS", "ENTER YOUR USE OWN MODEM RENTAL");
								} else {
									System.out.println("Own modem is not validated");
									reportStep("FAIL", "NOT ENTER YOUR USE OWN MODEM RENTAL");
								}
								loc++;
								base.mousehover(driver, arr_locator[loc]);

								break;
							}

						}
					} while (loc == 4);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("Fail", "Not ENTER YOUR USE OWN MODEM RENTAL " + e);

				}

			}

			// BundleCartContainer

			else if (action != null && action.equalsIgnoreCase("BundleCartContainer")) {
				try {
					System.out.println("You've entered sumarry page");
					String val17 = base.getMapData(LocatorName);
					String[] arr_locator = val17.split("%");
					String[] arr_value = value.split(",");
					int loc = 0;
					int data = 0;
					do {

						if (loc == 0 && data == 0) {

							base.wait(driver, arr_locator[loc]);
							base.mousehover(driver, arr_locator[loc]);
							loc++;
							System.out.println("Value given by user " + arr_value[data]);

							// Internet speed
							WebElement internetSpeed = driver
									.findElement(By.xpath("(//span[text()='" + arr_value[data] + "'])[1]"));
							System.out.println(internetSpeed);
							String internetSpeedText = internetSpeed.getText();
							System.out.println(internetSpeedText);
							data++;
							// Internet

							if (internetSpeedText.equals(arr_value[data])) {
								data++;
								WebElement SpeedPrice = driver
										.findElement(By.xpath("(//p[text()='" + arr_value[data] + "'])[1]"));
								String SpeedPriceText = SpeedPrice.getText();

								if (SpeedPriceText.equals(arr_value[data])) {
									System.out.println("Speed and pricing has been validated" + internetSpeedText
											+ SpeedPriceText);
									reportStep("PASS", "ENTER YOUR INTERNET SPEED");
								} else {
									System.out.println("Speed and pricing  is not validated");
									reportStep("Fail", "Not ENTER YOUR INTERNET SPEED");
								}
							}

							// Use Modem Rental
							data++;
							if (arr_value[data].equals("Addon1")) {

								data++;
								WebElement modemRental = driver.findElement(By.xpath(arr_locator[loc]));
								String modemRentalText = modemRental.getText();
								if (modemRentalText.equals(arr_value[data])) {
									data++;
									loc++;
									WebElement modemRentalPrice = driver.findElement(By.xpath(arr_locator[loc]));
									String modemRentalPriceText = modemRentalPrice.getText();
									System.out.println("Modem Rental pricing given by user" + modemRentalPriceText);
									if (modemRentalPriceText.equals(arr_value[data])) {
										System.out.println("Modem Rental pricing has been validated");
										base.scroll(driver, arr_locator[loc]);
										reportStep("PASS", "ENTER YOUR MODEM RENTAL");
									} else {
										System.out.println("Modem Rental pricing is not validated");
										reportStep("FAIL", "NOT ENTER YOUR MODEM RENTAL");
									}

								}

							}
							// OWN Modem
							else if (arr_value[data].equals("Addon2")) {

								loc++;

								data++;
								WebElement ownModem = driver.findElement(By.xpath(arr_locator[loc]));
								String ownModemText = ownModem.getText();
								System.out.println(ownModemText);
								if (ownModemText.equals(arr_value[data])) {

									System.out.println("Own modem has been validated");
									reportStep("PASS", "ENTER YOUR OWN MODEM");

								} else {
									System.out.println("Own modem is not validated");
									reportStep("FAIL", "NOT ENTER YOUR MODEM RENTAL");
								}
							}
							loc++;
							
							System.out.println(loc);
							System.out.println("hai");
							data++;
							System.out.println(data);
							// phone
							if (loc == 3 && data == 6) {
								System.out.println("welcome");
								Thread.sleep(5000);
								base.scroll(driver, arr_locator[loc]);
								System.out.println("Excel" + arr_value[data]);
								base.wait(driver, (arr_locator[loc]));
								WebElement bus = driver.findElement(By.xpath(arr_locator[loc]));
								String Busvalue = bus.getText();
								System.out.println(Busvalue);

								if (Busvalue.equals(arr_value[data])) {
									loc++;
									data++;
									Thread.sleep(3000);
									WebElement busPrice = driver.findElement(By.xpath(arr_locator[loc]));
									String busPriceText = busPrice.getText();
									if (busPriceText.equals(arr_value[data])) {
										System.out.println("Price for Business Phone line is validated as given by User"
												+ arr_value[data]);
										reportStep("PASS", "ENTER YOUR bUSINESS PHONE LINE");

									} else {
										System.out.println("Price for Business Phone lines is not validated");
										reportStep("FAIL", "NOT ENTER YOUR bUSINESS PHONE LINE");

									}

									data++;
									// Use Existing Phone Numbers
									if ((arr_value[data]).equals("Addon1")) {
										loc++;
										data++;
										WebElement exist = driver.findElement(By.xpath(arr_locator[loc]));
										String existvalue = exist.getText();
										if (existvalue.equals(arr_value[data])) {
											System.out.println("Use Existing Phone Numbers validated");
											reportStep("PASS", "ENTER YOUR USE EXISTING PHONE NUMBER");

										} else {
											System.out.println("not validated");
											reportStep("FAIL", "NOT ENTER YOUR USE EXISTING PHONE NUMBER");

										}
										loc++;
										loc++;
										base.wait(driver, arr_locator[loc]);
										
										base.mousehover(driver, arr_locator[loc]);
									}
									// New Phone Numbers
									else if ((arr_value[data]).equals("Addon2")) {

										loc++;
										loc++;
										data++;
										base.wait(driver, arr_locator[loc]);
										WebElement newPhone = driver.findElement(By.xpath(arr_locator[loc]));
										String newPhonevalue = newPhone.getText();
										if (newPhonevalue.equals(arr_value[data])) {
											System.out.println("Use New Phone Numbers validated");
											reportStep("PASS", "ENTER YOUR USE NEW PHONE NUMBER");
										} else {
											System.out.println("not validated");
											reportStep("FAIL", "NOT ENTER YOUR USE NEW PHONE NUMBER");
										}
										loc++;
										base.wait(driver, arr_locator[loc]);
										
										base.mousehover(driver, arr_locator[loc]);
//										base.click_element(driver, arr_locator[loc]);
										Thread.sleep(2000);
									}
								}

								break;

							}

						}
					} while (loc == 8);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", "NOT ENTER YOUR USE NEW PHONE NUMBER");

				}

			}
			// Confirmation voice page
			else if (action != null && action.equalsIgnoreCase("ConfirmationVoice")) {
				try {
					System.out.println("You've been moved to confirmation page");
					String val17 = base.getMapData(LocatorName);
					String[] arr_locator = val17.split("%");
					String[] arr_value = value.split(",");
					int loc = 0;
					int data = 0;
					do {

						if (loc == 0 && data == 0) {

							System.out.println("Voiceinconfirmationpage" + arr_value[data]);
							base.wait(driver, (arr_locator[loc]));
							WebElement bus = driver.findElement(By.xpath(arr_locator[loc]));
							String Busvalue = bus.getText();

							System.out.println(Busvalue);
							// Business line

							if (Busvalue.equals(arr_value[data])) {
								loc++;
								data++;
								WebElement busPrice = driver.findElement(By.xpath(arr_locator[loc]));
								String busPriceText = busPrice.getText();
								if (busPriceText.equals(arr_value[data])) {
									System.out.println("Price for Business Phone line is validated as given by User"
											+ arr_value[data]);
									reportStep("PASS", "ENTER YOUR BUSINESS LINE");

								} else {
									System.out.println("Price for Business Phone lines is not validated");
									reportStep("FAIL", "NOT ENTER YOUR BUSINESS LINE");
								}

								data++;
								// Use Existing Phone Numbers
								if ((arr_value[data]).equals("Addon1")) {
									loc++;
									data++;
									WebElement exist = driver.findElement(By.xpath(arr_locator[loc]));
									String existvalue = exist.getText();
									if (existvalue.equals(arr_value[data])) {
										System.out.println("Use Existing Phone Numbers validated");
										reportStep("PASS", "ENTER YOUR USE EXISTING PHONE NUMBERS");

									} else {
										System.out.println("not validated");
										reportStep("FAIL", "NOT ENTER YOUR USE EXISTING PHONE NUMBERS");

									}
									loc++;
									loc++;
									base.click_element(driver, arr_locator[loc]);
								}

								// New Phone Numbers
								else if ((arr_value[data]).equals("Addon2")) {

									loc++;
									loc++;
									data++;
									WebElement newPhone = driver.findElement(By.xpath(arr_locator[loc]));
									String newPhonevalue = newPhone.getText();
									if (newPhonevalue.equals(arr_value[data])) {
										System.out.println("Use New Phone Numbers validated");
										reportStep("PASS", "ENTER YOUR USE NEW PHONE NUMBERS");

									} else {
										System.out.println("not validated");
										reportStep("FAIL", "NOT ENTER YOUR USE NEW PHONE NUMBERS");

									}
								}

								break;
							}
						}

					} while (loc == 3);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", "NOT ENTER YOUR USE NEW PHONE NUMBERS");

				}

			}
			// SMB mywin
			else if (action != null && action.equalsIgnoreCase("SmbMyAccount")) {

				try {
					String locatorlink = base.getMapData(LocatorName);
					String[] arr_locator = locatorlink.split("%");

					// String[] arr_value = value.split(",");
					int loc = 0;
					System.out.println(arr_locator[loc]);
					// int data = 0;
					System.out.println("hai inside my win");
					WebElement account = driver.findElement(By.xpath(arr_locator[loc]));
					System.out.println(account);
					Thread.sleep(5000);
					loc++;
					System.out.println("next line");
					Actions builder = new Actions(driver);
					Thread.sleep(5000);
					WebElement account1 = driver.findElement(By.xpath(arr_locator[loc]));
					builder.moveToElement(account1).perform();
					// Thread.sleep(5000);
					List<WebElement> elements = account.findElements(By.tagName("a"));

					int size = elements.size();
					System.out.println(size);
					String ParentWindowHandle = driver.getWindowHandle();
					// Thread.sleep(5000);;
					JavascriptExecutor js = (JavascriptExecutor) driver;
					for (int j = 0; j < size; j++) {
						System.out.println(".........Inside For loop..........");
						System.out.println("List of sub-menus is: " + elements.get(j));
						String hrefLink = elements.get(j).getAttribute("href");
						System.out.println("Href Value is " + hrefLink);
						Thread.sleep(5000);
						js.executeScript("window.open('" + hrefLink + "','_blank');");

					}
					// Thread.sleep(5000);
					System.out.println("Control Came out of the For Loop : ======>");
					Set<String> allWindowHandles = driver.getWindowHandles();
					int handleCount = 0;
					Thread.sleep(20000);
					System.out.println("My Parent Window handle - > " + ParentWindowHandle);
					driver.switchTo().window(ParentWindowHandle);

					List<String> list = new ArrayList<String>();
					for (String Invhandle : allWindowHandles) {
						handleCount++;
						System.out.println("Currently Window handle before IF matches is  - > " + Invhandle
								+ "Handler Count is: " + handleCount);
						if (!ParentWindowHandle.equals(Invhandle)) {
							list.add(Invhandle);
						}
					}

					int tempH, valueiter;
					String expectedURL = smblink.smbLinkData(value);
					// String[] arr_locator = val10.split("%");
					// String[] arr_value = value.split(",");
					// int loc = 0;
					// String expectedURL = smblink.getLinkData(value);
					System.out.println("values from excel" + value);
					System.out.println("Expected url from excel" + expectedURL);
					String[] splittedvalues = expectedURL.split("@");
					/*
					 * int data=0;
					 * 
					 * System.out.println(splittedvalues[data]); data++;
					 * System.out.println(splittedvalues[data]); data++;
					 * System.out.println(splittedvalues[data]); data++;
					 */
					// System.out.println(splittedvalues[data]);
					for (tempH = list.size() - 1, valueiter = 0; tempH >= 0
							&& valueiter <= splittedvalues.length; tempH--, valueiter++) {
						driver.switchTo().window(list.get(tempH));

						System.out.println("Control Came inside IF Loop after when PH not-matches : ======>");
						String childURl = driver.getCurrentUrl();
						if (splittedvalues[valueiter].contains(childURl)) {
							System.out.println("Expected URL " + splittedvalues[valueiter]);
							System.out.println("Actual URL from site" + childURl);
							System.out.println("URL status is PASS");
						} else {
							System.out.println("Expected URL " + splittedvalues[valueiter]);
							System.out.println("Actual URL from site" + childURl);
							System.out.println("URL status is FAIL");
						}

						driver.close();
					}
					driver.switchTo().window(ParentWindowHandle);
				} catch (Exception e) {
					reportStep("FAIL", "SMB Account not selected");
					e.printStackTrace();
				}

			}
			// Products header
			else if (action != null && action.equalsIgnoreCase("ProductsHeader")) {

				try {
					String locatorlink = base.getMapData(LocatorName);
					String[] arr_locator = locatorlink.split("%");
					int loc = 0;
					System.out.println(arr_locator[loc]);
					base.wait(driver, arr_locator[loc]);
					base.click_element(driver, arr_locator[loc]);
					loc++;
					base.wait(driver, arr_locator[loc]);
					base.click_element(driver, arr_locator[loc]);
					loc++;
					WebElement InternetList = driver.findElement(By.xpath(arr_locator[loc]));
					System.out.println(InternetList);
					Thread.sleep(5000);
					loc++;
					System.out.println("next line");
					// Actions builder = new Actions(driver);
					// Thread.sleep(5000);
					// WebElement account1 = driver.findElement(By.xpath(arr_locator[loc]));
					// builder.moveToElement(account1).perform();
					// Thread.sleep(5000);
					List<WebElement> elements = InternetList.findElements(By.tagName("a"));

					int size = elements.size();
					System.out.println(size);
					String ParentWindowHandle = driver.getWindowHandle();
					// Thread.sleep(5000);;
					JavascriptExecutor js = (JavascriptExecutor) driver;
					for (int s = 0; s < size; s++) {
						System.out.println(".........Inside For loop..........");
						System.out.println("List of sub-menus is: " + elements.get(s));
						String hrefLink = elements.get(s).getAttribute("href");
						System.out.println("Href Value is " + hrefLink);
						Thread.sleep(5000);
						js.executeScript("window.open('" + hrefLink + "','_blank');");

					}
					// Thread.sleep(5000);
					System.out.println("Control Came out of the For Loop : ======>");
					Set<String> allWindowHandles = driver.getWindowHandles();
					int handleCount = 0;
					Thread.sleep(20000);
					System.out.println("My Parent Window handle - > " + ParentWindowHandle);
					driver.switchTo().window(ParentWindowHandle);

					List<String> list = new ArrayList<String>();
					for (String Invhandle : allWindowHandles) {
						handleCount++;
						System.out.println("Currently Window handle before IF matches is  - > " + Invhandle
								+ "Handler Count is: " + handleCount);
						if (!ParentWindowHandle.equals(Invhandle)) {
							list.add(Invhandle);
						}
					}

					int tempH, valueiter;
					String expectedURL = smblink.smbLinkData(value);

					System.out.println("values from excel" + value);
					System.out.println("Expected url from excel" + expectedURL);
					String[] splittedvalues = expectedURL.split("@");

					for (tempH = list.size() - 1, valueiter = 0; tempH >= 0
							&& valueiter <= splittedvalues.length; tempH--, valueiter++) {
						driver.switchTo().window(list.get(tempH));

						System.out.println("Control Came inside IF Loop after when PH not-matches : ======>");
						String childURl = driver.getCurrentUrl();
						if (splittedvalues[valueiter].contains(childURl)) {
							System.out.println("Expected URL " + splittedvalues[valueiter]);
							System.out.println("Actual URL from site" + childURl);
							System.out.println("URL status is PASS");
						} else {
							System.out.println("Expected URL " + splittedvalues[valueiter]);
							System.out.println("Actual URL from site" + childURl);
							System.out.println("URL status is FAIL");
						}

						driver.close();
					}
					driver.switchTo().window(ParentWindowHandle);
				} catch (Exception e) {
					reportStep("FAIL", "SMB Product header not selected");
					e.printStackTrace();
				}

			}
			// Resurces
			else if (action != null && action.equalsIgnoreCase("ResourcesHeader")) {

				try {
					String locatorlink = base.getMapData(LocatorName);
					String[] arr_locator = locatorlink.split("%");
					int loc = 0;
					System.out.println(arr_locator[loc]);
					base.wait(driver, arr_locator[loc]);
					base.click_element(driver, arr_locator[loc]);
					loc++;
					base.wait(driver, arr_locator[loc]);
					// base.click_element(driver, arr_locator[loc]);
					// loc++;
					WebElement InternetList = driver.findElement(By.xpath(arr_locator[loc]));
					System.out.println(InternetList);
					Thread.sleep(5000);
					loc++;
					System.out.println("next line");

					List<WebElement> elements = InternetList.findElements(By.tagName("a"));

					int size = elements.size();
					System.out.println(size);
					String ParentWindowHandle = driver.getWindowHandle();
					// Thread.sleep(5000);;
					JavascriptExecutor js = (JavascriptExecutor) driver;
					for (int s = 0; s < size; s++) {
						System.out.println(".........Inside For loop..........");
						System.out.println("List of sub-menus is: " + elements.get(s));
						String hrefLink = elements.get(s).getAttribute("href");
						System.out.println("Href Value is " + hrefLink);
						Thread.sleep(5000);
						js.executeScript("window.open('" + hrefLink + "','_blank');");

					}
					// Thread.sleep(5000);
					System.out.println("Control Came out of the For Loop : ======>");
					Set<String> allWindowHandles = driver.getWindowHandles();
					int handleCount = 0;
					Thread.sleep(20000);
					System.out.println("My Parent Window handle - > " + ParentWindowHandle);
					driver.switchTo().window(ParentWindowHandle);

					List<String> list = new ArrayList<String>();
					for (String Invhandle : allWindowHandles) {
						handleCount++;
						System.out.println("Currently Window handle before IF matches is  - > " + Invhandle
								+ "Handler Count is: " + handleCount);
						if (!ParentWindowHandle.equals(Invhandle)) {
							list.add(Invhandle);
						}
					}

					int tempH, valueiter;
					String expectedURL = smblink.smbLinkData(value);

					System.out.println("values from excel" + value);
					System.out.println("Expected url from excel" + expectedURL);
					String[] splittedvalues = expectedURL.split("@");

					for (tempH = list.size() - 1, valueiter = 0; tempH >= 0
							&& valueiter <= splittedvalues.length; tempH--, valueiter++) {
						driver.switchTo().window(list.get(tempH));

						System.out.println("Control Came inside IF Loop after when PH not-matches : ======>");
						String childURl = driver.getCurrentUrl();
						if (splittedvalues[valueiter].contains(childURl)) {
							System.out.println("Expected URL " + splittedvalues[valueiter]);
							System.out.println("Actual URL from site" + childURl);
							System.out.println("URL status is PASS");

						} else {
							System.out.println("Expected URL " + splittedvalues[valueiter]);
							System.out.println("Actual URL from site" + childURl);
							System.out.println("URL status is FAIL");

						}

						driver.close();
					}
					driver.switchTo().window(ParentWindowHandle);
					reportStep("PASS", "Resource header is selected");
				} catch (Exception e) {
					reportStep("FAIL", "Resource header not selected");
					e.printStackTrace();
				}

			}
			// CommunityHeader
			else if (action != null && action.equalsIgnoreCase("CommunityHeader")) {
				try {
					base.wait(driver, LocatorName);
					WebElement link = driver.findElement(By.xpath(LocatorName));
					List<WebElement> elements = link.findElements(By.tagName("a"));
					int size = elements.size();
					System.out.println(size);
					String ParentWindowHandle = driver.getWindowHandle();
					for (int a = 0; a < size; a++) {
						String keys = Keys.chord(Keys.CONTROL, Keys.ENTER);
						Thread.sleep(3000);
						elements.get(a).sendKeys(keys);

					}
					Thread.sleep(5000);
					System.out.println("Control Came out of the For Loop : ======>");
					Set<String> allWindowHandles = driver.getWindowHandles();
					int handleCount = 0;
					Thread.sleep(20000);
					System.out.println("My Parent Window handle - > " + ParentWindowHandle);
					driver.switchTo().window(ParentWindowHandle);

					List<String> list = new ArrayList<String>();
					for (String Invhandle : allWindowHandles) {
						handleCount++;
						System.out.println("Currently Window handle before IF matches is  - > " + Invhandle
								+ "Handler Count is: " + handleCount);
						if (!ParentWindowHandle.equals(Invhandle)) {
							list.add(Invhandle);
						}
					}

					int tempH, valueiter;
					String expectedURL = smblink.smbLinkData(value);
					System.out.println("values from excel" + value);
					String[] splittedvalues = expectedURL.split("@");
					for (tempH = list.size() - 1, valueiter = 0; tempH >= 0
							&& valueiter <= splittedvalues.length; tempH--, valueiter++) {
						driver.switchTo().window(list.get(tempH));

						System.out.println("Control Came inside IF Loop after when PH not-matches : ======>");
						System.out.println("splitted values" + splittedvalues[valueiter]);
						String childURl = driver.getCurrentUrl();
						if (splittedvalues[valueiter].equals(childURl)) {
							System.out.println("Expected URL " + splittedvalues[valueiter]);
							System.out.println("Actual URL from site" + childURl);
							System.out.println("URL status is PASS");

						} else {
							System.out.println("Expected URL " + splittedvalues[valueiter]);
							System.out.println("Actual URL from site" + childURl);
							System.out.println("URL status is FAIL");

						}

						URL u = new URL(splittedvalues[valueiter]);
						HttpURLConnection hc = (HttpURLConnection) u.openConnection();
						hc.setRequestMethod("HEAD");
						hc.connect();
						int rc = hc.getResponseCode();
						System.out.println(rc);
						String rm = hc.getResponseMessage();
						if (rc == 200) {
							System.out.println(childURl + "is valid");

						} else {
							System.out.println(childURl + "is a Broken link");

						}
						driver.close();
					}
					driver.switchTo().window(ParentWindowHandle);
					reportStep("PASS", "Resource header is selected");
				} catch (Exception e) {
					reportStep("FAIL", "Resource header not selected");
					e.printStackTrace();
				}

			}

			//
			// form validation

			else if (action != null && action.equalsIgnoreCase("form")) {
				try {
					String data = base.getMapData(LocatorName);
					System.out.println("the data is " + data);
					WebElement findElement = driver.findElement(By.xpath(data));
					System.out.println("the findelement is " + findElement);
					System.out.println("+++++++++++++++++++ " + findElement.getText());
					String text = findElement.getText();
					System.out.println("findelement gettext " + text);
					if (text.equalsIgnoreCase(value)) {
						System.out.println("form is valid");
						System.out.println("the add value" + value);
						reportStep("PASS", "Font Header idselected");
					} else {
						System.out.println("form is not valid");
						System.out.println("the add value" + value);
						reportStep("FAIL", "Font Header not selected");

					}
				} catch (Exception e) {
					reportStep("FAIL", "Font Header not selected");
					e.printStackTrace();
				}

			}

			// SMB Top search
			else if (action != null && action.equalsIgnoreCase("SMBTopSearch")) {
				try {
					String linkData = base.getMapData(LocatorName);
					String[] arr_locator = linkData.split("%");
					String[] arr_value = value.split("@");
					int loc = 0;
					int data = 0;
					System.out.println(arr_value[data]);
					base.wait(driver, arr_locator[loc]);
					// clicking on top search to provide search data
					base.click_element(driver, arr_locator[loc]);
					loc++;
					base.wait(driver, arr_locator[loc]);
					base.SendKeys(driver, arr_locator[loc], arr_value[data]);
					loc++;
					base.click_element(driver, arr_locator[loc]);
					// verify page count
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					// verify search url
					data++;
					String currentUrl = driver.getCurrentUrl();
					System.out.println("URL from site " + currentUrl);

					boolean equals = currentUrl.equals(arr_value[data]);
					System.out.println("Given url matches " + equals);
					if (equals == true) {

						System.out.println("top search URL matched");

					} else {
						System.out.println("top search URL does not match");

					}
					// verify first link
					loc++;
					data++;
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify internet page text and back
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					driver.navigate().back();
					// navigation 2
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify next and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify last and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify Back and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify First and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					Thread.sleep(3000);
					reportStep("PASS", "Top search is Done");

				} catch (Exception e) {
					reportStep("FAIL", "Top search is not Done");
					e.printStackTrace();
				}
			}

			// SMB Resource search
			else if (action != null && action.equalsIgnoreCase("SMBResourceSearch")) {
				try {
					String linkData = base.getMapData(LocatorName);
					String[] arr_locator = linkData.split("%");
					String[] arr_value = value.split("@");
					int loc = 0;
					int data = 0;
					System.out.println(arr_value[data]);
					base.wait(driver, arr_locator[loc]);
					// clicking on resource in header
					base.click_element(driver, arr_locator[loc]);
					loc++;
					// clicking on see all resources
					base.wait(driver, arr_locator[loc]);
					base.click_element(driver, arr_locator[loc]);
					loc++;
					base.wait(driver, arr_locator[loc]);
					base.SendKeys(driver, arr_locator[loc], arr_value[data]);
					loc++;
					base.click_element(driver, arr_locator[loc]);
					// verify page count
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					// verify search url
					data++;
					String currentUrl = driver.getCurrentUrl();
					System.out.println("URL from site " + currentUrl);

					boolean equals = currentUrl.equals(arr_value[data]);
					System.out.println("Given url matches " + equals);
					if (equals == true) {

						System.out.println("top search URL matched");
					} else {

						System.out.println("top search URL does not match");
					}
					// verify first link
					loc++;
					data++;
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify internet page text and back
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					// verify back to resource text
					loc++;
					data++;
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					driver.navigate().back();
					// navigation 2
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify next and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify last and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify Back and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify First and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					Thread.sleep(3000);
					reportStep("PASS", "Resouces search is Done");
				} catch (Exception e) {
					reportStep("FAIL", "Resouces search is Done");
					e.printStackTrace();
				}
			}

			// SMB Invalid Top search
			else if (action != null && action.equalsIgnoreCase("SMBInvalidTopSearch")) {
				try {
					String linkData = base.getMapData(LocatorName);
					String[] arr_locator = linkData.split("%");
					String[] arr_value = value.split("@");
					int loc = 0;
					int data = 0;
					System.out.println(arr_value[data]);
					base.wait(driver, arr_locator[loc]);
					// clicking on top search to provide search data
					base.click_element(driver, arr_locator[loc]);
					loc++;
					base.wait(driver, arr_locator[loc]);
					base.SendKeys(driver, arr_locator[loc], arr_value[data]);
					loc++;
					base.click_element(driver, arr_locator[loc]);
					// verify search result text
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);

					// verify search url
					data++;
					String currentUrl = driver.getCurrentUrl();
					System.out.println("URL from site " + currentUrl);

					boolean equals = currentUrl.equals(arr_value[data]);
					System.out.println("Given url matches " + equals);
					if (equals == true) {

						System.out.println("top search URL matched");

					} else {
						System.out.println("top search URL does not match");

					}
					Thread.sleep(3000);
					reportStep("PASS", "Invalid top search is Done");
				} catch (Exception e) {
					reportStep("FAIL", "Invalid top search is not Done");
					e.printStackTrace();
				}
			}

			// SMB Invalid Resource and Support Top search
			else if (action != null && action.equalsIgnoreCase("SMBInvalidResourceSearch")) {
				try {
					String linkData = base.getMapData(LocatorName);
					String[] arr_locator = linkData.split("%");
					String[] arr_value = value.split("@");
					int loc = 0;
					int data = 0;
					System.out.println(arr_value[data]);
					base.wait(driver, arr_locator[loc]);
					// clicking on resource in header
					base.click_element(driver, arr_locator[loc]);
					loc++;
					// clicking on see all resources
					base.wait(driver, arr_locator[loc]);
					base.click_element(driver, arr_locator[loc]);
					loc++;
					base.wait(driver, arr_locator[loc]);
					base.SendKeys(driver, arr_locator[loc], arr_value[data]);
					loc++;
					base.click_element(driver, arr_locator[loc]);
					// verify search result text
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					// verify search result text/Back
					loc++;
					data++;

					base.verifytext(driver, arr_locator[loc], arr_value[data]);

					// verify search url
					data++;
					String currentUrl = driver.getCurrentUrl();
					System.out.println("URL from site " + currentUrl);

					boolean equals = currentUrl.equals(arr_value[data]);
					System.out.println("Given url matches " + equals);
					if (equals == true) {

						System.out.println("top search URL matched");

					} else {
						System.out.println("top search URL does not match");

					}
					Thread.sleep(3000);
					reportStep("PASS", "Invalid Resource Search is Done");
				} catch (Exception e) {
					reportStep("FAIL", "Invalid Resource Search is not Done");
					e.printStackTrace();
				}
			}

			// Support and support article search
			else if (action != null && action.equalsIgnoreCase("SMBTopAndArticleSearch")) {
				try {
					String linkData = base.getMapData(LocatorName);
					String[] arr_locator = linkData.split("%");
					String[] arr_value = value.split("@");
					int loc = 0;
					int data = 0;
					System.out.println(arr_value[data]);
					base.wait(driver, arr_locator[loc]);
					// clicking on resource in header
					base.click_element(driver, arr_locator[loc]);
					loc++;
					// clicking on see all resources
					base.wait(driver, arr_locator[loc]);
					base.click_element(driver, arr_locator[loc]);
					loc++;
					base.wait(driver, arr_locator[loc]);
					base.SendKeys(driver, arr_locator[loc], arr_value[data]);
					loc++;
					base.click_element(driver, arr_locator[loc]);
					// verify search result text
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);

					// verify search url
					data++;
					String currentUrl = driver.getCurrentUrl();
					System.out.println("URL from site " + currentUrl);

					boolean equals = currentUrl.equals(arr_value[data]);
					System.out.println("Given url matches " + equals);
					if (equals == true) {

						System.out.println("top search URL matched");

					} else {
						System.out.println("top search URL does not match");

					}
					// verify first link and click
					loc++;
					// base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify back to support in breadcrump
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					// verify cloud apps in breadcrump
					loc++;
					data++;
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					// verify current page title in breadcrump
					loc++;
					data++;
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					// verify current page title
					loc++;
					data++;
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					// clicking back to support
					loc++;
					base.click_element(driver, arr_locator[loc]);
					// clicking on my account
					loc++;
					base.wait(driver, arr_locator[loc]);
					base.click_element(driver, arr_locator[loc]);
					// searching in article search
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					// base.scroll(driver, "//span[text()='Get Support. Find Answers. Gain
					// Insight.']");
					base.SendKeys(driver, arr_locator[loc], arr_value[data]);
					loc++;
					base.click_element(driver, arr_locator[loc]);
					// navigation 2
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify next and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify last and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify Back and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					// verify First and click
					loc++;
					data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver, arr_locator[loc], arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					Thread.sleep(3000);
					reportStep("PASS", "Top And Article Search is Done");
				} catch (Exception e) {
					reportStep("FAIL", "Top And Article Search is not Done");
					e.printStackTrace();
				}
			}
			// SMB FOOTER CODE
			else if (action != null && action.equalsIgnoreCase("SMBFooter")) {
				try {
					String linkdata = base.getMapData(LocatorName);
					WebElement link = driver.findElement(By.xpath(linkdata));
					List<WebElement> elements = link.findElements(By.tagName("a"));
					int size = elements.size();
					System.out.println(size);
					String ParentWindowHandle = driver.getWindowHandle();
					for (int b = 0; b < size; b++) {
						String keys = Keys.chord(Keys.CONTROL, Keys.ENTER);
						Thread.sleep(3000);
						elements.get(b).sendKeys(keys);

					}
					Thread.sleep(5000);
					System.out.println("Control Came out of the For Loop : ======>");
					Set<String> allWindowHandles = driver.getWindowHandles();
					int handleCount = 0;
					Thread.sleep(20000);
					System.out.println("My Parent Window handle - > " + ParentWindowHandle);
					driver.switchTo().window(ParentWindowHandle);

					List<String> list = new ArrayList<String>();
					for (String Invhandle : allWindowHandles) {
						handleCount++;
						System.out.println("Currently Window handle before IF matches is  - > " + Invhandle
								+ "Handler Count is: " + handleCount);
						if (!ParentWindowHandle.equals(Invhandle)) {
							list.add(Invhandle);
						}
					}

					int tempH, valueiter;
					String expectedURL = smblink.smbLinkData(value);
					System.out.println("values from excel" + value);
					String[] splittedvalues = expectedURL.split("@");
					for (tempH = list.size() - 1, valueiter = 0; tempH >= 0
							&& valueiter <= splittedvalues.length; tempH--, valueiter++) {
						driver.switchTo().window(list.get(tempH));

						System.out.println("Control Came inside IF Loop after when PH not-matches : ======>");
						System.out.println("splitted values" + splittedvalues[valueiter]);
						String childURl = driver.getCurrentUrl();
						if (splittedvalues[valueiter].equals(childURl)) {
							System.out.println("Expected URL " + splittedvalues[valueiter]);
							System.out.println("Actual URL from site" + childURl);
							System.out.println("URL status is PASS");

						} else {
							System.out.println("Expected URL " + splittedvalues[valueiter]);
							System.out.println("Actual URL from site" + childURl);
							System.out.println("URL status is FAIL");

						}

						URL u = new URL(splittedvalues[valueiter]);
						HttpURLConnection hc = (HttpURLConnection) u.openConnection();
						hc.setRequestMethod("HEAD");
						hc.connect();
						int rc = hc.getResponseCode();
						System.out.println(rc);
						String rm = hc.getResponseMessage();
						if (rc == 200) {
							System.out.println(childURl + "is valid");

						} else {
							System.out.println(childURl + "is a Broken link");

						}
						
						driver.close();
					}
					driver.switchTo().window(ParentWindowHandle);
					reportStep("PASS", "Footer link are matches with the excepted links");
				} catch (Exception e) {
					reportStep("FAIL", "Footer link are not matches with the excepted links");
					e.printStackTrace();
				}

			}
			
			//VOIP scenarios
			else if (action != null && action.equalsIgnoreCase("VOIP_POPUP")) {
				String val17 = base.getMapData(LocatorName);
				String[] arr_locator = val17.split("%");
				
				int loc = 0;
				
				do {

					if (loc == 0) {
						//Agree and continue in voip pop up
						base.click_element(driver, arr_locator[loc]);
						loc++;
						//footer continue
						base.wait(driver, arr_locator[loc]);
						base.click_element(driver, arr_locator[loc]);
						loc++;
						//summary back
						base.wait(driver, arr_locator[loc]);
						base.click_element(driver, arr_locator[loc]);
						loc++;
						//add on back
						base.wait(driver, arr_locator[loc]);
						base.click_element(driver, arr_locator[loc]);
						loc++;
						//learn more
						base.wait(driver, arr_locator[loc]);
						base.click_element(driver, arr_locator[loc]);
						loc++;
						//learn more continue
						base.wait(driver, arr_locator[loc]);
						base.click_element(driver, arr_locator[loc]);
						loc++;
						//agree and continue
						base.wait(driver, arr_locator[loc]);
						base.click_element(driver, arr_locator[loc]);
						
						Thread.sleep(5000);
						System.out.println("done");
						
					}
				
			}while(loc==7);
			}
			
			// VOIP content checking in checkout page
						else if (action != null && action.equalsIgnoreCase("VOIP_Checkout")) {
						         base.wait(driver, LocatorName);
						         base.verifytext(driver, LocatorName, value);
						}
			// default
		}

	}

}
