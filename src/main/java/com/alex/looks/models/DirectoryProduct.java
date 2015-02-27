package com.alex.looks.models;

import java.sql.Timestamp;

public class DirectoryProduct {
	private String name;
	private String firm;
	private String provider;
	private String article;
	private int number;
	private double costOutPut;
	private Timestamp timeUpdate;
	private String story;
	private String nameAdmin;
	
	public DirectoryProduct(String firm, String article) {
		this.firm = firm;
		this.article = article;
	}
	
	public DirectoryProduct(String name, String firm, String article,
			int number, double costOutPut, Timestamp timeUpdate) {//user
		super();
		this.name = name;
		this.firm = firm;
		this.article = article;
		this.number = number;
		this.costOutPut = costOutPut;
		this.timeUpdate = timeUpdate;
	}

	public DirectoryProduct(String name, String firm, String provider, String article,
			int number, double costOutPut, String story, String nameAdmin) {// admin
		super();
		this.name = name;
		this.firm = firm;
		this.provider = provider;
		this.article = article;
		this.number = number;
		this.costOutPut = costOutPut;
		this.story = story;
		this.nameAdmin = nameAdmin;
	}
	
	public DirectoryProduct(String name, String firm, String provider, String article,
			int number, double costOutPut, Timestamp timeUpdate, String story, String nameAdmin) {// admin
		super();
		this.name = name;
		this.firm = firm;
		this.provider = provider;
		this.article = article;
		this.number = number;
		this.costOutPut = costOutPut;
		this.timeUpdate = timeUpdate;
		this.story = story;
		this.nameAdmin = nameAdmin;
	}
	
	public DirectoryProduct(){
		
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
	
	public Timestamp getTimeUpdate() {
		return timeUpdate;
	}

	public void setTimeUpdate(Timestamp timeUpdate) {
		this.timeUpdate = timeUpdate;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	public String getNameAdmin() {
		return nameAdmin;
	}
	public void setNameAdmin(String nameAdmin) {
		this.nameAdmin = nameAdmin;
	}

	// myself
	public String toStirng() {
		String line = "";
		line = line + "name: " + name + "; ";
		line = line + "firm: " + firm + "; ";
		line = line + "provider: " + provider + "; ";
		line = line + "article: " + article + "; ";
		line = line + "number: " + number + "; ";
		line = line + "cost out put: "
				+ String.format("%.2f", costOutPut) + "; ";
		line = line + "time update product: " + timeUpdate + "; ";
		line = line + "story: " + story + "; ";
		line = line + "nameAdmin: " + nameAdmin + "; ";
		return line;
	}
	
	public String toStirngTimeUser() {
		String line = "";
		
		line += zeroPlus(timeUpdate.getDate())+".";
		line += zeroPlus((timeUpdate.getMonth()+1))+".";
		line += timeUpdate.getYear()+1900+" â ";
		line += zeroPlus(timeUpdate.getHours())+":";
		line += zeroPlus(timeUpdate.getMinutes());
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
