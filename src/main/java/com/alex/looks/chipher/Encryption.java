package com.alex.looks.chipher;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;

public class Encryption {
	private String username;
	private String password;
	private String create = "AlexKrasnianskiy";
	
	
	
	public Encryption(String username, String password) {
		this.username = username;
		this.password = password;
	}



	public String encryptionMD5(){
		String st = username + create + password;
		String md5 = DigestUtils.md5Hex(st);
		System.out.println(md5);
		return md5;
	}
	
}
