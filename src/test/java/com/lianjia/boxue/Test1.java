package com.lianjia.boxue;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//1521876297219
		//ObjectMapper om = new ObjectMapper();
		System.out.println(DateUtils.truncate(new Date(), Calendar.MONTH));
		System.out.println(DateUtils.ceiling(new Date(), Calendar.MONTH));
		//System.out.println(Utils.getWrapperJson("ddd", "success", 1));
	}

}

class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 527186101690322424L;
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
