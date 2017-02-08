package com.simple.common.util;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author renren
 */

public class PageResult implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_PAGE = 10;
	
	/*总记录数**/
	private int totalCount;
	/*每页个数**/
	private int pageSize;
	/*当前页号**/
	private int currentIndex;
	/*总页数**/
	private int totalIndex;
	/*数据**/
	private List datas;
	
	public PageResult(int totalCount,int pageSize,int currentIndex,List datas) {
		this.totalCount = totalCount > 0 ? totalCount : 0 ;
		this.pageSize = pageSize;
		this.currentIndex = currentIndex > 1 ? currentIndex : 1 ;
		this.datas = datas;
	}
	
	public PageResult(int totalCount,int currentIndex,List datas) {
		this.totalCount = totalCount> 0 ? totalCount : 0 ;
		this.pageSize = DEFAULT_PAGE;
		this.currentIndex = currentIndex > 1 ? currentIndex : 1 ;
		this.datas = datas;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public int getTotalIndex() {
		if(totalCount%pageSize==0) {
			totalIndex = totalCount/pageSize;
		}else {
			totalIndex = totalCount/pageSize + 1;
		}
		totalIndex = totalIndex > 1 ? totalIndex : 1;
		return totalIndex;
	}

	public List getDatas() {
		return datas;
	}

	@Override
	public String toString() {
		return "PageResult [totalCount=" + totalCount + ", pageSize="
				+ pageSize + ", currentIndex=" + currentIndex + ", totalIndex="
				+ totalIndex + ", datas=" + datas + "]";
	}
	
}
