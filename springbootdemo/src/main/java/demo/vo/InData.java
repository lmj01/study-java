package demo.vo;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonComponent
@JsonIgnoreProperties(ignoreUnknown=true)
public class InData {
	private int code;
	private String message;
	
	public InData() {
		code = 0;
		message = "";
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
//		return super.toString();
		return "InData{"+"code='"+code+'\''+", message='" + message + '\'' + '}';
	}
	
}
