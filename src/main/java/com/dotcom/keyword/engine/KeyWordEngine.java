package com.dotcom.keyword.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.dotcom.keyword.base.Base;

public class KeyWordEngine {
	
	public WebDriver driver;
	public Properties prop;
	public Base base;
	public WebElement element;
	
	public static Workbook book;
	public static org.apache.poi.ss.usermodel.Sheet sheet;	
	
	public final String SCENARIO_SHEET_PATH="C:\\Users\\asmakhatoon.l\\eclipse-workspace\\DotComKeyword\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Test_Scenarios.xlsx";
	
	public void startExecution(String sheetName) throws Throwable 
	
	{
		//String locatorName="NA";
		//String locatorValue="NA";
		 
		FileInputStream file= null;
		try {
			file =new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		sheet = book.getSheet(sheetName);
		
		int k=0;
		for(int i=0; i<sheet.getLastRowNum(); i++) 
		{
			
			
				
				String locatorType=sheet.getRow(i+1).getCell(k+1).toString().trim();
				String locatorValue=sheet.getRow(i+1).getCell(k+2).toString().trim();
//				if(!locatorColValue.equals("NA"))
//				
//				{
//					locatorName=locatorColValue.split("%%")[0].trim();
//					locatorValue=locatorColValue.split("%%")[1].trim();
//				} 
				
				
				String action=sheet.getRow(i+1).getCell(k+3).toString().trim();	
				String value=sheet.getRow(i+1).getCell(k+4).toString().trim();	
				
				
				
				switch (action) {
				
				//open browser
				case "open browser":
					base = new Base();
					prop=base.init_properties();
					//base.init_driver(prop);
					if(value.isEmpty()||value.equals("NA"))
					{
						driver=base.init_driver(prop.getProperty("browser"));
						//Log.info("open browser");
					}
					else 
					{
					driver=base.init_driver(value);
					//Log.info("open browser");
					}
					break;
					
				//enter URL
				
				case "Navigate to site":
					if(value.isEmpty()||value.equals("NA"))
					{
						driver.get(prop.getProperty("url"));
						//Log.info("Nav to site");
					}
					else 
					{
						driver.get(value);
						//Log.info("Nav to site");
					}
					break;
					//Verify
				//case "Verify":
					
					
				//quit browser
				
				case "quit":
					driver.quit();
					break;
					

				default:
					break;
				}
				
				
				
				//key
				
				switch (locatorType) {
				
				case "id":
					
					element=driver.findElement(By.id(locatorValue));
					
					//sendkeys
					
					
					if(action.equalsIgnoreCase("sendKeys")) 
					try
					{  
						element.clear();
					element.sendKeys(value);
					}
					
					catch (NoSuchElementException e) 
					{
						System.out.println("Send Keys failed");
						System.out.println("Error is " + e);
						}
					//dropdown
					else if(action.equalsIgnoreCase("dropdown"))
					{
						try {
							Select s=new Select(element);
							s.selectByVisibleText("AR");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					//click
					else if(action.equalsIgnoreCase("click")) 
					{
						
						try {
							Thread.sleep(7000);
							element.click();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					locatorType=null;
					break;
					
				case "Xpath":
					
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					element=driver.findElement(By.xpath(locatorValue));
					
					//click
					
					if(action.equalsIgnoreCase("click"))
					{
					try {
						try {
							Thread.sleep(7000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						element.click();
						}
					catch (NoSuchElementException e) 
						{
							System.out.println("click action failed");
							System.out.println("Error is " + e);
							}
					}
					
					//Dropdown
					
					else if(action.equalsIgnoreCase("dropdown"))
					{
						try {
							Select s=new Select(element);
							s.selectByVisibleText("NE");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					
					//Dropdown
					
					else if(action.equalsIgnoreCase("dropdown"))
					{
					try {
						Select s=new Select(element);
						s.selectByVisibleText(value);
						} catch (Exception e) {
						e.printStackTrace();
						}
					}

					
             //alert
					
					else if(action.equalsIgnoreCase("alert"))
					{
						try {
							
							Alert alert = driver.switchTo().alert();
							alert.accept();
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					
					//send keys
					
					else if(action.equalsIgnoreCase("sendKeys"))
						try
					{  
						element.clear();
					element.sendKeys(value);
					}
					
					catch (NoSuchElementException e) 
					{
						System.out.println("Send Keys failed");
						System.out.println("Error is " + e);
						}
					//download
					
	
					// Verify link text
					
					else if(action.equalsIgnoreCase("verifyLinkText"))
						
					{
						element.getText().equals(value);
					}
					
					
				//Mousehoverclick

					
					else if(action.equalsIgnoreCase("Mousehoverclick"))
						
					{
						try {
							Actions ac=new Actions(driver);
							ac.moveToElement(element).click().build().perform();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				
					
                 // Mousehover
					
					else if(action.equalsIgnoreCase("Mousehover"))
						
					{
						try {
							Actions ac=new Actions(driver);
							ac.moveToElement(element).click().build().perform();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					
					// click button
					else if(action.equalsIgnoreCase("clickButton"))
					{
						element.click(); 
					}
					
					

					
					//verify button text
					
					else if(action.equalsIgnoreCase("verifyButtonText"))
					{

						try {
							boolean equals = element.getText().equals(value);
							System.out.println("Verifiying products:"+  equals);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//select list 
					//verify all list elements
					//verify all list selection
					//SelectRadio
					
					else if(action.equalsIgnoreCase("selectRadio"))
					{
						element.click();
					}
					
					//verifyRadioSelected
					
					else if(action.equalsIgnoreCase("verifyRadioSelected"))
					{
						element.getAttribute("checked");
					}
					
					//check check box
					
					else if(action.equalsIgnoreCase("checkCheckBox"))
					{
						element.getAttribute("checked");
						
					}
					
					//checking
					else if(action.equalsIgnoreCase("checking"))
					{
						try {
							String pageText = element.getText();
							CharSequence s = null;
							Assert.assertTrue(pageText.contains(s));
							System.out.println(pageText);
							 
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					//uncheck check box
					
					else if(action.equalsIgnoreCase("uncheckCheckBox")) 
					{
						element.getAttribute("checked");	
					}
					
					// verify checkbox selected
					
					else if(action.equalsIgnoreCase("verifyCheckBoxSelected"))
					{
						element.getAttribute("checked");	
					}
					
					//scroll
					else if(action.equalsIgnoreCase("scroll"))
					{
						try {
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("arguments[0].scrollIntoView()", element);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					//wait
					else if(action.equalsIgnoreCase("wait"))
					{
						try {
							WebDriverWait ws = new WebDriverWait(driver, 30);
							ws.until(ExpectedConditions.visibilityOf(element));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
					}

					//ssn click
					else if(action.equalsIgnoreCase("SsnClick"))
					{
						try {
							Thread.sleep(10000);
						element.click();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					
					locatorType=null;					
					break;
					
					
					
				case "PLinkText":
					
					element=driver.findElement(By.partialLinkText(locatorValue));
					
					if(action.equalsIgnoreCase("click"))
					{
						element.click();
					}
					locatorType=null;
					break;
					
					
					
				case "LinkText":
					
					element=driver.findElement(By.partialLinkText(locatorValue));
					
					if(action.equalsIgnoreCase("click"))
					{
						Thread.sleep(3000);
						element.click();
					}
					locatorType=null;
					break;
					

				default:
					break;
				}

				
				
			
			
		}
	}


}
