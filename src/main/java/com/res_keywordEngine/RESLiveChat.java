package com.res_keywordEngine;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.dotcom.keyword.engine.LiveChat;

public class RESLiveChat extends RESLiveChatExcel {
	public static void main(String[] args) throws Throwable {

		System.setProperty("webdriver.chrome.driver",
				"./chromeDriver/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		
		
		driver.get("https://www.windstream.com/seo/products");
		driver.manage().window().maximize();
		
		RESLiveChatExcel res= new RESLiveChatExcel();
		res.chatLinkData("");
		
		/*** Getting EST time ***/
		TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));

		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");

		Date time = new Date();

		String time1 = formatter.format(time);

		System.out.println(time1);
		int hours = time.getHours();

		System.out.println(hours);
		// Check for Business hours three tile
		if ((hours >= 8) && (hours <= 18)) {
			
			WebElement BeginChatText = driver.findElement(By.xpath("(//a[text()='Chat Now'])[1]"));
			String bushr = BeginChatText.getText();
			System.out.println(bushr);
			String BeginChatActualText = "Chat now";
			System.out.println("the value of"+ BeginChatActualText);
			WebElement BusHoursText = driver.findElement(By.xpath("(//div[@class='card-body'])[6]/p[1]"));
			String bushrText = BeginChatText.getText();
			System.out.println(bushrText);
			String ChatActualText = "Get help fast and easy in real time from a knowledgeable representative.";
			System.out.println("the value of"+ ChatActualText);
			if (bushr.equalsIgnoreCase(BeginChatActualText) || bushrText.contains(ChatActualText)) {
				System.out.println("Live chat cta is visible");
				BeginChatText.click();
				
				
				
				String parent = driver.getWindowHandle();

						Set<String> wind = driver.getWindowHandles();

						for (String windowHandle : wind) {
							if (!(windowHandle.equals(parent))) {
								driver.switchTo().window(windowHandle);

								Thread.sleep(5000);

								 String cta = driver.getCurrentUrl();

								if (cta.contains("kineticcommunities")) {
									System.out.println("kineticcommunities is passed");
									//CustomKeywords.'chatkey.chat.title'(i, 13, 'passed')
									//live.title(i, 13, 'passed')
								} else {
									System.out.println("kineticcommunities is failed");

									//CustomKeywords.'chatkey.chat.title'(i, 13, 'Failed')
								//	live.title(i, 13, 'Failed')
								}

								driver.close();

								driver.switchTo().window(parent);
							}
//							else{
//System.out.println("windowhandles failed");
//							}
						}
				

			}else {
				System.out.println("Not valid");
			}
		} 
		
		
		//non business hours
		else {
			//grey bar
			WebElement BeginChatText = driver.findElement(By.xpath("//a[text()='Chat ']"));
			String bushr = BeginChatText.getText();
			System.out.println(bushr);
			String BeginChatActualText = "Chat";
			System.out.println("the value of"+ BeginChatActualText);
			WebElement BusHoursText = driver.findElement(By.xpath("//div[@class='title']"));
			String bushrText = BeginChatText.getText();
			System.out.println(bushrText);
			String ChatActualText = "Still need help ? We're here to assist you.";
			System.out.println("the value of"+ ChatActualText);
			if (bushr.equalsIgnoreCase(BeginChatActualText) || bushrText.contains(ChatActualText)) {
				System.out.println("Live chat cta is visible in grey bar");
				BeginChatText.click();
				
				String parent = driver.getWindowHandle();

						Set<String> wind = driver.getWindowHandles();

						for (String windowHandle : wind) {
							if (!(windowHandle.equals(parent))) {
								driver.switchTo().window(windowHandle);

								Thread.sleep(5000);

								 String cta = driver.getCurrentUrl();

								if (cta.contains("contact-us")) {
									System.out.println("contact-us Page is opened");
									
								} else {
									System.out.println("contact-us Page is not opened");

								
								}

								driver.close();

								driver.switchTo().window(parent);
							}

						}
				

			}else {
				System.out.println("Not valid");
			}

			
			
			
			//three tile
/*
			WebElement BeginChatText = driver.findElement(By.xpath("(//a[text()='Chat Now'])[1]"));
			String bushr = BeginChatText.getText();
			System.out.println(bushr);
			String BeginChatActualText = "Chat Now";
			System.out.println("the value of"+ BeginChatActualText);
			WebElement BusHoursText = driver.findElement(By.xpath("(//div[@class='card-body'])[6]/p[1]"));
			String bushrText = BeginChatText.getText();
			System.out.println(bushrText);
			String ChatActualText = "We're sorry, but Live Chat is only available during the following hours: Monday-Friday, 8:00 a.m. – 6:00 p.m. (ET) Saturday, 8:30 a.m. – 5 p.m. (ET)";
			System.out.println("the value of"+ ChatActualText);
			if (bushr.equalsIgnoreCase(BeginChatActualText) || bushrText.contains(ChatActualText)) {
				System.out.println("Live chat cta is visible");
				BeginChatText.click();
				
				
				
				String parent = driver.getWindowHandle();

						Set<String> wind = driver.getWindowHandles();

						for (String windowHandle : wind) {
							if (!(windowHandle.equals(parent))) {
								driver.switchTo().window(windowHandle);

								Thread.sleep(5000);

								 String cta = driver.getCurrentUrl();

								if (cta.contains("contact-us")) {
									System.out.println("contact-us Page is opened");
									
								} else {
									System.out.println("contact-us Page is not opened");

								
								}

								driver.close();

								driver.switchTo().window(parent);
							}

						}
				

			}else {
				System.out.println("Not valid");
			}

			
			
			*/
			
			}

	}

}
