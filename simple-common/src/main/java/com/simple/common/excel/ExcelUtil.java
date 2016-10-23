package com.simple.common.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	
	public static Workbook getWorkBook(InputStream is,String suffix) {
    	if (OFFICE_EXCEL_2003_POSTFIX.equals(suffix)) {
    		try {
    			if ( null != is) {
    				return new HSSFWorkbook(is);
    			}else {
    				return new HSSFWorkbook(); 
    			}
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return null;
        } else if (OFFICE_EXCEL_2010_POSTFIX.equals(suffix)) {
    		try {
    			if ( null != is ) {
    				return new XSSFWorkbook(is);
    			}else {
    				return new XSSFWorkbook();
    			}
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
    		return null;
        }
    	return null;
    }
	
	public static void createFile(Workbook workbook,String filefolder,String fileName) {
    	try {
    		File file = new File(filefolder);
    		if (!file.exists()) {
    			file.mkdirs();
    		}
    		
    		File destionFile = new File(filefolder+"/"+fileName);
    		if (!destionFile.exists()) {
    			destionFile.createNewFile();
    		}
			FileOutputStream outputStream = new FileOutputStream(destionFile);
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
