package com.res_keywordEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RESLiveChatExcel {

	
	
	public void readChatLink() throws Throwable {


		File f = new File(
				System.getProperty("user.dir")+"\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Residential_Scenarios.xlsx");
		FileInputStream fin = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fin);
		Sheet sheet = wb.getSheet("LiveChatRES");
		int TotalRowCount = sheet.getPhysicalNumberOfRows();
		String TC = String.valueOf(TotalRowCount);
		System.out.println("Total Plan Count is:- " + TC);
		for (int i = 1; i <= sheet.getPhysicalNumberOfRows() - 1; i++) {
			System.out.println("MasterSheet Value of i is:- " + i);
			Row headrow = sheet.getRow(i);
			String headname = headrow.getCell(0).getStringCellValue();
			System.out.println(headname);
			System.out.println("LiveChatSMB Value of Flag is:- " + headname);
			
			}
		}
	
		public static Map<String, Map<String, String>> setMapData() throws IOException {
            String path = System.getProperty("user.dir")+"\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Residential_Scenarios.xlsx";
            FileInputStream fis = new FileInputStream(path);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(4);
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
   public static String chatLinkData(String key) throws IOException {
            Map<String, String> m = setMapData().get("DataSheet");
            String value = m.get(key);
            return value;
   }


	
	public void writeChatLink(int rownum, int colnum, Date value) throws Throwable {
		
        File f = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Residential_Scenarios.xlsx");
        FileInputStream fi = new FileInputStream(f);
        Workbook wb = new XSSFWorkbook(fi);
        Sheet sheet = wb.getSheet("LiveChatRES");
        Row row = sheet.getRow(rownum);
        if (row == null) {
                  row = sheet.createRow(rownum);
        }

        Cell cell = row.getCell(colnum);

        if (cell == null) {
                  cell = row.createCell(colnum);
        }

        cell.setCellValue(value);

        FileOutputStream fo = new FileOutputStream(f);
        wb.write(fo);
        fo.close();



	}
}
