package com.alex.looks.dao.mybatis;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import com.alex.looks.chipher.Encryption;
import com.alex.looks.dao.mybatis.MyBatisConnectionFactory;
import com.alex.looks.dao.mybatis.MyBatisDAO;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.model.EachTestNotifier;
import org.xml.sax.SAXException;

import com.alex.looks.models.DirectoryProduct;
import com.alex.looks.models.EntrancePrice;
import com.alex.looks.models.HalfUser;
import com.alex.looks.models.Provider;
import com.alex.looks.models.SoldProduct;
import com.alex.looks.models.User;

public class MyBatisDAOTest {

	private MyBatisDAO dao = new MyBatisDAO(MyBatisConnectionFactory.getSqlSessionFactory());
	
	/*@Test
	public void testCrypt(){
		dao.deleteAllProvider();
		dao.deleteAllDirectory();
		dao.deleteAllEntrance();
		dao.deleteAllReturnOrWriteOff();
		dao.deleteAllSoldProduct();
		
	}*/
		/*f.read();
		
		try {
			f.reserve(new File("webapp/upload/reserve.xml").getAbsolutePath());
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	
	/*@Test
	public void testCrypt(){
		Encryption cry = new Encryption("anastasia", "root937992");
		String d = cry.encryptionMD5();
	}*/
	
	
	/*public void testInsertNewEntrancePrice(){
		String text = "2015-01-26 11:27:00";
		Timestamp tim = Timestamp.valueOf(text);
		EntrancePrice n = new EntrancePrice("Ó˜ÍË", "benq", " ‡ÒÌˇÌÒÍËÈ ¿.¬.", "dg-532G", 36, 356, 700, tim, "¬Î‡‰ËÏÂ ¬‡ÎÂËÂ‚Ë˜ ¡ÂÁÁÛ·ÂÌÍÓ");
		dao.insertNewEntrancePrice(n);
	}*/
	
	/*@Test
	public void testSelectOnTheArticleEntrancePrice(){
		List<EntrancePrice> list = null;
		String f = "999999";
		list = dao.selectOnTheArticleEntrancePrice(f);
		for(EntrancePrice i : list){
			System.out.println(i.toStirng());
		}
	}*/
	
	/*@Test
	public void testSelectAllEntrancePrice(){
		List<EntrancePrice> list = null;
		list = dao.selectAllEntrancePrice();
		for(EntrancePrice i : list){
			System.out.println(i.toStirng());
		}
	}*/
	
	/*@Test
	public void testInsertNewSoldProduct(){
		String text = "2015-01-26 11:27:00";
		Timestamp tim = Timestamp.valueOf(text);
		//System.out.println(tim);
		SoldProduct n = new SoldProduct("O—á–∫–∏", "DataArt", "df-31-89-B", 2, 250.00, 50.00, 450.00, tim);
		//–ø—Ä–µ–¥—É—Å–º–æ—Ç—Ä–µ—Ç—å –≤—ã—á–∏—Å–ª–µ–Ω–∏–µ —Å—É–º–º—ã –≤ –∫–æ–Ω—Ç—Ä–æ–ª–µ—Ä—Ä–µ –∏–ª–∏ –Ω–∞ html —Å—Ç—Ä–∞–Ω–∏—Ü–µ
		dao.insertNewSoldProduct(n);
	}*/
	
	/*@Test
	public void testSelectOnTheArticleSoldProduct(){
		List<SoldProduct> list = null;
		String f = "999999";
		list = dao.selectOnTheArticleSoldProduct(f);
		for(SoldProduct i : list){
			System.out.println(i.toStirng());
		}
	}*/
	
	/*@Test
	public void testSelectAllSoldProduct(){
		List<SoldProduct> list = null;
		list = dao.selectAllSoldProduct();
		for(SoldProduct i : list){
			System.out.println(i.toStirng());
		}
	}*/
	
	/*@Test
	public void testSelectLimit(){
		
		int t = dao.selectLimit();
		System.out.println(t);
	}*/
	
	/*@Test
	public void testInsertNewDirectoryProduct(){
		String text = "2015-02-26 17:21:19";
		Timestamp tim = Timestamp.valueOf(text);
		//System.out.println(tim);
		DirectoryProduct n = new DirectoryProduct("–æ—á–∫–∏", "—Å—Ç—Ä–∞–π–∫", "db67-04h", 2, 200.66, tim);
		//–ø—Ä–µ–¥—É—Å–º–æ—Ç—Ä–µ—Ç—å –≤—ã—á–∏—Å–ª–µ–Ω–∏–µ —Å—É–º–º—ã –≤ –∫–æ–Ω—Ç—Ä–æ–ª–µ—Ä—Ä–µ –∏–ª–∏ –Ω–∞ html —Å—Ç—Ä–∞–Ω–∏—Ü–µ
		dao.insertNewDirectoryProduct(n);
	}*/
	
	/*@Test
	public void testSelectOnTheArticleDirectoryProduct(){
		DirectoryProduct prod = new DirectoryProduct();
		String f = "999999";
		prod = dao.selectOnTheArticleDirectoryProduct(f);
		
		System.out.println(prod.toStirng());
	}*/
	
	/*@Test
	public void testSelectAllSoldProduct(){
		List<DirectoryProduct> list = null;
		list = dao.selectAllDirectoryProduct();
		for(DirectoryProduct i : list){
			System.out.println(i.toStirng());
		}
	}*/
	
	/*@Test
	public void testUpdateDirectoryProduct(){
		String text = "2015-02-26 17:21:19";
		Timestamp tim = Timestamp.valueOf(text);
		DirectoryProduct read = new DirectoryProduct("1112", "2222", "1994", 8, 60.77, tim);
		dao.updateDirectoryProduct(read);
	}*/
	
	/*@Test
	public void testSelectUser(){
		User us = new User("", "vladimirbezzubenko", "mit1902sumi1902", "");
		HalfUser f = dao.selectUser(us);
		System.out.println("test - "+f.getHash()+"    "+f.getStatus());
	}*/
	
	/*@Test
	public void testSelectAllUsers(){
		List<User> us = dao.selectAllUsers();
		Assert.assertTrue(us.size()>1);
	}*/
	
	/*@Test
	public void testInsertUser(){
		User us = new User("user", "alexkrasnynskiy", "1234");
		dao.insertUser(us);
	}*/
	/*@Test
	public void testMy(){
		String line = "(@260.0@5@08.02.2015@);(@110.0@6@27.01.2015@);";
		String rez="";
		while(line.indexOf("@") != -1){
			line = line.substring(line.indexOf("@")+1, line.length());
			rez = "¬ıÓ‰Ì‡ˇ ˆÂÌ‡:" + line.substring(0, line.indexOf("@"))+" „Ì.|";
			line = line.substring(line.indexOf("@")+1, line.length());
			rez = rez + " ÓÎ-‚Ó:" + line.substring(0, line.indexOf("@"))+" ¯Ú.|";
			line = line.substring(line.indexOf("@")+1, line.length());
			rez = rez + " " + line.substring(0, line.indexOf("@"))+"";
			line = line.substring(line.indexOf("@")+1, line.length());
			if(line.indexOf("@") == -1){
				break;
			}
		}
		System.out.println(rez);
	}*/
}
