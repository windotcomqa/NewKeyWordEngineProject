package com.res_keywordEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadLinks {
          
          public static  Map<String, Map<String, String>> setMapData() throws Throwable {
                   
                   File f = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Residential_Scenarios.xlsx");
                   FileInputStream fin = new FileInputStream(f);
                   Workbook wb = new XSSFWorkbook(fin);
                   Sheet sheet = wb.getSheetAt(3);
                   int lastRow = sheet.getLastRowNum();
                   System.out.println("The lastrow number is "+lastRow);
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
                   public static String getMapData(String key) throws Throwable {
                             Map<String, String> m = setMapData().get("DataSheet");
                             String value = m.get(key);
                             return value;
                   }
}

