//package com.dotcom.keyword.engine;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.TimeZone;
//
//public class LiveChat {
//	
//	public static void main(String[] args) {
//		
//
//	
//	/***Getting EST time***/
//	TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
//
//	SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
//
//	Date time = new Date();
//
//	String time1 = formatter.format(time);
//
// System.out.println(time1);
//	int hours = time.getHours();
//
//	System.out.println(hours);
//	//Check for Business hours or Non-Business hours
//	if ((hours >= 7) && (hours <= 18)) {	
//	    CustomKeywords.'chatkey.chat.business'()
//	} 
//
//	/***Steps for Non-business hours starts ***/
//	else {
//	    for (int i = 1; i <= rowcount; i++) {
//	        try { 
//				
//				//Reading inputs from Excel file
//	            customertype = data.getValue('Customer Type', i)
//
//	            println(customertype)      //Printing the customer type
//
//	            Street = data.getValue('StreetName', i) //Storing  Streetname
//
//	            city = data.getValue('City', i)			//Storing  City details
//
//	            state = data.getValue('State', i)		//Storing  state details
//
//	            zipcode = data.getValue('Zipcode', i)	//Storing  zipcode details
//
//				
//				/*** Steps for customer type = unknown ***/
//	            if (customertype == 'Unknown') {
//	                chatlinks = data.getValue('URL', i)		//Storing URL Value
//
//	                greybar = data.getValue('GREYBAR-NB', i)//Storing Greybar value
//
//	                println(greybar)						//Prining the Greybar value
//
//	                WebUI.delay(3)
//
//	                println(chatlinks)
//
//	                driver.navigate().to(chatlinks)			//Navigating to the URL
//
//	                WebUI.delay(3)
//					
//					
//						/*** Steps FOR Unknown customer with Greybar=yes ***/
//	                	if (greybar == 'YES') {
//	                    Boolean chatCTA = driver.findElement(By.xpath('//a[text()=\'Chat \']')).isDisplayed()	//verifying Chatnow button is available or not
//
//	                    println(chatCTA)
//
//	                    WebUI.delay(3)
//
//	                    if (chatCTA == true) {
//	                        driver.findElement(By.xpath('//a[text()=\'Chat \']')).click()	//Clicking CHAT CTA button
//
//	                        WebUI.delay(3)
//
//	                        CustomKeywords.'chatkey.chat.title'(i, 9, 'passed')		//Writing status in Excel file
//							
//							WebUI.delay(3)
//							
//							cta = driver.getCurrentUrl()	//Reading the current URL
//							println(cta);
//							
//						   //Checking Page navigated to contact-us page
//						   if (cta.contains('contact-us')) {
//																CustomKeywords.'chatkey.chat.title'(i, 10, 'passed') //Writing status in Excel file
//															} else {
//																CustomKeywords.'chatkey.chat.title'(i, 10, 'Failed') //Writing status in Excel file
//															}
//							
//															WebUI.delay(3)
//															
//															//Checking Non-business hours text in Contact us page										
//															Boolean msg = driver.findElement(By.xpath("//h2[text()='Live Chat']/parent::span/parent::div/p[3]/strong")).isDisplayed();
//		
//															println(msg);
//
//															if (msg == true) {
//																CustomKeywords.'chatkey.chat.title'(i, 11, 'passed')	//Writing status in Excel file
//															} else {
//															CustomKeywords.'chatkey.chat.title'(i, 11, 'Failed')	//Writing status in Excel file
//															}
//							
//															} else {
//															CustomKeywords.'chatkey.chat.title'(i, 9, 'CTA not present')	//Writing status in Excel file
//	                    }
//	                }
//						 
//					//Steps for greybar = No for Unknown customer
//					else {
//	                    Boolean chatCTA = driver.findElement(By.xpath('(//a[text()=\'Chat Now\'])[1]')).isDisplayed()//verifying Chatnow button is available or not
//
//	                    println(chatCTA)
//
//	                    WebUI.delay(3)
//
//	                    if (chatCTA == true) {
//	                        driver.findElement(By.xpath('(//a[text()=\'Chat Now\'])[1]')).click()//Clicking CHAT CTA button
//
//	                        WebUI.delay(3)
//
//	                        CustomKeywords.'chatkey.chat.title'(i, 9, 'passed')//Writing status in Excel file
//							
//							//Window Handler
//							String parent = driver.getWindowHandle()
//							
//							Set<String> wind = driver.getWindowHandles()
//							
//							for (String windowHandle : wind) {
//								
//								if (!(windowHandle.equals(parent))) //comparing with Parent window
//											{
//												driver.switchTo().window(windowHandle)
//							
//												WebUI.delay(3)
//												
//												cta = driver.getCurrentUrl()//Reading the current URL
//												println(cta);
//												
//												//Checking Non-business hours text in Contact us page
//									           if (cta.contains('contact-us')) {
//																					CustomKeywords.'chatkey.chat.title'(i, 10, 'passed')//Writing status in Excel file
//																				} else {
//																					CustomKeywords.'chatkey.chat.title'(i, 10, 'Failed')//Writing status in Excel file
//																				}
//												
//																				WebUI.delay(3)
//						Boolean msg = driver.findElement(By.xpath("//h2[text()='Live Chat']/parent::span/parent::div/p[3]/strong")).isDisplayed();//Checking Non-business hours text in Contact us page
//							
//						println(msg);
//						
//												if (msg == true) {
//														CustomKeywords.'chatkey.chat.title'(i, 11, 'passed')//Writing status in Excel file
//													} else {
//														CustomKeywords.'chatkey.chat.title'(i, 11, 'Failed')//Writing status in Excel file
//													}
//															
//													driver.close()//Closing the current window
//							
//											driver.switchTo().window(parent)//Switch to Parent window
//							
//	                    } 
//						
//	                }
//	                    }
//						
//						
//						//If Chat CTA is not present
//						else{
//							CustomKeywords.'chatkey.chat.title'(i, 9, 'CTA not present')
//							}
//						
//						}
//					
//					 } 
//				
//				/***Steps for customertype=Prospect***/
//				else if (customertype == 'Prospect') {
//	                chatlinks = data.getValue('URL', i)	//Reading inputs from Excel file
//
//	                greybar = data.getValue('GREYBAR-NB', i) //Reading inputs from Excel file
//
//	                println(greybar)
//
//	                driver.navigate().to('https://www.windstream.com/')	//Navigating to HomePage
//
//	                WebUI.delay(3)
//
//	                CustomKeywords.'chatkey.LiveChat.demokey'(Street, city, state, zipcode)	//sending inputs to provide address
//
//	                WebUI.delay(10)
//					
//					
//					/***Steps when Greybar=yes for Prospect Customer***/
//	                if (greybar == 'YES') {
//	                    Boolean chatCTA = driver.findElement(By.xpath('//a[text()=\'Chat \']')).isDisplayed()	//verifying Chatnow button is available or not
//
//	                    println(chatCTA)
//
//	                    WebUI.delay(3)
//
//	                    if (chatCTA == true) {
//	                        driver.findElement(By.xpath('//a[text()=\'Chat \']')).click()	//Clicking CHAT CTA button
//
//	                        WebUI.delay(3)
//
//	                        CustomKeywords.'chatkey.chat.title'(i, 9, 'passed')	//Writing status in Excel file
//							
//							WebUI.delay(3)
//							
//							cta = driver.getCurrentUrl()	//Reading the current URL
//							println(cta);
//							
//							//Checking Non-business hours text in Contact us page
//							if (cta.contains('contact-us')) {
//																CustomKeywords.'chatkey.chat.title'(i, 10, 'passed')//Writing status in Excel file
//															} else {
//																CustomKeywords.'chatkey.chat.title'(i, 10, 'Failed')//Writing status in Excel file
//															}
//							
//															WebUI.delay(3)
//															
//					//Checking Non-business hours text in Contact us page
//					Boolean msg = driver.findElement(By.xpath("//h2[text()='Live Chat']/parent::span/parent::div/p[3]/strong")).isDisplayed();
//		
//							   println(msg);
//
//							if (msg == true) {
//									CustomKeywords.'chatkey.chat.title'(i, 11, 'passed')	//Writing status in Excel file
//								} else {
//									CustomKeywords.'chatkey.chat.title'(i, 11, 'Failed')	//Writing status in Excel file
//								}
//	                    } 
//						
//						//If Chat CTA is not present
//						else {
//	                        CustomKeywords.'chatkey.chat.title'(i, 9, 'Chat CTA not present')
//	                    }
//	                } 
//					
//					//Steps when Greybar = no for Prospect customer
//					else {
//	                    Boolean chatCTA = driver.findElement(By.xpath('(//a[text()=\'Chat Now\'])[1]')).isDisplayed() //verifying Chatnow button is available or not
//
//	                    println(chatCTA)
//
//	                    WebUI.delay(3)
//
//	                    if (chatCTA == true) {
//	                        driver.findElement(By.xpath('(//a[text()=\'Chat Now\'])[1]')).click()	//Clicking CHAT CTA button
//
//	                        WebUI.delay(3)
//
//	                        CustomKeywords.'chatkey.chat.title'(i, 9, 'passed')	//Writing status in Excel file
//							
//							//Window Handler
//							String parent = driver.getWindowHandle()
//							
//							Set<String> wind = driver.getWindowHandles()
//							
//							for (String windowHandle : wind) {
//								
//								if (!(windowHandle.equals(parent))) //comparing with Parent window
//											{
//												driver.switchTo().window(windowHandle)
//							
//												WebUI.delay(3)
//												
//												cta = driver.getCurrentUrl()	//Reading the current URL
//												
//											   if (cta.contains('contact-us')) {
//																					CustomKeywords.'chatkey.chat.title'(i, 10, 'passed')//Writing status in Excel file
//																				} else {
//																					CustomKeywords.'chatkey.chat.title'(i, 10, 'Failed')//Writing status in Excel file
//																				}
//												
//											   WebUI.delay(3)
//											   
//						//Checking Non-business hours text in Contact us page
//						Boolean msg = driver.findElement(By.xpath("//h2[text()='Live Chat']/parent::span/parent::div/p[3]/strong")).isDisplayed();
//							
//						println(msg);
//						
//												if (msg == true) {
//														CustomKeywords.'chatkey.chat.title'(i, 11, 'passed')//Writing status in Excel file
//													} else {
//														CustomKeywords.'chatkey.chat.title'(i, 11, 'Failed')//Writing status in Excel file
//													}
//															
//													driver.close()	//Closing the current window
//							
//											driver.switchTo().window(parent) //Switch to Parent window
//							
//						}
//						
//					}
//
//	                    } 
//						//If Chat CTA is not present
//						else {
//	                        CustomKeywords.'chatkey.chat.title'(i, 9, 'Chat Cta not present')
//	                    }
//	                }
//	            } 
//				
//				/***Steps for customertype = Prospect without prviding address***/
//				else if (customertype == 'Prospect1') {
//	                chatlinks = data.getValue('URL', i)	//Reading inputs from Excel file
//
//	                greybar = data.getValue('GREYBAR-NB', i)	//Reading inputs from Excel file
//
//	                println(greybar)
//
//	                WebUI.delay(3)
//
//	                println(chatlinks)
//
//	                driver.navigate().to(chatlinks)	//Navigating to the Current URL
//
//	                WebUI.delay(5)
//
//	                if (greybar == 'YES') {
//	                    Boolean chatCTA = driver.findElement(By.xpath('//a[text()=\'Chat \']')).isDisplayed()
//
//	                    println(chatCTA)
//
//	                    WebUI.delay(3)
//
//	                    if (chatCTA == true) {
//	                        driver.findElement(By.xpath('//a[text()=\'Chat \']')).click()
//
//	                        WebUI.delay(3)
//
//	                        CustomKeywords.'chatkey.chat.title'(i, 9, 'passed')
//							
//							WebUI.delay(3)
//							
//							cta = driver.getCurrentUrl()
//							println(cta);
//							
//						   if (cta.contains('contact-us')) {
//																CustomKeywords.'chatkey.chat.title'(i, 10, 'passed')
//															} else {
//																CustomKeywords.'chatkey.chat.title'(i, 10, 'Failed')
//															}
//							
//															WebUI.delay(3)
//															
//					Boolean msg = driver.findElement(By.xpath("//h2[text()='Live Chat']/parent::span/parent::div/p[3]/strong")).isDisplayed();
//		
//							   println(msg);
//
//							if (msg == true) {
//									CustomKeywords.'chatkey.chat.title'(i, 11, 'passed')
//								} else {
//									CustomKeywords.'chatkey.chat.title'(i, 11, 'Failed')
//								}
//	                    } else {
//	                        CustomKeywords.'chatkey.chat.title'(i, 9, 'Failed')
//	                    }
//	                } 
//					/***Steps when grey bar = No***/
//					else {
//	                    Boolean chatCTA = driver.findElement(By.xpath('(//a[text()=\'Chat Now\'])[1]')).isDisplayed()
//
//	                    println(chatCTA)
//
//	                    WebUI.delay(3)
//
//	                    if (chatCTA == true) {
//	                        driver.findElement(By.xpath('(//a[text()=\'Chat Now\'])[1]')).click()//Clicking CHAT CTA button
//
//	                        WebUI.delay(3)
//
//	                        CustomKeywords.'chatkey.chat.title'(i, 9, 'passed')
//							
//							String parent = driver.getWindowHandle()
//							
//							Set<String> wind = driver.getWindowHandles()
//							
//							for (String windowHandle : wind) {
//								
//								if (!(windowHandle.equals(parent))) //comparing with Parent window
//											{
//												driver.switchTo().window(windowHandle)
//							
//												WebUI.delay(3)
//												
//												cta = driver.getCurrentUrl()
//												
//											   if (cta.contains('contact-us')) {
//																					CustomKeywords.'chatkey.chat.title'(i, 10, 'passed')
//																				} else {
//																					CustomKeywords.'chatkey.chat.title'(i, 10, 'Failed')
//																				}
//												
//																				WebUI.delay(3)
//						Boolean msg = driver.findElement(By.xpath("//h2[text()='Live Chat']/parent::span/parent::div/p[3]/strong")).isDisplayed();
//							
//						println(msg);
//						
//												if (msg == true) {
//														CustomKeywords.'chatkey.chat.title'(i, 11, 'passed')
//													} else {
//														CustomKeywords.'chatkey.chat.title'(i, 11, 'Failed')
//													}
//															
//													driver.close()
//							
//											driver.switchTo().window(parent)
//							
//						}
//						
//					}
//
//							
//	                    } 
//						//If Chat CTA is not present
//						else {
//	                        CustomKeywords.'chatkey.chat.title'(i, 9, 'Chat Cta not present')
//	                    }
//	                }
//	            }
//				
//	            
//	        }
//	        catch (Exception E) {
//				
//				println("Non-business hours Exception thrown")
//	        } 
//	    }
//	}
//	}
//	
//}
