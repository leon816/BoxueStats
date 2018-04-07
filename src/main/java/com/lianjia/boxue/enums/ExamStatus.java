package com.lianjia.boxue.enums;

public enum ExamStatus {
	DaiKao("待考", 0), YiKao("已考", 1), BeiAn("备案", 2);
	private String desc;
	private int index;
	
	private ExamStatus(String desc, int index) {
		this.desc = desc;
		this.index = index;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
