package com.mzen.pcj.AddresssBook.lib;

public class Group {
	int key_;
	String name_;
	public int getKey() {
		return key_;
	}
	public String getName() {
		return name_;
	}
	
	public void setKey(int key) {
		key_ = key;
	}
	
	public void setName(String name) {
		name_ = name;
	}
	
	public Group(String name){
		setName(name);
	}
}
