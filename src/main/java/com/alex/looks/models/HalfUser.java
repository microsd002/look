package com.alex.looks.models;

public class HalfUser {
	
	private String fullName;
	private String status;
	private String hash;
	
	public HalfUser(){
		
	}
	
	public HalfUser(String fullName, String status, String hash) {
		this.fullName = fullName;
		this.status = status;
		this.hash = hash;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	
}
