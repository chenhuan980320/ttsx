package com.ttsx.dto;

public class JsonObject {
	private int total;
	private Object rows;
	
	@Override
	public String toString() {
		return "JsonObject [total=" + total + ", rows=" + rows + "]";
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public JsonObject(int total, Object rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public JsonObject() {
		super();
	}
	
	
	
}
