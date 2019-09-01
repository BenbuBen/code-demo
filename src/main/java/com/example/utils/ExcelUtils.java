package com.example.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel工具类
 * 代码实现vlookup功能
 * @author ben
 * 2019年8月9日
 */
public class ExcelUtils {

    public static Workbook getWorkbook(String excelPath){
	HSSFWorkbook wb = new HSSFWorkbook();
	return wb;
    }
    
    public static Sheet getSheet(Workbook wb,String sheetName){
	return wb.getSheet(sheetName);
    }
    
    public static void exportList(){
	
    }
}
