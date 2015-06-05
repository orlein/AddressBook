package com.mzen.pcj.AddressBook.lib;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ContactGroupRelationManager {
	int latestId_;
	
	ContactManager cm_;
	GroupManager gm_;
	
	Gson gson = new Gson();
	ArrayList<ContactGroupRelation> contactGroupRelations_;
	
	public ContactGroupRelationManager(ContactManager cm, GroupManager gm){
		cm_ = cm;
		gm_ = gm;
		contactGroupRelations_ = new ArrayList<ContactGroupRelation>();
		//id_ = 0;
	}
	public int getLatestId(){
		return contactGroupRelations_.get(contactGroupRelations_.size()-1).getId();
	}
	public void setLatestId(int latestId){
		latestId_ = latestId;
	}
	public void add(ContactGroupRelation cgr){
		//id_++;
		//cgr.setId(id_);;
		contactGroupRelations_.add(cgr);
	}
	public ContactGroupRelation[] makeIntoArray(){
		Iterator<ContactGroupRelation> it = contactGroupRelations_.iterator();
		ContactGroupRelation[] result = new ContactGroupRelation[contactGroupRelations_.size()];
		int i=0;
		while(it.hasNext()){
			result[i] = it.next();
			i++;
		}
		return result;
	}
	
	public ArrayList<Contact> findByGroupId(int id){
		ArrayList<Contact> tempContacts = new ArrayList<Contact>();
		
		Iterator<ContactGroupRelation> it = contactGroupRelations_.iterator();
		while(it.hasNext()){
			ContactGroupRelation tempCGR = it.next();
			if (tempCGR.getGroupId() == id){
				tempContacts.add(cm_.findByKey(tempCGR.getContactId()));
			}
		}
		
		return tempContacts;
		
	}
	public ArrayList<Group> findByContactId(int id){
		ArrayList<Group> tempGroups = new ArrayList<Group>();
		
		Iterator<ContactGroupRelation> it = contactGroupRelations_.iterator();
		while(it.hasNext()){
			ContactGroupRelation tempCGR = it.next();
			if(tempCGR.getContactId() == id){
				tempGroups.add(gm_.findById(tempCGR.getGroupId()));
			}
		}
		
		return tempGroups; 
	}
	public ContactGroupRelation findById(int id){
		Iterator<ContactGroupRelation> it = contactGroupRelations_.iterator();
		ContactGroupRelation result;
		while(it.hasNext()){
			result = it.next();
			if(result.getId() == id){
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
		int contactId = contact.getId();
		while(it.hasNext()){
			ContactGroupRelation tempCGR = it.next();
			if (contactId == tempCGR.getContactId()){
				arrayToBeDeleted.add((Integer)tempCGR.getId());
			}
		}
		
		Iterator<Integer> itt = arrayToBeDeleted.iterator();
		while(itt.hasNext()){
			Integer i = itt.next();
			remove(findById(i));
		}
		
	}
	public void remove(Group group){
		Iterator<ContactGroupRelation> it = contactGroupRelations_.iterator();
		ArrayList<Integer> arrayToBeDeleted = new ArrayList<Integer>();
		int groupId = group.getId();
		while(it.hasNext()){
			ContactGroupRelation tempCGR = it.next();
			if (groupId == tempCGR.getContactId()){
				if (groupId == tempCGR.getGroupId()){
					arrayToBeDeleted.add((Integer)tempCGR.getId());
				}
			}
		}
		
		Iterator<Integer> itt = arrayToBeDeleted.iterator();
		while(itt.hasNext()){
			Integer i = itt.next();
			remove(findById(i));
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
