package com.simple.common.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SheetRow implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<SheetCell> cells;

	public List<SheetCell> getCells() {
		return cells;
	}

	public void setCells(List<SheetCell> cells) {
		this.cells = cells;
	}
	
	public void addSheetCell(SheetCell sheetCell) {
		if ( null == cells ) {
			cells = new ArrayList<SheetCell>();
		}
		cells.add(sheetCell);
	}
	
}
