package com.alex.looks.dao.mybatis;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.alex.looks.chipher.Encryption;
import com.alex.looks.models.DirectoryProduct;
import com.alex.looks.models.EntrancePrice;
import com.alex.looks.models.HalfUser;
import com.alex.looks.models.Provider;
import com.alex.looks.models.ReturnOrWriteOff;
import com.alex.looks.models.SoldProduct;
import com.alex.looks.models.User;

public class MyBatisDAO {

	private SqlSessionFactory sqlSessionFactory = null;
	
	public MyBatisDAO(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public void insertNewEntrancePrice(EntrancePrice a){
			
		
		DirectoryProduct art = selectOnTheArticleFirmDirectoryProduct(new DirectoryProduct(a.getFirm(), a.getArticle()));
		SqlSession session = sqlSessionFactory.openSession();
		try{
			session.insert("EntrancePrice.insert",a);
		}finally{
			session.commit();
			session.close();
			if(art == null){
				DirectoryProduct read = new DirectoryProduct();
				read.setName(a.getName());
				read.setFirm(a.getFirm());
				read.setProvider(a.getProvider());
				read.setArticle(a.getArticle());
				read.setNumber(a.getNumber());
				read.setCostOutPut(a.getCostOutPut());
				read.setTimeUpdate(a.getTime());
				String story = "(@"+a.getCostEntrance()+"@"+a.getNumber()+"@";
				if(a.getTime().getDate() < 10){
					story = story + "0" + a.getTime().getDate();
				}
				else{
					story = story + a.getTime().getDate();
				}
				if(a.getTime().getMonth() < 10){
					story = story + ".0" + (a.getTime().getMonth()+1);
				}
				else{
					story = story + "." + (a.getTime().getMonth()+1);
				}
				story = story + "." + (a.getTime().getYear()+1900) + "@";
				story += a.getProvider()+"@);";
				read.setStory(story);
				read.setNameAdmin(a.getNameAdmin());
				insertNewDirectoryProduct(read);
			}
			else{
				String story = "(@"+a.getCostEntrance()+"@"+a.getNumber()+"@";
				if(a.getTime().getDate() < 10){
					story = story + "0" + a.getTime().getDate();
				}
				else{
					story = story + a.getTime().getDate();
				}
				if(a.getTime().getMonth() < 10){
					story = story + ".0" + (a.getTime().getMonth()+1);
				}
				else{
					story = story + "." + (a.getTime().getMonth()+1);
				}
				story = story + "." + (a.getTime().getYear()+1900) + "@";
				story += a.getProvider()+"@);";
				int num = art.getNumber() + a.getNumber();
					DirectoryProduct read = new DirectoryProduct(a.getName(),
							a.getFirm(), a.getProvider(), a.getArticle(), num, a.getCostOutPut(),
							a.getTime(), story+art.getStory(), a.getNameAdmin());
					
					updateDirectoryProduct(read);
			}
		}
	}
	
	public List<EntrancePrice> selectOnTheArticleEntrancePrice(String article){
		List<EntrancePrice> list = null;
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
            list = session.selectList("EntrancePrice.selectOnTheArticle", article);
        } finally {
            session.close();
        }
        return list;
	}
	
	public List<EntrancePrice> selectAllEntrancePrice(){
		List<EntrancePrice> list = null;
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
            list = session.selectList("EntrancePrice.selectAll");
        } finally {
            session.close();
        }
        return list;
	}
	
	public void insertNewSoldProduct(SoldProduct a){
		SqlSession session = sqlSessionFactory.openSession();
		
		try{
			session.insert("SoldProduct.insert",a);
		}finally{
			session.commit();
			session.close();
			
		}
	}
	
	public List<SoldProduct> selectOnTheArticleSoldProduct(String article){
		List<SoldProduct> list = null;
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
            list = session.selectList("SoldProduct.selectOnTheArticle", article);
        } finally {
            session.close();
        }
        return list;
	}
	
	public List<SoldProduct> selectAllSoldProduct(){
		List<SoldProduct> list = null;
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
            list = session.selectList("SoldProduct.selectAll");
        } finally {
            session.close();
        }
        return list;
	}
	
	public void deleteSoldProduct(int id){

		SqlSession session = sqlSessionFactory.openSession();
		try {
            session.delete("SoldProduct.delete", id);
        } finally {
        	session.commit();
            session.close();
        }
	}
	
	public int selectLimit(){
		int list;
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
            list = session.selectOne("SoldProduct.selectLimit");
        } finally {
            session.close();
        }
		System.out.println("DAO LIMIT" + list);
		return list;
	}
		
	public void insertNewDirectoryProduct(DirectoryProduct a){
		SqlSession session = sqlSessionFactory.openSession();
		
		try{
			session.insert("DirectoryProduct.insert",a);
		}finally{
			session.commit();
			session.close();
		}
	}
	
	public void insertNewReturnOrWriteOff(ReturnOrWriteOff a){
		SqlSession session = sqlSessionFactory.openSession();
		
		try{
			session.insert("ReturnOrWriteOff.insert",a);
		}finally{
			session.commit();
			session.close();
		}
	}
	
	
	public DirectoryProduct selectOnTheArticleDirectoryProduct(String article){
		DirectoryProduct prod = new DirectoryProduct();
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
            prod = session.selectOne("DirectoryProduct.selectOnTheArticle", article);
        } finally {
            session.close();
        }
        return prod;
	}
	
	public DirectoryProduct selectOnTheArticleFirmDirectoryProduct(DirectoryProduct article){
		DirectoryProduct prod = new DirectoryProduct();
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
            prod = session.selectOne("DirectoryProduct.selectOnTheArticleFirm", article);
        } finally {
            session.close();
        }
        return prod;
	}
	
	public List<DirectoryProduct> selectAllDirectoryProduct(){
		List<DirectoryProduct> list = null;
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
            list = session.selectList("DirectoryProduct.selectAll");
        } finally {
            session.close();
        }
        return list;
	}
	
	public List<ReturnOrWriteOff> selectAllReturnOrWriteOff(){
		List<ReturnOrWriteOff> list = null;
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
            list = session.selectList("ReturnOrWriteOff.select");
        } finally {
            session.close();
        }
        return list;
	}
	
	public void updateReturnOrWriteOff(ReturnOrWriteOff a){
		
		
		SqlSession session = sqlSessionFactory.openSession();
		
		try{
			session.update("ReturnOrWriteOff.update",a); 
		}finally{
			session.commit();
			session.close();
		}
	}
	
	public void updateDirectoryProduct(DirectoryProduct a){
		
		
		SqlSession session = sqlSessionFactory.openSession();
		
		try{
			session.update("DirectoryProduct.update",a); 
		}finally{
			session.commit();
			session.close();
		}
	}
	
	public HalfUser selectUser(User us){
		
		List <User> eq = null;		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			eq = session.selectList("User.selectOnTheUser", us);
	    } finally {
            session.close();
        }
		HalfUser halfuser = new HalfUser("", "", "");
		for(User i : eq){
			if (i.getStatus().equals("admin") || i.getStatus().equals("user")) {
				halfuser.setFullName(i.getFullName());
				halfuser.setStatus(i.getStatus());
				halfuser.setHash(i.getHash());
			}
		}
		return halfuser;
		
	}

	public List<User> selectAllUsers() {
		List <User> all = null;		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			all = session.selectList("User.selectAll");
	    } finally {
            session.close();
        }
		
		return all;
	}
	
	public void insertUser(User us) {	
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("User.insert",us);
	    } finally {
	    	session.commit();
            session.close();
        }
	}
	
	public void deleteUser(User us) {	
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.delete("User.delete",us);
	    } finally {
	    	session.commit();
            session.close();
        }
	}
	
	public void updateUser(User us) {	
		SqlSession session = sqlSessionFactory.openSession();
		Encryption cript = new Encryption(us.getUsername(),us.getPassword());
		us.setHash(cript.encryptionMD5());
		try {
			session.update("User.update",us);
	    } finally {
	    	session.commit();
            session.close();
        }
	}
	
	public void insertNewProvider(Provider a){
		SqlSession session = sqlSessionFactory.openSession();
		
		try{
			session.insert("Provider.insert",a);
		}finally{
			session.commit();
			session.close();
			
		}
	}
	
	public List<Provider> selectAllProvides() {
		List <Provider> all = null;		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			all = session.selectList("Provider.selectAll");
	    } finally {
            session.close();
        }
		
		return all;
	}
	
	public void updateProvider(Provider us) {	
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.update("Provider.update",us);
	    } finally {
	    	session.commit();
            session.close();
        }
	}
	
	public void deleteAllDirectory() {	
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.update("DirectoryProduct.deleteAllDirectory");
	    } finally {
	    	session.commit();
            session.close();
        }
	}
	
	public void deleteAllEntrance() {	
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.update("EntrancePrice.deleteAllEntrance");
	    } finally {
	    	session.commit();
            session.close();
        }
	}
	
	public void deleteAllProvider() {	
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.update("Provider.deleteAllProvider");
	    } finally {
	    	session.commit();
            session.close();
        }
	}
	
	public void deleteAllReturnOrWriteOff() {	
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.update("ReturnOrWriteOff.deleteAllReturnOrWriteOff");
	    } finally {
	    	session.commit();
            session.close();
        }
	}
	
	public void deleteAllSoldProduct() {	
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.update("SoldProduct.deleteAllSold");
	    } finally {
	    	session.commit();
            session.close();
        }
	}
}
