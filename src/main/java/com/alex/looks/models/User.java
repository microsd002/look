package com.alex.looks.models;

public class User {

	private String fullName;
	private String tel;
	private String status;
	private String username;
	private String password;
	private String hash;
	
	public User(){
		
	}
			
	public User(String fullName, String tel, String status, String username, String password, String hash) {
		super();
		this.fullName = fullName;
		this.tel = tel;
		this.status = status;
		this.username = username;
		this.password = password;
		this.hash = hash;
	}

	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
