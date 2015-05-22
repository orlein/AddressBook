package com.mzen.pcj.AddresssBook.lib;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GroupManager{

	
	
	ArrayList<Group> groups_;
	Gson gson = new Gson();
	int key;
	
	
	public GroupManager(){
		key = 0;
		groups_ = new ArrayList<Group>();
	}
	
	
	public void add(Group group){
		key++;
		group.setKey(key);
		groups_.add(group);
	}
	
	
	public Group find(String groupName){
		Iterator<Group> it = groups_.iterator();
		Group temp;
		while(it.hasNext()){
			temp = it.next();
			if (temp.getName() == groupName){
				return temp;
			}
		}
		return null;
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
		String fileName = FileManager.makeLatestFileName(FileManager.fileName_G);
		FileManager.saveStringIntoFile(gson.toJson(groups_),fileName);
	}
	public void loadFromJsonFile(String fileName){
		retrieveDataFromJson(FileManager.loadStringFromFile(fileName));
	}
	public void retrieveDataFromJson(String JSON){
		ArrayList<Group> result = new ArrayList<Group>();
		if (JSON != "null"){
			result = gson.fromJson(JSON, new TypeToken<ArrayList<Group>>(){}.getType());
			Iterator<Group> it = result.iterator();
			while(it.hasNext()){
				add(it.next());
			}
		}else{
			result = null;
		}
		
	}
}
