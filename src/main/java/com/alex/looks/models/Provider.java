package com.alex.looks.models;

public class Provider {

	private int id;
	private String fullName;
	private String adress;
	private String tel;
	private String site;
	private String email;
	private String description;
	
	
	
	public Provider(String fullName, String adress, String tel, String site,
			String email, String description) {
		super();
		this.fullName = fullName;
		this.adress = adress;
		this.tel = tel;
		this.site = site;
		this.email = email;
		this.description = description;
	}


	public Provider() {}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}	
	
	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getAdress() {
		return adress;
	}


	public void setAdress(String adress) {
		this.adress = adress;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String toStirng() {
		String line = "";
		line = line + "fullName: " + fullName + "; ";
		line = line + "address: " + adress + "; ";
		line = line + "tel: " + tel + "; ";
		line = line + "site: " + site + "; ";
		line = line + "email: " + email + "; ";
		line = line + "description: " + description + "; ";
		return line;
	}
	
	
	
}
