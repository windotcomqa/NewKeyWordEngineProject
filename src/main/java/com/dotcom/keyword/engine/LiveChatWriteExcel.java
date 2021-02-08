package com.dotcom.keyword.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LiveChatWriteExcel {
	public static void businessLiveChat(int rownum, int colnum, String value) throws IOException {

		File f = new File(
				System.getProperty("user.dir")+"\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Test_Scenarios.xlsx");
		FileInputStream fi = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fi);
		Sheet sheet = wb.getSheet("LiveChatSMB");
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
