package com.dotcom.keyword.base;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dotcom.keyword.engine.SmbLinksRepository;
import com.res_keywordEngine.BrokenLinks;
import com.res_keywordEngine.LinksObjectRepository;
import com.res_keywordEngine.ResLinksRepository;
import com.res_keywordEngine.ResNewKeywordEngin;

public class Base extends Reports {

	public static WebDriver driver;
	public static Properties prop;
	public RemoteWebDriver driver1;
	public BrokenLinks brok;
	 public ResLinksRepository reslink;
	// public SmbLinksRepository smblink;
	 public Base base;
	 
	// public LinksObjectRepository linksobj;
	
	public WebDriver init_driver(String browserName) {
		if (browserName.equals("chrome")) {

			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\roobini.bu\\Desktop\\mydocs\\Automaiton updated smb and res\\NewKeyWordEngineProject-master\\chromeDriver\\chromedriver.exe");

			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		return driver;
	}

	public Properties init_properties() {
		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream(
					"C:\\Users\\roobini.bu\\Desktop\\mydocs\\Automaiton updated smb and res\\NewKeyWordEngineProject-master\\src\\main\\java\\com\\dotcom\\qa\\keyword\\config\\config.properties");
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return prop;
	}

	// Object Rep hashmap

	public static Map<String, Map<String, String>> setMapData() throws IOException {
		String path = "C:\\Users\\roobini.bu\\Desktop\\mydocs\\Automaiton updated smb and res\\NewKeyWordEngineProject-master\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Test_Scenarios.xlsx";
		FileInputStream fis = new FileInputStream(path);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(1);
		int lastRow = sheet.getLastRowNum();
		Map<String, Map<String, String>> excelFileMap = new HashMap<String, Map<String, String>>();
		Map<String, String> dataMap = new HashMap<String, String>();
		// Looping over entire row
		for (int i = 0; i <= lastRow; i++) {
			Row row = sheet.getRow(i);
			// 1st Cell as Value
			Cell valueCell = row.getCell(1);
			// 0th Cell as Key
			Cell keyCell = row.getCell(0);
			String value = valueCell.getStringCellValue().trim();
			String key = keyCell.getStringCellValue().trim();
			// Putting key & value in dataMap
			dataMap.put(key, value);
			// Putting dataMap to excelFileMap
			excelFileMap.put("DataSheet", dataMap);
		}
		// Returning excelFileMap
		return excelFileMap;
	}

	// Method to retrieve value
	public static String getMapData(String key) throws IOException {
		Map<String, String> m = setMapData().get("DataSheet");
		String value = m.get(key);
		return value;
	}
	//SMB link repository
	public static Map<String, Map<String, String>> setMapData2() throws IOException {
        String path = "C:\\Users\\roobini.bu\\Desktop\\mydocs\\Automaiton updated smb and res\\NewKeyWordEngineProject-master\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Test_Scenarios.xlsx";
        FileInputStream fis = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(2);
        int lastRow = sheet.getLastRowNum();
        Map<String, Map<String, String>> excelFileMap = new HashMap<String, Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        // Looping over entire row
        for (int i = 0; i <= lastRow; i++) {
                  Row row = sheet.getRow(i);
                  // 1st Cell as Value
                  Cell valueCell = row.getCell(2);
                  // 0th Cell as Key
                  Cell keyCell = row.getCell(0);
                  String value = valueCell.getStringCellValue().trim();
                  String key = keyCell.getStringCellValue().trim();
                  // Putting key & value in dataMap
                  dataMap.put(key, value);
                  // Putting dataMap to excelFileMap
                  excelFileMap.put("DataSheet", dataMap);
        }
        // Returning excelFileMap
        return excelFileMap;
}
	//SMBlink retrive data
	// Method to retrieve value
	public static String getLinkData(String key) throws IOException {
	        Map<String, String> m = setMapData().get("DataSheet");
	        String value = m.get(key);
	        return value;
	}
	//RES link obj repository
	 
    public static Map<String, Map<String, String>> setMapData3() throws IOException {
             String path = "C:\\Users\\roobini.bu\\Desktop\\mydocs\\Automaiton updated smb and res\\NewKeyWordEngineProject-master\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Residential_Scenarios.xlsx";
             FileInputStream fis = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(fis);
             Sheet sheet = workbook.getSheetAt(1);
             int lastRow = sheet.getLastRowNum();
             Map<String, Map<String, String>> excelFileMap = new HashMap<String, Map<String, String>>();
             Map<String, String> dataMap = new HashMap<String, String>();
             // Looping over entire row
             for (int i = 0; i <= lastRow; i++) {
                       Row row = sheet.getRow(i);
                       // 1st Cell as Value
                       Cell valueCell = row.getCell(1);
                       // 0th Cell as Key
                       Cell keyCell = row.getCell(0);
                       String value = valueCell.getStringCellValue().trim();
                       String key = keyCell.getStringCellValue().trim();
                       // Putting key & value in dataMap
                       dataMap.put(key, value);
                       // Putting dataMap to excelFileMap
                       excelFileMap.put("DataSheet", dataMap);
             }
             // Returning excelFileMap
             return excelFileMap;
    }

    // Method to retrieve value
    public static String getLinkData2(String key) throws IOException {
             Map<String, String> m = setMapData().get("DataSheet");
             String value = m.get(key);
             return value;
    }

	// Title repository HashMap

	public static Map<String, Map<String, String>> setMapData1() throws IOException {
		String path = "C:\\Users\\roobini.bu\\Desktop\\mydocs\\Automaiton updated smb and res\\NewKeyWordEngineProject-master\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Test_Scenarios.xlsx";
		FileInputStream fis = new FileInputStream(path);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(5);
		int lastRow = sheet.getLastRowNum();
		Map<String, Map<String, String>> excelFileMap = new HashMap<String, Map<String, String>>();
		Map<String, String> dataMap = new HashMap<String, String>();
		// Looping over entire row
		for (int i = 0; i <= lastRow; i++) {
			Row row = sheet.getRow(i);
			// 1st Cell as Value
			Cell valueCell = row.getCell(1);
			// 0th Cell as Key
			Cell keyCell = row.getCell(0);
			String value = valueCell.getStringCellValue().trim();
			String key = keyCell.getStringCellValue().trim();
			// Putting key & value in dataMap
			dataMap.put(key, value);
			// Putting dataMap to excelFileMap
			excelFileMap.put("DataSheet1", dataMap);
		}
		// Returning excelFileMap
		return excelFileMap;
	}

	// Method to retrieve value
	public static String getMapData1(String key) throws IOException {
		Map<String, String> m = setMapData().get("DataSheet1");
		String value = m.get(key);
		return value;
	}

	// Navigate URL
	public void navigateUrl(WebDriver driver, String value) throws IOException {
		driver.get(value);
		driver.manage().window().maximize();
	}

	// Click_Element
	public void click_element(WebDriver driver, String LocatorName)

	{
		WebElement click_element = driver.findElement(By.xpath(LocatorName));
		click_element.click();

	}
	

	// SendKeys
	public void SendKeys(WebDriver driver, String LocatorName, String value)

	{
		WebElement SendKeys = driver.findElement(By.xpath(LocatorName));
		SendKeys.sendKeys(value);

	}

	public void mousehover(WebDriver driver, String LocatorName)

	{
		WebElement mousehover = driver.findElement(By.xpath(LocatorName));
		Actions ac = new Actions(driver);
		ac.moveToElement(mousehover).click().build().perform();

	}

	public void drop_down(WebDriver driver, String LocatorName, String value)

	{
		WebElement dropDown = driver.findElement(By.xpath(LocatorName));

		Select sel1 = new Select(dropDown);

		sel1.selectByVisibleText(value);

	}

	// Scroldown
	public void scroll(WebDriver driver, String LocatorName)

	{
		WebElement obj = driver.findElement(By.xpath(LocatorName));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].scrollIntoView(true);", obj);

	}
	// Verify titles

	public void title(WebDriver driver, String LocatorName, String value) {

		try {
			String textVal = driver.findElement(By.xpath(LocatorName)).getText();
			if (textVal.equalsIgnoreCase(value)) {

				System.out.println(
						"The element xpath " + LocatorName + " with value " + value + " is verified successfully");
			} else {

				System.err.println(
						"The element xpath " + LocatorName + " with value " + value + " is not matched in application");
			}
		} catch (NoSuchElementException e) {

			System.err.println("The element xpath " + LocatorName + " is not available in DOM");
		}

	}

	public void wait(WebDriver driver, String LocatorName)

	{

		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorName)));

	}

    

	
	public void verifytext(WebDriver driver, String LocatorName, String actualtext) {
        WebElement expected = driver.findElement(By.xpath(LocatorName));
        String expectedtext = expected.getText();
        System.out.println("Search result got from site " + expectedtext);
        if (expectedtext.equals(actualtext)) {
                  System.out.println("verification text matched " +actualtext+ ","  +expectedtext);
                  System.out.println("---------------------------------------------------------------------");
                  //reportStep("PASS", "ENTER YOUR USE MODEM RENTAL");
        } else {
                  System.out.println("verification text not matched " +actualtext+ ","  +expectedtext);
                  System.out.println("---------------------------------------------------------------------");
        }
    }
	
	//default
	}


	