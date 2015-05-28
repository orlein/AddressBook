package com.mzen.pcj.AddresssBook.lib;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ContactManager{

	Gson gson = new Gson();
	ArrayList<Contact> contacts_;
	int latestId_;
	//����ó List
	public enum Attributes{
		NAME,
		GENDER,
		PHONENUMBER,
		ADDRESS,
		EMAIL,
		MEMO
	};
	public ContactManager(){
		latestId_ = 0;
		contacts_ = new ArrayList<Contact>();
	}
	
	public ArrayList<Contact> getList(){
		return contacts_;
	}
	public int getLatestId(){
		return contacts_.get(contacts_.size()-1).getId();
	}
	public void setLatestId(int latestId){
		latestId_ = latestId;
	}
	public void add(Contact contact) {
		//id++;
		//contact.setId(id);
		contacts_.add(contact); 
	}
	public Contact getLatest(){
		return contacts_.get(contacts_.size()-1);
	}
	public Contact[] makeIntoArray(){
		Iterator<Contact> it = contacts_.iterator();
		Contact[] result = new Contact[contacts_.size()];
		int i=0;
		while(it.hasNext()){
			result[i] = it.next();
			i++;
		}
		return result;
	}

	public ArrayList<Contact> find(String query, Contact.Attributes att){
		Iterator<Contact> it = contacts_.iterator();
		Contact temp;
		ArrayList<Contact> result = new ArrayList<Contact>();
		while(it.hasNext()){
			temp = it.next();
			switch(att){
			case NAME:
				if (temp.getName().contains(query)){
					FileManager.logOutput("Found name:"+ query);
					result.add(temp);
				}
				break;
			case GENDER:
				if (temp.getGender().contains(query)){
					FileManager.logOutput("Found gender: "+query);
					result.add(temp);
				}
				break;
			case PHONENUMBER:
				if (temp.getPhoneNumber().contains(query)){
					FileManager.logOutput("Found phonenumber: "+ query);
					result.add(temp);
				}
				break;
			case ADDRESS:
				if (temp.getAddress().contains(query)){
					FileManager.logOutput("Found address: "+query);
					result.add(temp);
				}
				break;
			case EMAIL:
				if (temp.getEmail().contains(query)){
					FileManager.logOutput("Found email: "+query);
					result.add(temp);
				}
				break;
			case MEMO:
				FileManager.logOutput("cannot find by memo");
				return null;
			default:
				FileManager.logOutput("cannot find the query attribute");
				return null;
			}
		}
		return result;
	}
	public Contact findByKey(int id){
		Iterator<Contact> it = contacts_.iterator();
		Contact result;
		while(it.hasNext()){
			result = it.next();
			if(result.getId() == id){
				return result;
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
	public void saveAllIntoJsonFile(){	
		String fileName = FileManager.makeLatestFileName(FileManager.fileName_AB);
		FileManager.saveStringIntoFile(gson.toJson(contacts_),fileName);
	}
	public void saveAllIntoJsonFile(String fileName){	
		
		FileManager.saveStringIntoFile(gson.toJson(contacts_),fileName+".JSON");
	}
	
	public void loadFromJsonFile(String fileName){
		retrieveDataFromJson(FileManager.loadStringFromFile(fileName));
	}
	public void retrieveDataFromJson(String JSON){
		ArrayList<Contact> result = new ArrayList<Contact>();
		if (JSON != "null"){
			result = gson.fromJson(JSON, new TypeToken<ArrayList<Contact>>(){}.getType());
			Iterator<Contact> it = result.iterator();
			while(it.hasNext()){
				add(it.next());
			}
		}else{
			result = null;
		}
		
	}
	
	

}
