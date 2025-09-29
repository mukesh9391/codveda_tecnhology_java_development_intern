package com.bank.exception;

public class BankUserException extends RuntimeException
{
private String msg;

public BankUserException(String msg) 
{
	this.msg = msg;
}

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}

}
