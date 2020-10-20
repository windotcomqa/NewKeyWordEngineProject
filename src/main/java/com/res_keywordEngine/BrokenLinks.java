package com.res_keywordEngine;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import freemarker.core.ReturnInstruction.Return;

public class BrokenLinks {
          
          public static WebDriver driver;
          public static ReadLinks read;
          public static WriteLinksStatus status;

          public static String brokenlink(WebDriver driver,String linkname, int val) throws Throwable {
                   
//                 System.setProperty("webdriver.chrome.driver", "/Users/apple/Desktop/NewKeyWordEngineProject-master/Driver/chromedriver 2");
//                 driver = new ChromeDriver();
//                 driver.get("https://www.windstream.com");
//                 driver.manage().window().maximize();
//                 
//                 WebElement element = driver.findElement(By.xpath("//a[text()='Visit Residential']"));
//                 element.click();
//                 //Internet
//                 Thread.sleep(10000);
//                 WebElement element2 = driver.findElement(By.xpath("(//a[text()='Internet'])[2]"));
//                 element2.click();
//                 Thread.sleep(10000);
                   String currentUrl = driver.getCurrentUrl();
                   System.out.println(currentUrl);
                   String data = read.getMapData(linkname);
                   System.out.println("the data value is "+data);
                   boolean equals = currentUrl.equals(data);
                   System.out.println("Given url matches "+equals);
                   if (equals==true) {
                                      if (data==null || data.isEmpty()) {
                                                System.out.println("HREF NOT AVAILABLE");
                                      }

                   try {
                             URL u=new URL(data);
                             HttpURLConnection hc=(HttpURLConnection) u.openConnection();
                             hc.setRequestMethod("HEAD");
                             hc.connect();
                             int rc = hc.getResponseCode();
                             System.out.println(rc);
                             String rm = hc.getResponseMessage();
                             if (rc==200) {
                                      System.out.println("valid");
                                      status.updateExcel(val, 3, "valid");

                             }else {
                                      System.out.println("Broken link");
                                      status.updateExcel(val, 3, "Broken link");

                             }
                   } catch (MalformedURLException e) {
                             e.printStackTrace();
                   } catch (ClassCastException e) {
                             e.printStackTrace();
                   }
                             status.updateExcel(val, 2, "Pass");
                             
                   }else {
                             status.updateExcel(val, 2, "Fail");
                   }
                   return currentUrl;

          }

          
}

