package com.tigerslab.tigererp.model.httphelper;

public class ResponseMetadata {
	
	private String status;
	private String messeges;
	private Object data;
	
	public ResponseMetadata() {
		
	}
	
	public ResponseMetadata(String status) {
		this.status = status;
	}
	
	public ResponseMetadata(String status, String messages) {
		this.status = status;
		this.messeges = messages;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMesseges() {
		return messeges;
	}
	public void setMesseges(String messeges) {
		this.messeges = messeges;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseMetadata [status=" + status + ", messeges=" + messeges + ", data=" + data + "]";
	}

}
