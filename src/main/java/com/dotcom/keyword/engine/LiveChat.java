package com.dotcom.keyword.engine;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



public class LiveChat   {
	

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\roobini.bu\\Desktop\\Automaiton updated smb and res backup\\NewKeyWordEngineProject-master\\chromeDriver\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://business.windstream.com/fiber-internet");;
		driver.manage().window().maximize();

		/*** Getting EST time ***/
		TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));

		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");

		Date time = new Date();

		String time1 = formatter.format(time);

		System.out.println(time1);
		int hours = time.getHours();

		System.out.println(hours);
		// Check for Business hours or Non-Business hours
		if ((hours >= 8) && (hours <= 18)) {
			WebElement BeginChatText = driver.findElement(By.xpath("//a[text()='Begin Chat ']"));
			String bushr = BeginChatText.getText();
			System.out.println(bushr);
			String BeginChatActualText = "Begin Chat";
			System.out.println("the value of"+ BeginChatActualText);
			WebElement BusHoursText = driver.findElement(By.xpath("( //p[@class=' para_desc vertical'])[1]"));
			String bushrText = BeginChatText.getText();
			System.out.println(bushrText);
			String ChatActualText = "Have a quick question?";
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
									LiveChatWriteExcel liv= new LiveChatWriteExcel();
									try {
										liv.businessLiveChat(1, 2, "BusinesshourchatPass");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								} else {
									System.out.println("kineticcommunities is failed");
									LiveChatWriteExcel liv= new LiveChatWriteExcel();
									try {
										liv.businessLiveChat(1, 2, "BusinesshourchatFail");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

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
		} else {
			WebElement nonbushrs = driver.findElement(By.xpath("( //p[@class=' para_desc vertical'])[1]"));
			String nonbushr = nonbushrs.getText();
			System.out.println(nonbushr);
			String s1 = "We're sorry, but Live Chat is only available during the following hours: Monday-Friday, 8:00 a.m. – 6:00 p.m. (ET) Saturday, 8:30 a.m. – 5 p.m. (ET)";
			if (nonbushr.equalsIgnoreCase(s1)) {
				System.out.println("Live chat cta is visible");
				LiveChatWriteExcel liv= new LiveChatWriteExcel();
				try {
					liv.businessLiveChat(1, 1, "Non-BusinesshourPass");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else {
				LiveChatWriteExcel liv= new LiveChatWriteExcel();
				try {
					liv.businessLiveChat(1, 1, "Non-BusinesshourFail");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		}

		
		
	}
}
