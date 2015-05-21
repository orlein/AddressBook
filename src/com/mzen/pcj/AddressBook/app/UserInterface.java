package com.mzen.pcj.AddressBook.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.mzen.pcj.AddresssBook.lib.Contact;
import com.mzen.pcj.AddresssBook.lib.ContactGroupRelationManager;
import com.mzen.pcj.AddresssBook.lib.ContactManager;
import com.mzen.pcj.AddresssBook.lib.FileManager;
import com.mzen.pcj.AddresssBook.lib.GroupManager;


public class UserInterface {
	ContactManager cm;
	GroupManager gm;
	ContactGroupRelationManager cgrm;
	
	public void logOutput(String str){
		//to Log, modify this method
		System.out.println(str);
	}
	public void uiOutput(String str){
		System.out.println(str);
	}
	public void initiate(){
		cm = new ContactManager();
		gm = new GroupManager();
		cgrm = new ContactGroupRelationManager(cm,gm);
		showMain();
	}
	public void initiate(ContactManager contactManager){
		cm = contactManager;
		showMain();
	}
	
	public char inputSingleChar(){
		InputStream is = System.in;
		char inputChar;
		try {
			inputChar = (char) is.read();
			logOutput("입력함:"+((Character)inputChar).toString());
			is.read();
			is.read();
			return inputChar;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("resource")
	public String inputString(){
		String msg;
		Scanner scan = new Scanner(System.in);
		msg = scan.nextLine();
		
		
		logOutput("입력함:"+msg);
		
		return msg;
	}
	public void showMain(){
		showFile("Interface_main");
		char temp = inputSingleChar();
		switch(temp){
		case '1':
			showList(cm);
			break;
		case '2':
			showAdd();
			break;
		case '3':
			showFind();
			break;
		case '4':
			showSave();
			break;
		case '5':
			showLoad();
			break;
		case '6':
			showGroup();
			break;
		case '7':
			break;
		case '8':
			System.exit(0);
			break;
		default:
			break;
		}
	}
	public void showList(ContactManager cm){
		showFile("Interface_list");
		ArrayList<Contact> contacts = cm.getList();
		if (contacts != null){
			Iterator<Contact> it = contacts.iterator();
			while(it.hasNext()){
				Contact temp = it.next();
				logOutput(
				temp.getKey()+"\t"+
				temp.getName()+"\t"+
				temp.getGender()+"\t"+
				temp.getPhoneNumber()+"\t"+
				temp.getAddress()+"\t"+
				temp.getEmail()+"\t"+
				temp.getMemo()
				);
			}
		}
		showMain();
	}
	public void showFile(String fileName){
		FileManager.readFileAndShow(fileName);
	}
	public void showLoad(){
		showFile("Interface_load");
		String[] fileList = FileManager.findExistingFiles();
		char input;
		String fileName;
		for(int i=0; i<fileList.length; i++){
			uiOutput("* "+i+"\t"+fileList[i]);
		}
		uiOutput("***************************************");
		input = inputSingleChar();
		fileName = FileManager.fileName + ((Character)input).toString() + ".JSON";
		cm.loadFromJsonFile(fileName);
		logOutput("load to cm");
		showList(cm);
		showMain();
	}
	public void showAdd(){
		showFile("Interface_add");
		String[] atts = {"이름","성(M/F)","전화번호","주소","이메일","메모"};
		for (int i=0; i<atts.length; i++){
			uiOutput(atts[i]);
			atts[i] = inputString();
		}
		cm.add(new Contact(atts));
		showMain();
	}
	public void showSave(){
		showFile("Interface_save");
		cm.saveAllContactsIntoJsonFile();
		showMain();
	}
	public void showFind(){
		showFile("Interface_find");
		char temp = inputSingleChar();
		String query;
		switch(temp){
		case '1':
			query = inputString();
			cm.find(query, ContactManager.Attributes.NAME);
			break;
		case '2':
			query = inputString();
			cm.find(query, ContactManager.Attributes.PHONENUMBER);
			break;
		case '3':
			query = inputString();
			cm.find(query, ContactManager.Attributes.ADDRESS);
			break;
		case '4':
			query = inputString();
			cm.find(query, ContactManager.Attributes.EMAIL);
			break;
		case '5':
			query = inputString();
			
			break;
		case '6':
			showMain();
			break;
		default:
			break;
		}
	}
	public void showGroup(){
		
	}
	
	//new Contact("Anthony Schwab",Contact.Male,"478229987054","1892 Nabereznyje Telny Lane","Anthony@Tafuna.samoa","example 2","Visitor");
	

}
