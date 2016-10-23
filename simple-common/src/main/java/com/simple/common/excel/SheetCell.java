package com.simple.common.excel;

import java.io.Serializable;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

public class SheetCell implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final short ALIGNMENT_CENTER = CellStyle.ALIGN_CENTER;
	public static final short ALIGNMENT_LEFT = CellStyle.ALIGN_LEFT;
	public static final short ALIGNMENT_RIGHT = CellStyle.ALIGN_RIGHT;
	
	public static final short VERTICAL_CENTER = CellStyle.VERTICAL_CENTER;
	public static final short VERTICAL_BOTTOM = CellStyle.VERTICAL_BOTTOM;
	public static final short VERTICAL_TOP = CellStyle.VERTICAL_TOP;
	
	public static final short BORDER_DASH_DOT = CellStyle.BORDER_DASH_DOT;
	public static final short BORDER_DASH_DOT_DOT = CellStyle.BORDER_DASH_DOT_DOT;
	public static final short BORDER_DASHED = CellStyle.BORDER_DASHED;
	public static final short BORDER_DOTTED = CellStyle.BORDER_DOTTED;
	public static final short BORDER_DOUBLE = CellStyle.BORDER_DOUBLE;
	public static final short BORDER_HAIR = CellStyle.BORDER_HAIR;
	public static final short BORDER_MEDIUM = CellStyle.BORDER_MEDIUM;
	public static final short BORDER_MEDIUM_DASH_DOT = CellStyle.BORDER_MEDIUM_DASH_DOT;
	public static final short BORDER_MEDIUM_DASH_DOT_DOT = CellStyle.BORDER_MEDIUM_DASH_DOT_DOT;
	public static final short BORDER_MEDIUM_DASHED = CellStyle.BORDER_MEDIUM_DASHED;
	public static final short BORDER_NONE = CellStyle.BORDER_NONE;
	public static final short BORDER_SLANTED_DASH_DOT = CellStyle.BORDER_SLANTED_DASH_DOT;
	public static final short BORDER_THICK = CellStyle.BORDER_THICK;
	public static final short BORDER_THIN = CellStyle.BORDER_THIN;
	
	
	private SheetCell() {
		
	}
	
	public SheetCell(String content,int mergetInde,int mergeEnd) {
		this.content = content;
		this.mergeCellIndexStart = (short)(mergetInde);
		this.mergeCellIndexEnd = (short)(mergeEnd); 
	}
	
	public SheetCell(String content,int index) {
		this.content = content;
		this.mergeCellIndexStart = (short)(index);
		this.mergeCellIndexEnd = (short)(index); 
	}
	
	
	//行合并开始序列, 从1开始
	//private short mergedRowIndexStart;
	//行合并结束序列
	//private short mergeRowIndexEnd;
	//列合并开始序列, 从1开始
	private short mergeCellIndexStart;
	//列合并结束序列
	private short mergeCellIndexEnd;
	//列内容
	private String content;
	//水平排列
	private short alignment;
	//竖直排列
	private short verticalAlignment;
	//背景色
	private HSSFColor bgColor;
	//字体
	private String fontName;
	//字体粗细
	private short boldWeight;
	//字体大小
	private short fontSize;
	//字体颜色
	private HSSFColor fontColor;
	//边框
	private short border;
	//边框颜色
	private HSSFColor borderColor;
	
	public short getMergeCellIndexStart() {
		return mergeCellIndexStart;
	}
	public void setMergeCellIndexStart(short mergeCellIndexStart) {
		this.mergeCellIndexStart = mergeCellIndexStart;
	}
	public short getMergeCellIndexEnd() {
		return mergeCellIndexEnd;
	}
	public void setMergeCellIndexEnd(short mergeCellIndexEnd) {
		this.mergeCellIndexEnd = mergeCellIndexEnd;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public short getAlignment() {
		return alignment;
	}
	public void setAlignment(short alignment) {
		this.alignment = alignment;
	}
	public short getVerticalAlignment() {
		return verticalAlignment;
	}
	public void setVerticalAlignment(short verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}
	public String getFontName() {
		return fontName;
	}

	public short getBoldWeight() {
		return boldWeight;
	}

	public short getFontSize() {
		return fontSize;
	}

	public HSSFColor getFontColor() {
		return fontColor;
	}

	public void setFontColor(HSSFColor fontColor) {
		this.fontColor = fontColor;
	}
	
	public short getBorder() {
		return border;
	}

	public HSSFColor getBorderColor() {
		return borderColor;
	}

	public HSSFColor getBgColor() {
		return bgColor;
	}

	public void setBgColor(HSSFColor bgColor) {
		this.bgColor = bgColor;
	}

	public void setFont(String fontName,short size,short weight,HSSFColor color) {
		this.fontName = fontName;
		this.fontSize = size;
		this.boldWeight = weight;
		this.fontColor = color;
	}
	
	public void setBorder(short border,HSSFColor color) {
		this.border = border;
		this.borderColor = color;
	}
	
}
