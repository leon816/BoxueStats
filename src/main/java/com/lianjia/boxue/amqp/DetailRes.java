package com.lianjia.boxue.amqp;

public class DetailRes {
	boolean isSuccess;
    String errMsg;
	public boolean isSuccess() {
		return isSuccess;
	}
	public DetailRes(boolean isSuccess, String errMsg) {
		super();
		this.isSuccess = isSuccess;
		this.errMsg = errMsg;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
