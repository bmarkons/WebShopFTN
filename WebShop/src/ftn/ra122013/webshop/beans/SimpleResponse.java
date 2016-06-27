package ftn.ra122013.webshop.beans;

public class SimpleResponse {
	private String msg;
	
	public SimpleResponse(String msg){
		this.setMsg(msg);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
