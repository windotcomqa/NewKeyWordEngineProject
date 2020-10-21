package com.res_keywordEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

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
