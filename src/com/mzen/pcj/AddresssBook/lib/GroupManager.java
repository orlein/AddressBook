package com.mzen.pcj.AddresssBook.lib;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;

public class GroupManager{

	
	
	ArrayList<Group> groups_;
	Gson gson = new Gson();
	
	public GroupManager(){
		
	}
	
	
	public void add(Group group){
		group.setKey(groups_.size());
		
	}
	
	
	public int find(String groupName){
		Iterator<Group> it = groups_.iterator();
		Group temp;
		while(it.hasNext()){
			temp = it.next();
			if (temp.getName() == groupName){
				return temp.getKey();
			}
		}
		return -1;
	}
	public Group findByKey(int key){
		Iterator<Group> it = groups_.iterator();
		Group result;
		while(it.hasNext()){
			result = it.next();
			if(result.getKey() == key){
				return result;
			}
		}
		return null;
	}
	
	public boolean remove(String groupName){
		
		if(groups_.contains(groupName)){
			groups_.remove(find(groupName));
			return true;
		}else{
			return false;
		}
	}
	
	public ArrayList<Group> getList(){
		return groups_;
	}


	public void saveAllIntoJsonFile() {
		String fileName = FileManager.makeLatestFileName();
		FileManager.saveStringIntoFile(gson.toJson(groups_),fileName);
	}
	public void retrieveDataFromJson(String JSON){
		
	}
}
