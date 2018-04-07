package com.lianjia.boxue.enums;

import javax.persistence.AttributeConverter;

public enum Duty {
	DEFAULT("无", 0), MAIMAI("买卖", 1), ZUNING("租赁", 2), XINGANG("新房", 3), ZONGHE("综合", 4), ZHIXIAO("直销", 5);
	private String desc;
	private int index;

	private Duty(String desc, int index) {
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

	public static Duty getDuty(int i) {
		for (Duty d : Duty.values()) {
			if (d.index == i) {
				return d;
			}
		}
		return Duty.DEFAULT;
	}

	public static Duty getDuty(String desc) {
		for (Duty d : Duty.values()) {
			if (d.desc == desc) {
				return d;
			}
		}
		return Duty.DEFAULT;
	}

	public static class Convert implements AttributeConverter<Duty, Integer> {

		@Override
		public Integer convertToDatabaseColumn(Duty d) {
			return d.getIndex();
		}

		@Override
		public Duty convertToEntityAttribute(Integer i) {
			for (Duty type : Duty.values()) { // 将数字转换为描述
				if (i.equals(type.getIndex())) {
					return type;
				}
			}
			throw new RuntimeException("Unknown database value: " + i);

		}

	}
}
