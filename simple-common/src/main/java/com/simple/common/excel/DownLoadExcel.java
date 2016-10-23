package com.simple.common.excel;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.StringUtils;

import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.util.PrimaryKeyUtil;
import com.simple.common.util.ResponseInfo;
import com.simple.common.util.ResponseStatus;

/**
 * 下载2010
 * @author zhengfy1
 *
 */
public class DownLoadExcel {

	private static final String ROOT  = "/temp/download/";
	
	public static ResponseInfo download(List datas,List<String> titles,DownLoadExcutor downloadExcutor) {
		try {
			Workbook workbook = ExcelUtil.getWorkBook(null, ExcelUtil.OFFICE_EXCEL_2010_POSTFIX);
			Sheet sheet = workbook.createSheet();
			if ( null != titles) {
				Row titleRow = sheet.createRow(0);
				for (int i = 0 ; i < titles.size() ; i ++) {
					Cell cell = titleRow.createCell(i);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(titles.get(i));
				}
			}
			if ( null != datas && datas.size() > 0 ) {
				for ( int i = 0 ; i< datas.size(); i++) {
					Row row = sheet.createRow(i+1);
					List<String> cells =  downloadExcutor.getCellValues(datas.get(i));
					if ( null != cells ) {
						for (int j = 0 ; j< cells.size(); j++) {
							Cell cell = row.createCell(j);
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(cells.get(j));
						}
					}
				}
			}
			String filefolder = ROOT+System.currentTimeMillis();
			String filename = PrimaryKeyUtil.getUUID()+"."+ExcelUtil.OFFICE_EXCEL_2010_POSTFIX;
			ExcelUtil.createFile(workbook, EnvPropertiesConfiger.getValue("uploadDir")+filefolder, filename);
			return new ResponseInfo(new ResponseStatus(true,"生成文件成功"), filefolder+"/"+filename);
		}catch(Exception e) {
			return new ResponseInfo(new ResponseStatus(false,"生成文件失败："+e.getLocalizedMessage()), null);
		}
	}
	
	public static String splitAndFilterString(String input) {    
        if (input == null || input.trim().equals("")) {    
            return "";    
        }    
        // 去掉所有html元素, 
        // 去掉所有html元素,    
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(    
                "<[^>]*>", "");    
        str = str.replaceAll("[(/>)<]", ""); 
        //String str = input.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "");  
        return str;    
	}  
	
	public static void downExcel(HttpServletResponse response,String fileName,List<SheetRow> rows,int cellCount) {
		try {      
			   response.reset();
			   response.setContentType("application/vnd.ms-excel");
			   response.addHeader("Content-Disposition","attachment;filename=user.xls");
			   response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1")
			   +"."+ExcelUtil.OFFICE_EXCEL_2010_POSTFIX);
			   OutputStream out=response.getOutputStream();
			   Workbook workbook = ExcelUtil.getWorkBook(null, ExcelUtil.OFFICE_EXCEL_2010_POSTFIX);
			   generateExcel(workbook,rows,cellCount);
			   workbook.write(out);
		       out.close();
			
//			   response.reset();// 清空输出流 
//			   //定义输出流，以便打开保存对话框______________________begin  
//			   //OutputStream os = response.getOutputStream();// 取得输出流  
//			   OutputStream os = new BufferedOutputStream(response.getOutputStream());  
//			   response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1")+"."+ExcelUtil.OFFICE_EXCEL_2010_POSTFIX);  
//			// 设定输出文件头        
//			   response.setContentType("application/vnd.ms-excel");// 定义输出类型      
//			   //定义输出流，以便打开保存对话框_______________________end  
//			  
//			   /** **********创建工作簿************ */  
//			   
////			   response.setContentType("application/vnd.ms-excel");    
////		        response.setHeader("Content-disposition", "attachment;filename=student.xls");    
////		        OutputStream ouputStream = response.getOutputStream();    
//			   workbook.write(os); 
//			   os.flush();    
//			   os.close(); 
		}catch(Exception e) {
			e.printStackTrace();
		}
			   
	}
	
	private static void generateExcel(Workbook workbook,List<SheetRow> rows,int cellCount) {
		Sheet sheet = workbook.createSheet();
		if ( null != rows ) {
			for ( int i = 0 ; i < rows.size(); i++ ) {
				SheetRow sheetRow = rows.get(i);
				Row row = sheet.createRow(i);
				List<SheetCell> cells = sheetRow.getCells();
				if ( null != cells ) {
					for (int j = 0 ; j < cells.size(); j++) {
						SheetCell sheetCell = cells.get(j);
						short megerBegin = (short) (sheetCell.getMergeCellIndexStart()>0?sheetCell.getMergeCellIndexStart()-1:0);
						short megerEnd = (short) (sheetCell.getMergeCellIndexEnd()>0?sheetCell.getMergeCellIndexEnd()-1:0);
						//从初始到结束，设置同样的样式和同样的值，然后合并
						for (int k = megerBegin ; k <= megerEnd ; k ++ ) {
							Cell cell = row.createCell(k);
							cell.setCellValue(sheetCell.getContent());
							
							CellStyle style = workbook.createCellStyle();  
							if ( sheetCell.getAlignment() > 0 ) {
								style.setAlignment(sheetCell.getAlignment());  
							}
							if ( sheetCell.getVerticalAlignment() > 0 ) {
								style.setVerticalAlignment(sheetCell.getVerticalAlignment());  
							}
							Font font = workbook.createFont(); 
							if (!StringUtils.isEmpty(sheetCell.getFontName())) {
								font.setFontName(sheetCell.getFontName()); 
							}
							if (sheetCell.getFontSize() > 0 ) {
								font.setFontHeightInPoints(sheetCell.getFontSize());  
							}
							if (sheetCell.getBoldWeight() > 0 ) {
								font.setBoldweight(sheetCell.getBoldWeight());  
							}
							if ( null != sheetCell.getFontColor()) {
								font.setColor(sheetCell.getFontColor().getIndex());  
							}
							style.setFont(font); //调用字体样式对象  
	//			            
	//			            style.setWrapText(true);  
	//			                           //增加表格边框的样式 例子  
							if (sheetCell.getBorder() > 0 ) {
		                        style.setBorderTop(sheetCell.getBorder());  
		                        style.setBorderLeft(sheetCell.getBorder());  
		                        style.setBorderBottom(sheetCell.getBorder());
		                        style.setBorderRight(sheetCell.getBorder());
							}
							
							if ( null != sheetCell.getBorderColor()) {
								style.setTopBorderColor(sheetCell.getBorderColor().getIndex());  
		                        style.setLeftBorderColor(sheetCell.getBorderColor().getIndex()); 
		                        style.setBottomBorderColor(sheetCell.getBorderColor().getIndex());
		                        style.setRightBorderColor(sheetCell.getBorderColor().getIndex());
							}
							if ( null != sheetCell.getBgColor()) {
								style.setFillBackgroundColor(sheetCell.getBgColor().getIndex());
								style.setFillPattern(CellStyle.SOLID_FOREGROUND);
							}
				            //3.单元格应用样式  
				            cell.setCellStyle(style);  
						}
						if (megerBegin != megerEnd) {
							CellRangeAddress cra=new CellRangeAddress(i, i,megerBegin, megerEnd);  
							sheet.addMergedRegion(cra); 
						}
					}
				}
			}
		}
		//ExcelUtil.createFile(workbook, "D:/", "1.xlsx");
	}
	
	public static void main(String[] args) {
		Workbook workbook = ExcelUtil.getWorkBook(null, ExcelUtil.OFFICE_EXCEL_2010_POSTFIX);
		List<SheetRow> rows = new ArrayList<SheetRow>();
		SheetRow titleRow = new SheetRow();
		SheetCell titleCell = new SheetCell("军训安全教育调查问卷",1,3);
		titleCell.setAlignment(SheetCell.ALIGNMENT_CENTER);
		titleCell.setFont("宋体", (short)16, (short)1.0, new HSSFColor.AQUA());
		titleCell.setBorder(SheetCell.BORDER_DASHED, new HSSFColor.BLUE());
		titleCell.setBgColor(new HSSFColor.CORAL());
		titleRow.addSheetCell(titleCell);
		rows.add(titleRow);
		
		SheetRow zj1Row = new SheetRow();
		SheetCell zj1Cell = new SheetCell("第1题: 性别",1,3);
		zj1Row.addSheetCell(zj1Cell);
		rows.add(zj1Row);
		
		SheetRow row1 = new SheetRow();
		SheetCell cell1 = new SheetCell("选项",1);
		row1.addSheetCell(cell1);
		
		SheetCell cell2 = new SheetCell("小计",2);
		row1.addSheetCell(cell2);
		
		SheetCell cell3 = new SheetCell("比例",3);
		row1.addSheetCell(cell3);
		rows.add(row1);
		
		
		SheetRow row2 = new SheetRow();
		SheetCell cell21 = new SheetCell("男",1);
		row2.addSheetCell(cell21);
		
		SheetCell cell22 = new SheetCell("4",2);
		row2.addSheetCell(cell22);
		
		SheetCell cell23 = new SheetCell("66.67%",3);
		row2.addSheetCell(cell23);
		
		rows.add(row2);
		
		SheetRow row3 = new SheetRow();
		SheetCell cell31 = new SheetCell("女",1);
		row3.addSheetCell(cell31);
		
		SheetCell cell32 = new SheetCell("2",2);
		row3.addSheetCell(cell32);
		
		SheetCell cell33 = new SheetCell("33.33%",3);
		row3.addSheetCell(cell33);
		
		rows.add(row3);
		
		SheetRow row4 = new SheetRow();
		SheetCell cell41 = new SheetCell("本地有效填写人数",1);
		row4.addSheetCell(cell41);
		
		SheetCell cell42 = new SheetCell("6",2,3);
		row4.addSheetCell(cell42);
		
		rows.add(row4);
		
		
		generateExcel(workbook,rows,3);
	}
}
