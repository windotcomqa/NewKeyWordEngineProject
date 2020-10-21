package com.res_keywordEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import com.dotcom.keyword.base.Base;
import com.dotcom.keyword.engine.WriteExcel;
import com.dotcom.testcase.ResKickStart;

public class ResNewKeywordEngin extends ResKickStart {

	public WebDriver driver;
	public String TestCaseID;
	public Properties prop;
	public Base base;
	public WriteExcel wc;
	public WebElement element;
	public LinksObjectRepository links;
	public ReadLinks read;
	public WriteLinksStatus status;
	public BrokenLinks brok;
	public ResLinksRepository reslink;
	
	public static Workbook book;
	public static org.apache.poi.ss.usermodel.Sheet sheet;
	// reports
	public final String SCENARIO_SHEET_PATH = System.getProperty("user.dir")+"\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Residential_Scenarios.xlsx";

	// Master sheet test plan
	public void readExecution() throws Throwable {

		File f = new File(
				System.getProperty("user.dir")+"\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Residential_Scenarios.xlsx");
		FileInputStream fin = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fin);
		Sheet sheet = wb.getSheet("ResMasterSheet");
		int TotalRowCount = sheet.getPhysicalNumberOfRows();
		String TC = String.valueOf(TotalRowCount);
		System.out.println("Total Plan Count is:- " + TC);
		for (int i = 1; i <= sheet.getPhysicalNumberOfRows() - 1; i++) {
			System.out.println("ResMasterSheet Value of i is:- " + i);
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
			

			// Visit Residential
			if (action.equalsIgnoreCase("Visit Residential")) {
				try {
					String linkData = links.getLinkData(LocatorName);
					base.click_element(driver, linkData);
					Thread.sleep(15000);
					reportStep("PASS", "Navigated to the website");
				} catch (Exception e) {
					reportStep("FAIL", "Please ckeck your URL");
					e.printStackTrace();
				}
			}

			
			
			
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
					reportStep("FAIL", "Browser has not launched");

				}
			}

			else if (action.equalsIgnoreCase("navigateURL")) {
				try {
					base.navigateUrl(driver, value);
					System.out.println("URL launched");
					reportStep("PASS", "Browser has not launched");

				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", "Browser has not launched");
					
				}
			}

			// Click element

			else if (action != null && action.equalsIgnoreCase("click_element")) {

				try {
					String val = base.getMapData(LocatorName);

					base.click_element(driver, val);

					System.out.println(value);
					reportStep("PASS", "click element Done");

				} catch (Exception e) {

					System.out.println(e.getMessage());
					reportStep("FAIL", "click element NOTDone");
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
					reportStep("FAIL", "Address is not entered");
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

						}
					} while (loc == 10);

				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", "NOT YET Completed your Purchase");

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
					reportStep("FAIL", "Address is not entered");
				} catch (Exception e) {
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

						}
					} while (loc == 10);

				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", "Completed your Purchase");
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
						base.wait(driver, arr_locator[loc]);
						base.click_element(driver, arr_locator[loc]);
						// base.wait(driver, arr_locator[loc]);
						loc++;
						base.click_element(driver, arr_locator[loc]);
						reportStep("PASS", "CLICK HERE TO ENTER YOUR ADDRESS");
						break;
					} while (loc == 1);

				} catch (Exception e) {
					System.out.println(e.getMessage());
					reportStep("FAIL", "CLICK HERE TO ENTER YOUR ADDRESS");

				}
			}

									
			//MYwin login
			else if (action != null && action.equalsIgnoreCase("EnterCrendentials")) {
				try {
					System.out.println("MyWIN Menu Validation For Residential");
					String linkData = links.getLinkData(LocatorName);
					String[] arr_locator = linkData.split("%");
					String[] arr_value = value.split(",");
					int loc=0;
					int data=0;
					base.click_element(driver, arr_locator[loc]);
					if (loc == 0 && data==0)
					{
							System.out.println("USERNAME " + arr_value[data]);
							//base.wait(driver, (arr_locator[loc]));
							
							System.out.println("Enter your credentials");
							loc++;
							base.wait(driver, arr_locator[loc]);
							base.SendKeys(driver, arr_locator[loc], arr_value[data]);
							loc++;
							data++;
							System.out.println("PASSWORD "+arr_value[data]); 
							base.SendKeys(driver, arr_locator[loc], arr_value[data]);
							loc++;
							base.click_element(driver, arr_locator[loc]);
							System.out.println("Log in Successfully");
							//validation
							String actualUrl="https://www.windstream.com/#/";
					        String expectedUrl= driver.getCurrentUrl();
					        
					        if(actualUrl.equalsIgnoreCase(expectedUrl))
					        {
					            System.out.println("Test passed");
					            reportStep("PASS", "MyWin logged in");
					        }
					        else
					        {
					            System.out.println("Test failed");
					            System.out.println("please enter the valid credentials");
					            reportStep("FAIL", "please enter the valid credentials");
					        }
					}
						
					

					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					
					reportStep("FAIL", "please enter the valid credentials");
				}

			}
			
		//Res my win
			
			else if (action != null && action.equalsIgnoreCase("MyWinMenu")) {
				try {
					String linkData = links.getLinkData(LocatorName);
					String[] arr_locator = linkData.split("%");
					String[] arr_value = value.split(",");
					int loc=0;
					int data=0;						
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
					String expectedURL = reslink.resLinkData(value);
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
					reportStep("PASS", "MyWin URls Has been checked");
					
				} catch (Exception e) {
					reportStep("FAIL", "please enter the valid credentials");
				
					e.printStackTrace();
				}

			}
			//form validation
            else if (action != null && action.equalsIgnoreCase("form")) {
                     try {
                               String sryadd = links.getLinkData(LocatorName);
                               WebElement findElement = driver.findElement(By.xpath(sryadd));
                               String text = findElement.getText();
                               System.out.println("findelement gettext "+text);
                               if (text.equalsIgnoreCase(value)) {
                                         System.out.println("form is Valid");
                                         reportStep("PASS", "Form Validation Done");
                               }else {
                                         System.out.println("not valid address");
                                         reportStep("FAIL", "please enter the valid credentials");
                               }
                     } catch (Exception e) {
                    	 reportStep("FAIL", "please enter the valid credentials");
                              
                               e.printStackTrace();
                     }
                     
            }
			//Primary Links
            
            if (action.equalsIgnoreCase("PrimaryLinks")) {
                     try {
						String linkData = read.getMapData(value);
						 System.out.println("The value is"+value);
						 System.out.println("check the link"+ linkData);
						 driver.get(linkData);
						 int status=1;
						 brok.brokenlink(driver,value,i);
						 reportStep("PASS", "PrimaryLinks Validation Done");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						reportStep("FAIL", "please enter the valid credentials");
						e.printStackTrace();
					}
                     
            }
          //Search
            
            if (action.equalsIgnoreCase("Search")){
                 try {
					String linkData1 = links.getLinkData(LocatorName);
					String[] arr_locator = linkData1.split("%");
					String[] arr_value = value.split("@");
					int loc=0; int data = 0;
					System.out.println(arr_value[data]);
					base.wait(driver, arr_locator[loc]);
					//clicking on top search/support to provide search data
					base.click_element(driver, arr_locator[loc]);
					loc++;
					base.wait(driver,arr_locator[loc]);
					//providing search data
					base.SendKeys(driver, arr_locator[loc], arr_value[data]);
					loc++;
					//clicking on search icon
					base.click_element(driver, arr_locator[loc]);         
					loc++;data++;
					System.out.println(arr_value[data]);
					base.wait(driver, arr_locator[loc]);
					//verify search page count title
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					data++;
					
					//verifing the url match
					String currentUrl = driver.getCurrentUrl();
					System.out.println("URL from site "+currentUrl);
            
					boolean equals = currentUrl.equals(arr_value[data]);
					System.out.println("Given url matches "+equals);
					if (equals==true) {
					
					System.out.println("top search URL matched");
					}
					else {
					System.out.println("top search URL does not match");
					}
					
					//checking first URL
					loc++;data++;
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;
					Thread.sleep(5000);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					driver.navigate().back();
					                
					//checking first read more
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					driver.navigate().back();
					
					//checking second URL
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					driver.navigate().back();
					
					//checking second read more
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					driver.navigate().back(); 
					
					//footer2
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
            
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					
					//next
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					
					
					//last
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					
					//Back
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					
					//First
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					Thread.sleep(10000);
					reportStep("PASS", "Search Validation Done");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					reportStep("FAIL", "please enter the valid Input");
					e.printStackTrace();
				}
                     
            }
            // Verifying Support Back Functionality
            
            if (action.equalsIgnoreCase("SupportBack")) {
                     try {
						String linkData = links.getLinkData(LocatorName);
						 String[] arr_locator = linkData.split("%");
						 String[] arr_value = value.split("@");
						 int loc=0; int data = 0;
						 System.out.println(arr_value[data]);
						 base.wait(driver, arr_locator[loc]);
						 //clicking on support to provide search data
						 base.click_element(driver, arr_locator[loc]);
						 loc++;
						 base.wait(driver,arr_locator[loc]);
						 //providing search data
						 base.SendKeys(driver, arr_locator[loc], arr_value[data]);
						 loc++;
						 //clicking on search icon
						 base.click_element(driver, arr_locator[loc]); 
						 loc++;data++;
						 base.wait(driver, arr_locator[loc]);
						 base.verifytext(driver,arr_locator[loc],arr_value[data]);
						 base.click_element(driver, arr_locator[loc]);
						 loc++;data++;
						 base.wait(driver,arr_locator[loc]);
						 //providing search data
						 base.SendKeys(driver, arr_locator[loc], arr_value[data]);
						 loc++;
						 //clicking on search icon
						 base.click_element(driver, arr_locator[loc]);
						 loc++;
						 base.wait(driver, arr_locator[loc]);
						 base.click_element(driver, arr_locator[loc]);
						 //verifying back to support in breadcrumps
						 loc++;data++;
						 base.wait(driver, arr_locator[loc]);
						 base.verifytext(driver,arr_locator[loc],arr_value[data]);
						 //verifying Digital tv in breadcrumps
						 loc++;data++;
						 base.verifytext(driver,arr_locator[loc],arr_value[data]);
						 //verifying Kinetic tv in breadcrumps
						 loc++;data++;
						 base.verifytext(driver,arr_locator[loc],arr_value[data]);
						 //click on digital tv
						 loc++;
						 base.click_element(driver, arr_locator[loc]);
						 //verify digital tv
						 loc++;data++;
						 base.wait(driver, arr_locator[loc]);
						 base.verifytext(driver,arr_locator[loc],arr_value[data]);
						 //verify and click back to support
						 loc++;data++;
						 base.click_element(driver, arr_locator[loc]);
						 //verify support page title
						 loc++;data++;
						 base.wait(driver, arr_locator[loc]);
						 base.verifytext(driver,arr_locator[loc],arr_value[data]);
						 reportStep("PASS", "Support Search valdiation done");
					} catch (Exception e) {
						reportStep("FAIL", "please enter the valid Input");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }
            
            // Moving to support article search
            
            if (action.equalsIgnoreCase("SupportArticle")) {       
                 try {
					String linkData = links.getLinkData(LocatorName);
					String[] arr_locator = linkData.split("%");
					String[] arr_value = value.split("@");
					int loc=0; int data = 0;
					System.out.println(arr_value[data]);
					base.wait(driver, arr_locator[loc]);
					//clicking on support to provide search data
					base.click_element(driver, arr_locator[loc]);
					//moving to support page and clicking my account article
					loc++;
					base.wait(driver,arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					//provide search data and click on search
					loc++;data++;
					base.wait(driver,arr_locator[loc]);
					base.SendKeys(driver, arr_locator[loc], arr_value[data]);
					loc++;
					base.click_element(driver, arr_locator[loc]);
					//verify search page count title
					loc++;data++;
					base.wait(driver,arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					data++;
					//verify support article URL
					String currentUrl = driver.getCurrentUrl();
					System.out.println("URL from site "+currentUrl);
            
					boolean equals = currentUrl.equals(arr_value[data]);
					System.out.println("Given url matches "+equals);
					if (equals==true) {
					
					System.out.println("top search URL matched");
					}
					else {
					System.out.println("top search URL does not match");
					}                        
					//verify back to support present
					loc++;data++;
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					//verify and click on first link
					loc++;data++;
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;
					base.wait(driver,arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]); 
					//verify breadcrumps
					loc++;data++;
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					loc++;data++;
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					loc++;data++;
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					
					//providing search in support article result page
					loc++;data++;
					base.SendKeys(driver, arr_locator[loc], arr_value[data]);
					loc++;
					base.click_element(driver, arr_locator[loc]);
					
           //footer2
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
            
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					
					//next
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					
					
					//last
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					
					//Back
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					
					//First
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					base.click_element(driver, arr_locator[loc]);
					loc++;data++;
					base.wait(driver, arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					reportStep("PASS", "Support Artical search validation done");
					Thread.sleep(10000);
				} catch (Exception e) {
					reportStep("FAIL", "please enter the valid Input");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            
                
            }
            
            //invalid search
            if (action.equalsIgnoreCase("InvalidSearch")) {
                 try {
					String linkData = links.getLinkData(LocatorName);
					String[] arr_locator = linkData.split("%");
					String[] arr_value = value.split("@");
					int loc=0; int data = 0;
					System.out.println(arr_value[data]);
					base.wait(driver, arr_locator[loc]);
					//clicking on top search to provide search data
					base.click_element(driver, arr_locator[loc]);
					loc++;
					base.wait(driver,arr_locator[loc]);
					base.SendKeys(driver, arr_locator[loc], arr_value[data]);
					loc++;
					base.click_element(driver, arr_locator[loc]);
					//verify search page count title
					loc++;data++;
					base.wait(driver,arr_locator[loc]);
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					loc++;data++;
					base.verifytext(driver,arr_locator[loc],arr_value[data]);
					data++;
					//verify support article URL
					String currentUrl = driver.getCurrentUrl();
					System.out.println("URL from site "+currentUrl);
            
					boolean equals = currentUrl.equals(arr_value[data]);
					System.out.println("Given url matches "+equals);
					if (equals==true) {
					
					System.out.println("top search URL matched");
					}
					else {
					System.out.println("top search URL does not match");
					}
					reportStep("PASS", "Invalid search validation done");
				} catch (Exception e) {
					reportStep("FAIL", "please enter the valid Input");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}                        
                
            }
          //header products and support

            
            if (action.equalsIgnoreCase("HeaderProductsSupport")) {
                
            
                 
                try {
					String linkData = links.getLinkData(LocatorName);
					String[] arr_locator = linkData.split("%");
					int loc = 0;
					   WebElement account = driver.findElement(By.xpath(arr_locator[loc]));
					   loc++;
					   //Thread.sleep(5000);
					   Actions builder = new Actions(driver);
					   Thread.sleep(5000);
					   WebElement account1 = driver.findElement(By.xpath(arr_locator[loc]));
					   builder.moveToElement(account1).perform();
					   //Thread.sleep(5000);
					   List<WebElement> elements = account.findElements(By.tagName("a"));
         
           int size = elements.size();
           System.out.println(size);
           String ParentWindowHandle = driver.getWindowHandle();
          // Thread.sleep(5000);;
           JavascriptExecutor js = (JavascriptExecutor)driver;
           for (int j = 0; j < size; j++) {
					   System.out.println(".........Inside For loop..........");
					         System.out.println("List of sub-menus is: " + elements.get(j));
					         String hrefLink = elements.get(j).getAttribute("href");
					         System.out.println("Href Value is "+hrefLink);
					         Thread.sleep(5000);
					          js.executeScript("window.open('"+hrefLink+"','_blank');");
					    
           }
          // Thread.sleep(5000);
           System.out.println("Control Came out of the For Loop : ======>");
           Set<String> allWindowHandles = driver.getWindowHandles();
           int handleCount=0;
           Thread.sleep(5000);
           System.out.println("My Parent Window handle - > " + ParentWindowHandle);
          driver.switchTo().window(ParentWindowHandle);
           
           List<String> list=new ArrayList<String>();
           for(String Invhandle : allWindowHandles)
           {
					   handleCount++;
					   System.out.println("Currently Window handle before IF matches is  - > " + Invhandle + "Handler Count is: " + handleCount);
					   if (!ParentWindowHandle.equals(Invhandle)) {
					          list.add(Invhandle);
					   }
           }
         
          int tempH, valueiter;
          String expectedURL = reslink.resLinkData(value);
          System.out.println("values from excel" +value);
          String[] splittedvalues = expectedURL.split("@");
          for (tempH=list.size()-1,valueiter = 0;tempH>=0 && valueiter<=splittedvalues.length;tempH--, valueiter++) {
					   driver.switchTo().window(list.get(tempH));
					  
					   System.out.println("Control Came inside IF Loop after when PH not-matches : ======>");
					   String childURl = driver.getCurrentUrl();
					   if(splittedvalues[valueiter].equals(childURl)) {
					       System.out.println("Expected URL " +splittedvalues[valueiter]);
					       System.out.println("Actual URL from site" +childURl);
					       System.out.println("URL status is PASS" );
					   }
					   else
					   {
					       System.out.println("Expected URL " +splittedvalues[valueiter]);
					       System.out.println("Actual URL from site" +childURl);
					       System.out.println("URL status is FAIL" );
					   }
					 
					  driver.close();
          }
          driver.switchTo().window(ParentWindowHandle);
          reportStep("PASS", "Header Product Search validation done");
          
				} catch (Exception e) {
					reportStep("FAIL", "please enter the valid Input");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

          }

            //RES FOOTER CODE
            if (action.equalsIgnoreCase("RESFooter")) {
            	String linkData = links.getLinkData(LocatorName);
            	WebElement link = driver.findElement(By.xpath(linkData));
                List<WebElement> elements = link.findElements(By.tagName("a"));
                int size = elements.size();
                System.out.println(size);
                String ParentWindowHandle = driver.getWindowHandle();
                for (int z = 0; z < size; z++) {
                    String keys = Keys.chord(Keys.CONTROL, Keys.ENTER);
                    Thread.sleep(3000);
                    elements.get(z).sendKeys(keys);
             
                }
                Thread.sleep(5000);
                System.out.println("Control Came out of the For Loop : ======>");
                Set<String> allWindowHandles = driver.getWindowHandles();
                int handleCount=0;
                Thread.sleep(20000);
                System.out.println("My Parent Window handle - > " + ParentWindowHandle);
                driver.switchTo().window(ParentWindowHandle);
                
                List<String> list=new ArrayList<String>();
                for(String Invhandle : allWindowHandles)
                {
                     handleCount++;
                     System.out.println("Currently Window handle before IF matches is  - > " + Invhandle + "Handler Count is: " + handleCount);
                     if (!ParentWindowHandle.equals(Invhandle)) {
                            list.add(Invhandle);
                     }
                }
              
               int tempH, valueiter;
               String expectedURL = reslink.resLinkData(value);
               System.out.println("values from excel" +value);
               String[] splittedvalues = expectedURL.split("@");
               for (tempH=list.size()-1,valueiter = 0;tempH>=0 && valueiter<=splittedvalues.length;tempH--, valueiter++) {
                     driver.switchTo().window(list.get(tempH));
                    
                     System.out.println("Control Came inside IF Loop after when PH not-matches : ======>");
                     System.out.println("splitted values" +splittedvalues[valueiter]);
                     String childURl = driver.getCurrentUrl();
                     if(splittedvalues[valueiter].equals(childURl)) {
                            System.out.println("Expected URL " +splittedvalues[valueiter]);
                            System.out.println("Actual URL from site" +childURl);
                            System.out.println("URL status is PASS" );
                     }
                     else
                     {
                            System.out.println("Expected URL " +splittedvalues[valueiter]);
                            System.out.println("Actual URL from site" +childURl);
                            System.out.println("URL status is FAIL" );
                     }
                     
                     URL u=new URL(splittedvalues[valueiter]);
                    HttpURLConnection hc=(HttpURLConnection) u.openConnection();
                    hc.setRequestMethod("HEAD");
                    hc.connect();
                    int rc = hc.getResponseCode();
                    System.out.println(rc);
                    String rm = hc.getResponseMessage();
                    if (rc==200) {
                             System.out.println(childURl+ "is valid");
                      

                    }else {
                             System.out.println(childURl+ "is a Broken link");
                            

                    }
                    driver.close();
               }
               driver.switchTo().window(ParentWindowHandle);



            }
            
            else if (action != null && action.equalsIgnoreCase("microProvideAddress")) {

            	try {


            	 

            	String micro = links.getLinkData(LocatorName);

            	String[] arr_locator = micro.split("%");

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

            	 reportStep("FAIL", "Address is not entered");

            	}


            	 

            	}
			// default
		}
	}
}
