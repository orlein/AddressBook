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
import com.mzen.pcj.AddresssBook.lib.DatabaseManager;
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
		DatabaseManager.init("newuser", "Dpawps!@#$");
		cm_ = DatabaseManager.loadContactFromDB();
		gm_ = DatabaseManager.loadGroupFromDB();
		cgrm_ = DatabaseManager.loadCGRFromDB(cm_, gm_);

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
			DatabaseManager.endConn();
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
			showContact(cm.findByKey(atoi(query)));
			showEdit(cm.findByKey(atoi(query)));
		}else{
			showMain();
		}
	}
	public void showList(GroupManager gm){
		showFile("Interface_groupList");
		ArrayList<Group> groups = gm.getList();
		if(groups != null){
			showGroup(groups);
			uiOutput("[선택할 번호를 입력하세요]");
			String query = inputString();
			ArrayList<Contact> contacts;
			contacts = cgrm_.findByGroupId(atoi(query));
			showContact(contacts);	
			showEdit(gm_.findById(atoi(query)));
		}
		
		showMain();
		
	}
	
	public void showGroup(Group group){
		uiOutput(
				group.getId()+"\t"+
				group.getName()
				);
	}
	public void showGroup(ArrayList<Group> al){
		Iterator<Group> it = al.iterator();
		Group group;
		while(it.hasNext()){
			group = it.next();
			showGroup(group);
		}
	}
	public void showContact(Contact contact){
		uiOutput(
				contact.getId()+"\t"+
				contact.getName()+"\t"+
				contact.getGender()+"\t"+
				contact.getPhoneNumber()+"\t"+
				contact.getAddress()+"\t"+
				contact.getEmail()+"\t"+
				contact.getMemo()
				);
	}
	public void showContact(ArrayList<Contact> al){
		Iterator<Contact> it = al.iterator();
		Contact contact;
		while(it.hasNext()){
			contact = it.next();
			showContact(contact);
		}
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
		cm_ = new ContactManager();
		gm_ = new GroupManager();
		cgrm_ = new ContactGroupRelationManager(cm_,gm_);
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
		Contact con = new Contact(atts);
		cm_.add(con);
		DatabaseManager.saveContactIntoDB(con);
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
		ArrayList<Contact> result;
		String query;
		switch(temp){
		case '1':
			uiOutput("검색할 이름을 입력하세요");
			query = inputString();
			result = cm.find(query, "NAME");
			showContact(result);
			break;
		case '2':
			uiOutput("검색할 전화번호를 입력하세요");
			query = inputString();
			result = cm.find(query, "PHONENUMBER");
			showContact(result);
			break;
		case '3':
			uiOutput("검색할 주소를 입력하세요");
			query = inputString();
			result = cm.find(query, "ADDRESS");
			showContact(result);
			break;
		case '4':
			uiOutput("검색할 이메일을 입력하세요");
			query = inputString();
			result = cm.find(query, "EMAIL");
			showContact(result);
			break;
		case '5':
			uiOutput("검색할 그룹을 입력하세요");
			query = inputString();
			gm_.find(query);
			break;
		case '6':
			
			break;
		default:
			
			break;
		}
		showMain();
	}
	public void showFind(GroupManager gm){
		showFile("Interface_groupFind");
		char temp = inputSingleChar();
		ArrayList<Group> result;
		String query;
		switch (temp){
		case '1':
			query = inputString();
			result = gm.find(query);
			showGroup(result);
			break;
		case '2':
			break;
		default:
			break;
		}
		showMain();
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
			uiOutput("연락처에 추가할 그룹 이름을 입력하세요");
			str = inputString();
			ArrayList<Group> gr = gm_.find(str);
			if(gr.isEmpty()){
				FileManager.logOutput("No other name is found.");
				Group group = new Group(gm_.getLatestId()+1, str);
				gm_.add(group);
			}
			ContactGroupRelation cgr = new ContactGroupRelation(cgrm_.getLatestId()+1, contact.getId(),gr.get(0).getId());
			cgrm_.add(cgr);
			DatabaseManager.saveCGRIntoDB(cgr);
			
			break;
		case '7':
			FileManager.logOutput("Deleted contact: "+contact.getId()+"\t"+contact.getName());
			cgrm_.remove(contact);
			cm_.remove(contact);
			DatabaseManager.removeContactFromDB(contact);
			break;
		case '8':
			
			break;
		default:
			
			break;
		}
		showMain();
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
			DatabaseManager.editGroupFromDB(group);
			break;
		case '2':
			cgrm_.remove(group);
			gm_.remove(group);
			DatabaseManager.removeGroupFromDB(group);
			break;
		case '3':
			break;
		default:
			break;
		}
		showMain();
	}
	
	public void showAddGroup(){
		showFile("Interface_groupAdd");
		char temp = inputSingleChar();
		switch(temp){
		case '1':
			String[] atts = {"이름"};
			for (int i=0; i<atts.length; i++){
				uiOutput(atts[i]);
				atts[i] = inputString();
				Group gr = new Group(gm_.getLatestId()+1, atts[i]);
				gm_.add(gr);
				DatabaseManager.saveGroupIntoDB(gr);
			}
			break;
		case '2':
			
			break;
		default:
			
			break;
		}

		
	}
	
	//new Contact("Anthony Schwab",Contact.Male,"478229987054","1892 Nabereznyje Telny Lane","Anthony@Tafuna.samoa","example 2","Visitor");
	

}
