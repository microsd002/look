package com.alex.looks.models;

import java.sql.Timestamp;

public class ReturnOrWriteOff {

	private String name;
	private String firm;
	private String provider;
	private String article;
	private int number;
	private double costOutPut;
	private Timestamp time;
	private String nameUserAdmin;
	private String description;
	private String descriptionForWhom;	
	
	
	
	public ReturnOrWriteOff() {}
	
	
	public ReturnOrWriteOff(String name, String firm, String provider,
			String article, int number, double costOutPut, Timestamp time,
			String nameUserAdmin, String description,
			String descriptionForWhom) {
		super();
		this.name = name;
		this.firm = firm;
		this.provider = provider;
		this.article = article;
		this.number = number;
		this.costOutPut = costOutPut;
		this.time = time;
		this.nameUserAdmin = nameUserAdmin;
		this.description = description;
		this.descriptionForWhom = descriptionForWhom;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getCostOutPut() {
		return costOutPut;
	}
	public void setCostOutPut(double costOutPut) {
		this.costOutPut = costOutPut;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getNameUserAdmin() {
		return nameUserAdmin;
	}
	public void setNameUserAdmin(String nameUserAdmin) {
		this.nameUserAdmin = nameUserAdmin;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescriptionForWhom() {
		return descriptionForWhom;
	}
	public void setDescriptionForWhom(String descriptionForWhom) {
		this.descriptionForWhom = descriptionForWhom;
	}
	
	
	public String toStirng() {
		String line = "";
		line = line + "name: " + name + "; ";
		line = line + "firm: " + firm + "; ";
		line = line + "provider: " + provider + "; ";
		line = line + "article: " + article + "; ";
		line = line + "number: " + number + "; ";
		line = line + "cost out put: "
				+ String.format("%.2f", costOutPut) + "; ";
		line = line + "time: " + toStirngTimeUser() + "; ";
		line = line + "nameUserAdmin: " + nameUserAdmin + "; ";
		line = line + "description: " + description + "; ";
		line = line + "descriptionForWhom: " + descriptionForWhom + "; ";
		return line;
	}
	
	public String toStirngTimeUser() {
		String line = "";
		
		line += zeroPlus(time.getDate())+".";
		line += zeroPlus((time.getMonth()+1))+".";
		line += time.getYear()+1900+" â ";
		line += zeroPlus(time.getHours())+":";
		line += zeroPlus(time.getMinutes());
		return line;
	}
	
	public String zeroPlus(int i) {
		if(i<10){
			return "0"+i;
		}
		else{
			return ""+i;
		}
	}
}
