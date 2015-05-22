package com.mzen.pcj.AddresssBook.lib;

import java.util.ArrayList;
import java.util.Iterator;

public class ContactGroupRelationManager {
	ContactManager cm_;
	GroupManager gm_;
	
	ArrayList<ContactGroupRelation> contactGroupRelations_;
	
	public ContactGroupRelationManager(ContactManager cm, GroupManager gm){
		cm_ = cm;
		gm_ = gm;
		contactGroupRelations_ = new ArrayList<ContactGroupRelation>();
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

		
	}
}
