package com.res_keywordEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteLinksStatus {
          
          public static void updateExcel(int rownum, int colnum, String value) throws IOException {

                   File f = new File("C:\\Users\\roobini.bu\\Desktop\\mydocs\\Automaiton updated smb and res\\NewKeyWordEngineProject-master\\src\\main\\java\\com\\dotcom\\keyword\\scenarios\\Residential_Scenarios.xlsx");
                   FileInputStream fi = new FileInputStream(f);
                   Workbook wb = new XSSFWorkbook(fi);
                   Sheet sheet = wb.getSheet("Primary Links");
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

