package com.mzen.pcj.AddressBook.lib;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GroupManager{

	
	
	ArrayList<Group> groups_;
	Gson gson = new Gson();
	int latestId_;
	
	
	public GroupManager(){
		//id_ = 0;
		groups_ = new ArrayList<Group>();
	}
	public int getLatestId(){
		return groups_.get(groups_.size()-1).getId();
	}
	public void setLatestId(int latestId){
		latestId_ = latestId;
	}
	
	public void add(Group group){
		//id_++;
		//group.setKey(id_);
		groups_.add(group);
	}
	public Group[] makeIntoArray(){
		Iterator<Group> it = groups_.iterator();
		Group[] result = new Group[groups_.size()];
		int i=0;
		while(it.hasNext()){
			result[i] = it.next();
			i++;
		}
		return result;
	}
	
	public ArrayList<Group> find(String query){
		Iterator<Group> it = groups_.iterator();
		ArrayList<Group> result = new ArrayList<Group>();
		Group temp;
		while(it.hasNext()){
			temp = it.next();
			FileManager.logOutput("Finding: "+temp.getName());
			if (temp.getName().contains(query)){
				FileManager.logOutput("Found: "+temp.getName());
				result.add(temp);
			}
		}
		return result;
	}
	public Group findById(int id){
		Iterator<Group> it = groups_.iterator();
		Group result;
		while(it.hasNext()){
			result = it.next();
			if(result.getId() == id){
				return result;
			}
		}
		return null;
	}
	
	public boolean remove(Group group){
		
		if(groups_.remove(group)){
			return true;
		}else{
			return false;
		}
	}
	
	public ArrayList<Group> getList(){
		return groups_;
	}


	public void saveAllIntoJsonFile() {
		String fileName = FileManager.getFileName(FileManager.fileName_G);
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
