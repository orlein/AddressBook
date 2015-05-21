package com.mzen.pcj.AddresssBook.lib;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ContactManager{

	Gson gson = new Gson();
	ArrayList<Contact> contacts_;
	int key;
	//ø¨∂Ù√≥ List
	public enum Attributes{
		NAME,
		GENDER,
		PHONENUMBER,
		ADDRESS,
		EMAIL,
		MEMO
	};
	public ContactManager(){
		key = 0;
		contacts_ = new ArrayList<Contact>();
	}
	
	public ArrayList<Contact> getList(){
		return contacts_;
	}

	public void add(Contact contact) {
		key++;
		contact.setKey(key);
		contacts_.add(contact); 
	}

	public Contact find(String query, Attributes att){
		Iterator<Contact> it = contacts_.iterator();
		Contact result;
		while(it.hasNext()){
			result = it.next();
			switch(att){
			case NAME:
				if (result.getName().contains(query)){
					return result;
				}
			case GENDER:
				if (result.getGender().contains(query)){
					return result;
				}
			case PHONENUMBER:
				if (result.getPhoneNumber().contains(query)){
					return result;
				}
			case ADDRESS:
				if (result.getAddress().contains(query)){
					return result;
				}
			case EMAIL:
				if (result.getEmail().contains(query)){
					return result;
				}
			case MEMO:
				//CANNOT FIND BY MEMO
				return null;
			default:
				return null;
			}
		}
		return null;
	}
	
	public Contact edit(Contact contact, Attributes att, String string){
		Contact result;
		result = contact;
		switch(att){
		case NAME:
			result.setName(string);
			return result;
		case GENDER:
			result.setGender(string);
			return result;
		case PHONENUMBER:
			result.setPhoneNumber(string);
			return result;
		case ADDRESS:
			result.setAddress(string);
			return result;
		case EMAIL:
			result.setEmail(string);
			return result;
		case MEMO:
			result.setMemo(string);
			return result;
		default:
			return null;
		}
		
	}
	
	public ArrayList<Contact> select(String something) {
		ArrayList<Contact> result_list = new ArrayList<Contact>();
		Iterator<Contact> it = contacts_.iterator();
		while(it.hasNext()){
			Contact temp = it.next();
			if (temp.isContaining(something)){
				result_list.add(temp);
			}
		}
		return result_list;
	}
	
	public boolean remove(Contact contact) {
		if (contacts_.remove(contact)){
			return true;
		}else
			return false;
	}
	public String toJsonString(){
		String result = gson.toJson(contacts_);
		return result;
	}
	public void saveAllContactsIntoJsonFile(){	
		String fileName = FileManager.makeLatestFileName();
		FileManager.saveStringIntoFile(gson.toJson(contacts_),fileName);
	}
	public void loadFromJsonFile(String fileName){
		retrieveContactFromJson(FileManager.loadStringFromFile(fileName));
	}
	public void retrieveContactFromJson(String JSON){
		ArrayList<Contact> result;
		if (JSON != "null"){
			result = gson.fromJson(JSON, new TypeToken<ArrayList<Contact>>(){}.getType());
			contacts_ = result;
		}else{
			result = null;
		}
		
	}
	
	

}
