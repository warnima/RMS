package com.test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.codec.binary.Base64;
public class Main {

	public static void main(String[] args) {
		
		  /*String plainCredentials="ndbLeasing:AAbank@1987"; 
		  String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
		  System.out.println(base64Credentials);
		  
		  String decodeCredentials=new String(Base64.decodeBase64(base64Credentials.getBytes())); 
		  System.out.println(decodeCredentials);*/
		
		//encode
		/*try {
			File decfile = new File("D:\\ENTDOWN_A_NDB-N-0000_02032018.txt"); 
			FileWriter encWriter = new FileWriter("D:\\EncFile\\ENTDOWN_A_NDB-N-0000_02032018.txt");
			BufferedReader br = new BufferedReader(new FileReader(decfile)); 
			String st; 
		    while ((st = br.readLine()) != null) {
		    	String base64Credentials = new String(Base64.encodeBase64(st.getBytes()));
		    	encWriter.append(base64Credentials+"\n");
		    } 
		    encWriter.flush();
		    encWriter.close();
		    
		}catch (Exception e) {
			e.printStackTrace();
		}*/
		
		//decode 
		
		try {
			File encfile = new File("D:\\EncFile\\ENTDOWN_A_NDB-N-0000_02032018.txt"); 
			FileWriter decWriter = new FileWriter("D:\\EncFile\\DecFile\\\\ENTDOWN_A_NDB-N-0000_02032018.txt");
			BufferedReader br = new BufferedReader(new FileReader(encfile)); 
			String st; 
		    while ((st = br.readLine()) != null) {
		    	String decodeCredentials = new String(Base64.decodeBase64(st.getBytes()));
		    	decWriter.append(decodeCredentials+"\n");
		    } 
		    decWriter.close();
		    
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
