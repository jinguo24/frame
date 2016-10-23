package com.simple.common.excel;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelExcutor {

    public Workbook getWorkbook(InputStream is);
    
    public List<Row> getRows(Workbook wb);
}
