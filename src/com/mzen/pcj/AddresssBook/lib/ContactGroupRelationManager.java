package com.mzen.pcj.AddresssBook.lib;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ContactGroupRelationManager {
	ContactManager cm_;
	GroupManager gm_;
	
	Gson gson = new Gson();
	ArrayList<ContactGroupRelation> contactGroupRelations_;
	
	public ContactGroupRelationManager(ContactManager cm, GroupManager gm){
		cm_ = cm;
		gm_ = gm;
		contactGroupRelations_ = new ArrayList<ContactGroupRelation>();
	}
	public void add(ContactGroupRelation cgr){
		contactGroupRelations_.add(cgr);
	}
	
	public ArrayList<Contact> findByGroupKey(int key){
		ArrayList<Contact> tempContacts = new ArrayList<Contact>();
		
		Iterator<ContactGroupRelation> it = contactGroupRelations_.iterator();
		while(it.hasNext()){
			ContactGroupRelation tempCGR = it.next();
			if (tempCGR.getGroupKey() == key){
				tempContacts.add(cm_.findByKey(tempCGR.getContactKey()));
			}
		}
		
		return tempContacts;
		
	}
	
	public ArrayList<Group> findByContactKey(int key){
		ArrayList<Group> tempGroups = new ArrayList<Group>();
		
		Iterator<ContactGroupRelation> it = contactGroupRelations_.iterator();
		while(it.hasNext()){
			ContactGroupRelation tempCGR = it.next();
			if(tempCGR.getContactKey() == key){
				tempGroups.add(gm_.findByKey(tempCGR.getGroupKey()));
			}
		}
		
		return tempGroups; 
	}


	public void saveAllIntoJsonFile() {
		String fileName = FileManager.makeLatestFileName(FileManager.fileName_CGR);
		FileManager.saveStringIntoFile(gson.toJson(contactGroupRelations_),fileName);
		
	}
	public void loadFromJsonFile(String fileName){
		retrieveDataFromJson(FileManager.loadStringFromFile(fileName));
	}
	public void retrieveDataFromJson(String JSON){
		ArrayList<ContactGroupRelation> result = new ArrayList<ContactGroupRelation>();
		if (JSON != "null"){
			result = gson.fromJson(JSON, new TypeToken<ArrayList<ContactGroupRelation>>(){}.getType());
			Iterator<ContactGroupRelation> it = result.iterator();
			while(it.hasNext()){
				add(it.next());
			}
		}else{
			result = null;
		}
		
	}
}
