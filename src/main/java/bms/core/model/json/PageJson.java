package bms.core.model.json;

import java.util.List;

import bms.core.model.Group;

public class PageJson {
	private int total;
	private int pageSize = 15;
	private int pageNumber;
	private List rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<Group> getRows() {
		return rows;
	}

	public void setRows(List<Group> rows) {
		this.rows = rows;
	}

}
