package com.hieuvt.yamba;

public class MyStatus {

//	private String id;
	private String user;
	private String message;
	private String createAt;
	
	public MyStatus(String user, String message, String createAt){
		setUser(user);
		setMessage(message);
		setCreateAt(createAt);
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
}
