package com.mzen.pcj.AddresssBook.lib;

import java.util.ArrayList;

public class GroupManager{

	
	
	ArrayList<Group> groups_;	
	
	public GroupManager(){
		
	}
	
	
	public void add(Group group){
		group.setKey(groups_.size());
		
	}
	
	
	public int find(String groupName){
		if(groups_.contains(groupName)){
			return groups_.indexOf(groupName);
		}else{
			return -1;
		}
	}
	
	
	public boolean remove(String groupName){
		
		if(groups_.contains(groupName)){
			groups_.remove(find(groupName));
			return true;
		}else{
			return false;
		}
	}
	
	
	public void deleteDuplicates(){
	
	}
	
}
