package com.mzen.pcj.AddressBook.lib;

public class Group {
	int id_;
	String name_;
	public int getId() {
		return id_;
	}
	public String getName() {
		return name_;
	}
	
	public void setId(int id) {
		id_ = id;
	}
	
	public void setName(String name) {
		name_ = name;
	}
	
	public Group(int id, String name){
		setId(id);
		setName(name);
	}
	
}
