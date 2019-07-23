package com.tgb.lk.demo.model;

public class Student {
	private int id;
	private String name;
	private String classes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//get set·½·¨ÂÔ

	@Override
	public String toString() {
		return "Student [classes=" + classes + ", id=" + id + ", name=" + name
				+ "]";
	}
}
