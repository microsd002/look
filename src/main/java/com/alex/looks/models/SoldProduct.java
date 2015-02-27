package com.alex.looks.models;

import java.sql.Timestamp;
import java.util.Date;

public class SoldProduct {
	private int id;
	private String name;
	private String firm;
	private String article;
	private int number;
	private double costOutPut;
	private double discount;
	private double sum;
	private Timestamp time;
	private String nameUserAdmin;
	
	
	public SoldProduct(int id, String name, String firm,
			String article, int number, double costOutPut, double discount,
			double sum, Timestamp time, String nameUserAdmin) {
		super();
		this.id = id;
		this.name = name;
		this.firm = firm;
		this.article = article;
		this.number = number;
		this.costOutPut = costOutPut;
		this.discount = discount;
		this.sum = sum;
		this.time = time;
		this.nameUserAdmin = nameUserAdmin;
	}
	
	public SoldProduct(String name, String firm,
			String article, int number, double costOutPut, double discount,
			double sum, String nameUserAdmin) {
		super();
		this.name = name;
		this.firm = firm;
		this.article = article;
		this.number = number;
		this.costOutPut = costOutPut;
		this.discount = discount;
		this.sum = sum;
		this.nameUserAdmin = nameUserAdmin;
	}
	
	public SoldProduct(){
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
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

	// myself
	public String toStirng() {
		String line = "";
		line = line + "name: " + name + "; ";
		line = line + "firm: " + firm + "; ";
		line = line + "article: " + article + "; ";
		line = line + "number: " + number + "; ";
		line = line + "cost out put: "
				+ String.format("%.2f", costOutPut) + "; ";
		line = line + "discount: " + discount + "; ";
		line = line + "sum: " + sum + "; ";
		line = line + "time: " + time + "; ";
		line = line + "nameUserAdmin: " + nameUserAdmin + "; ";
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
