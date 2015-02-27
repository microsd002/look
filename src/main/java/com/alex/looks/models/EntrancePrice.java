package com.alex.looks.models;

import java.sql.Timestamp;

public class EntrancePrice {

	private String name;
	private String firm;
	private String provider;
	private String article;
	private int number;
	private double costEntrance;
	private double costOutPut;
	private Timestamp time;
	private String nameAdmin;
	
	public EntrancePrice(String name, String firm, String provider,
			String article, int number, double costEntrance, double costOutPut,
			Timestamp time, String nameAdmin) {
		super();
		this.name = name;
		this.firm = firm;
		this.provider = provider;
		this.article = article;
		this.number = number;
		this.costEntrance = costEntrance;
		this.costOutPut = costOutPut;
		this.time = time;
		this.nameAdmin = nameAdmin;
	}
	
	public EntrancePrice(){
		
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

	public double getCostEntrance() {
		return costEntrance;
	}

	public void setCostEntrance(double costEntrance) {
		this.costEntrance = costEntrance;
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
		line = line + "cost Entrance: " + String.format("%.2f", costEntrance) + "; ";
		line = line + "Cost Out Put: " + String.format("%.2f", costOutPut) + "; ";
		line = line + "Time: " + time + "; ";
		line = line + "nameAdmin: " + nameAdmin + "; ";
		return line;
	}
	
	public String toStirngTimeUser() {
		String line = "";
		
		line += zeroPlus(time.getDate())+".";
		line += zeroPlus((time.getMonth()+1))+".";
		line += time.getYear()+1900;
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
