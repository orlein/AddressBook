package com.pcj.AddresssBook.lib;

import java.util.ArrayList;
import java.util.Iterator;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ContactGroupRelationManager {
	int key;
	
	ContactManager cm_;
	GroupManager gm_;
	
	Gson gson = new Gson();
	ArrayList<ContactGroupRelation> contactGroupRelations_;
	
	public ContactGroupRelationManager(ContactManager cm, GroupManager gm){
		cm_ = cm;
		gm_ = gm;
		contactGroupRelations_ = new ArrayList<ContactGroupRelation>();
		key = 0;
	}
	public void add(ContactGroupRelation cgr){
		key++;
		cgr.setKey(key);;
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
	public ContactGroupRelation findByKey(int key){
		Iterator<ContactGroupRelation> it = contactGroupRelations_.iterator();
		ContactGroupRelation result;
		while(it.hasNext()){
			result = it.next();
			if(result.getKey() == key){
				return result;
			}
		}
		return null;
	}
	public boolean remove(ContactGroupRelation cgr) {
		if (contactGroupRelations_.remove(cgr)){
			return true;
		}else
			return false;
	}
	public void remove(Contact contact){
		Iterator<ContactGroupRelation> it = contactGroupRelations_.iterator();
		ArrayList<Integer> arrayToBeDeleted = new ArrayList<Integer>(); 
		int contactKey = contact.getKey();
		while(it.hasNext()){
			ContactGroupRelation tempCGR = it.next();
			if (contactKey == tempCGR.getContactKey()){
				arrayToBeDeleted.add((Integer)tempCGR.getKey());
			}
		}
		
		Iterator<Integer> itt = arrayToBeDeleted.iterator();
		while(itt.hasNext()){
			Integer i = itt.next();
			remove(findByKey(i));
		}
		
	}
	public void remove(Group group){
		Iterator<ContactGroupRelation> it = contactGroupRelations_.iterator();
		ArrayList<Integer> arrayToBeDeleted = new ArrayList<Integer>();
		int groupKey = group.getKey();
		while(it.hasNext()){
			ContactGroupRelation tempCGR = it.next();
			if (groupKey == tempCGR.getContactKey()){
				if (groupKey == tempCGR.getGroupKey()){
					arrayToBeDeleted.add((Integer)tempCGR.getKey());
				}
			}
		}
		
		Iterator<Integer> itt = arrayToBeDeleted.iterator();
		while(itt.hasNext()){
			Integer i = itt.next();
			remove(findByKey(i));
		}
	}
	



	public void saveAllIntoJsonFile() {
		String fileName = FileManager.getFileName(FileManager.fileName_CGR);
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
