package com.res_keywordEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GreyBar {
	
	public static String greyBarOrNot() throws IOException {
 {
	 String greybarNB = null;
	 String path = System.getProperty("user.dir")+"\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Residential_Scenarios.xlsx";
     FileInputStream fis = new FileInputStream(path);
     Workbook workbook = new XSSFWorkbook(fis);
     Sheet sheet = workbook.getSheetAt(4);
//     int lastRow = sheet.getLastRowNum();
     int k=0;
     for (int i = 1; i <= sheet.getPhysicalNumberOfRows() - 1; i++) {

 		System.out.println("Value of the (Index) is- " + i);			
 		greybarNB = sheet.getRow(i).getCell(k + 2).toString().trim();
 		System.out.println("Value of the greybar in non bus is- " + greybarNB);
 	
 		String greybarBUS = sheet.getRow(i).getCell(k + 3).toString().trim();
 		System.out.println("Value of the greybar in bus hours is- " + greybarBUS);
 		
     }
     return greybarNB;
 }

	}
	
}

