package com.mzen.pcj.AddressBook.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.mzen.pcj.AddressBook.test.Test;
import com.mzen.pcj.AddresssBook.lib.Contact;
import com.mzen.pcj.AddresssBook.lib.ContactGroupRelation;
import com.mzen.pcj.AddresssBook.lib.ContactGroupRelationManager;
import com.mzen.pcj.AddresssBook.lib.ContactManager;
import com.mzen.pcj.AddresssBook.lib.FileManager;
import com.mzen.pcj.AddresssBook.lib.Group;
import com.mzen.pcj.AddresssBook.lib.GroupManager;


public class UserInterface {
	ContactManager cm_;
	GroupManager gm_;
	ContactGroupRelationManager cgrm_;
	
	Test t1 = new Test();
	
	
	
	public static int atoi(String str) {
		int radix = 10;		
		byte[] temp = str.getBytes();
		int result = 0;
		for(int i=0;i<temp.length;i++) {	
			if (temp[i] < '0' || temp[i] > '9') { // 0~9 넘어갈경우 (문자 방지)
				throw new NumberFormatException();
			}
			result = (result*radix) + temp[i] - '0';
		}	
		
		return result;
	}
	public void uiOutput(String str){
		System.out.println(str);
	}
	public void initiate(){
		cm_ = new ContactManager();
		gm_ = new GroupManager();
		cgrm_ = new ContactGroupRelationManager(cm_,gm_);
		
		//Testing
		t1.makeData(cm_);
		//
		showMain();
	}
	
	public char inputSingleChar(){
		InputStream is = System.in;
		char inputChar;
		try {
			inputChar = (char) is.read();
			FileManager.logOutput("입력함:"+((Character)inputChar).toString());
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
		
		
		FileManager.logOutput("입력함:"+msg);
		
		return msg;
	}
	
	
	
	public void showMain(){
		showFile("Interface_main");
		char temp = inputSingleChar();
		switch(temp){
		case '1':
			showList(cm_);
			break;
		case '2':
			showAddContact();
			break;
		case '3':
			showFind(cm_);
			break;
		case '4':
			showSave();
			break;
		case '5':
			showLoad();
			break;
		case '6':
			showList(gm_);
			break;
		case '7':
			showAddGroup();
			break;
		case '8':
			System.exit(0);
			break;
		default:
			showMain();
			break;
		}
	}
	public void showList(ContactManager cm){
		showFile("Interface_contactList");
		ArrayList<Contact> contacts = cm.getList();
		if (contacts != null){
			Iterator<Contact> it = contacts.iterator();
			while(it.hasNext()){
				Contact temp = it.next();
				showContact(temp);
			}
		}
		uiOutput("편집하시겠습니까?(y/n)");
		char yn = inputSingleChar();
		if (yn == 'y'){
			uiOutput("[편집할 번호를 입력하세요]");
			String query = inputString();
			showEdit(cm.findByKey(atoi(query)));
		}else{
			showMain();
		}
	}
	public void showList(GroupManager gm){
		showFile("Interface_groupList");
		ArrayList<Group> groups = gm.getList();
		if(groups != null){
			Iterator<Group> it = groups.iterator();
			while(it.hasNext()){
				Group temp = it.next();
				showGroup(temp);
			}
		}
		uiOutput("선택하시겠습니까?(y/n)");
		char yn = inputSingleChar();
		if (yn == 'y'){
			uiOutput("[선택할 번호를 입력하세요]");
			String query = inputString();
			ArrayList<Contact> contacts;
			contacts = cgrm_.findByGroupKey(atoi(query));
			Iterator<Contact> it2 = contacts.iterator();
			while(it2.hasNext()){
				Contact t2 = it2.next();
				showContact(t2);
			}
		}else{
			showMain();
		}
	}
	
	public void showGroup(Group group){
		uiOutput(
				group.getKey()+"\t"+
				group.getName()
				);
	}
	public void showContact(Contact contact){
		uiOutput(
				contact.getKey()+"\t"+
				contact.getName()+"\t"+
				contact.getGender()+"\t"+
				contact.getPhoneNumber()+"\t"+
				contact.getAddress()+"\t"+
				contact.getEmail()+"\t"+
				contact.getMemo()
				);
	}
	public void showFile(String fileName){
		FileManager.readFileAndShow(fileName);
	}
	public void showLoad(){
		showFile("Interface_load");
		String[] fileList = FileManager.findExistingFiles();
		String input;
		String fileName;
		for(int i=0; i<fileList.length; i++){
			uiOutput("* "+i+"\t"+fileList[i]);
		}
		uiOutput("***************************************");
		input = inputString();
		fileName = FileManager.fileName_AB + input + ".JSON";
		cm_.loadFromJsonFile(fileName);
		fileName = FileManager.fileName_G + input + ".JSON";
		gm_.loadFromJsonFile(fileName);
		fileName = FileManager.fileName_CGR + input + ".JSON";
		cgrm_.loadFromJsonFile(fileName);
		FileManager.logOutput("load to cm");
		showList(cm_);
		showMain();
	}
	public void showAddContact(){
		
		showFile("Interface_contactAdd");
		String[] atts = {"이름","성(M/F)","전화번호","주소","이메일","메모"};
		for (int i=0; i<atts.length; i++){
			uiOutput(atts[i]);
			atts[i] = inputString();
		}
		cm_.add(new Contact(atts));
		showMain();
	}
	public void showSave(){
		showFile("Interface_save");
		cm_.saveAllIntoJsonFile();
		gm_.saveAllIntoJsonFile();
		cgrm_.saveAllIntoJsonFile();
		showMain();
	}
	public void showFind(ContactManager cm){
		showFile("Interface_contactFind");
		char temp = inputSingleChar();
		Contact result;
		String query;
		switch(temp){
		case '1':
			query = inputString();
			result = cm.find(query, ContactManager.Attributes.NAME);
			showContact(result);
			break;
		case '2':
			query = inputString();
			result = cm.find(query, ContactManager.Attributes.PHONENUMBER);
			showContact(result);
			break;
		case '3':
			query = inputString();
			result = cm.find(query, ContactManager.Attributes.ADDRESS);
			showContact(result);
			break;
		case '4':
			query = inputString();
			result = cm.find(query, ContactManager.Attributes.EMAIL);
			showContact(result);
			break;
		case '5':
			query = inputString();
			//cgrm.find(query, GROUP);
			break;
		case '6':
			showMain();
			break;
		default:
			showMain();
			break;
		}
	}
	public void showFind(GroupManager gm){
		showFile("Interface_groupFind");
		char temp = inputSingleChar();
		Group result;
		String query;
		switch (temp){
		case '1':
			query = inputString();
			result = gm.find(query);
			showGroup(result);
			break;
		case '2':
			showMain();
			break;
		default:
			showMain();
			break;
		}
	}
	
	
	public void showEdit(Contact contact){
		showFile("Interface_contactEdit");
		char temp = inputSingleChar();
		String str;
		switch(temp){
		case '1':
			str = inputString();
			contact.setName(str);
			showContact(contact);
			break;
		case '2':
			str = inputString();
			contact.setGender(str);
			showContact(contact);
			break;
		case '3':
			str = inputString();
			contact.setAddress(str);
			showContact(contact);
			break;
		case '4':
			str = inputString();
			contact.setEmail(str);
			showContact(contact);
			break;
		case '5':
			str = inputString();
			contact.setMemo(str);
			showContact(contact);
			break;
		case '6':
			str = inputString();
			Group gr = gm_.find(str);
			if(gr==null){
				gr = new Group(str);
				gm_.add(gr);
			}
			cgrm_.add(new ContactGroupRelation(contact.getKey(),gr.getKey()));
			break;
		case '7':
			showMain();
			break;
		default:
			showMain();
			break;
		}
	}
	
	public void showEdit(Group group){
		showFile("Interface_groupEdit");
		char temp = inputSingleChar();
		String str;
		switch(temp){
		case '1':
			str = inputString();
			group.setName(str);
			showGroup(group);
			break;
		case '2':
			showMain();
			break;
		default:
			showMain();
			break;
		}
	}
	
	public void showAddGroup(){
		showFile("Interface_groupAdd");
		String[] atts = {"이름"};
		for (int i=0; i<atts.length; i++){
			uiOutput(atts[i]);
			atts[i] = inputString();
		}
		gm_.add(new Group(atts));
		showMain();
	}
	
	//new Contact("Anthony Schwab",Contact.Male,"478229987054","1892 Nabereznyje Telny Lane","Anthony@Tafuna.samoa","example 2","Visitor");
	

}
