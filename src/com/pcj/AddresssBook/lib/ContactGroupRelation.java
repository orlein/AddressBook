package com.pcj.AddresssBook.lib;

public class ContactGroupRelation {
	int key_;
	int contactKey_;
	int groupKey_;


	public int getKey(){
		return key_;
	}
	public int getContactKey() {
		return contactKey_;
	}
	public void setContactKey(int contactKey) {
		contactKey_ = contactKey;
	}
	public int getGroupKey() {
		return groupKey_;
	}
	public void setGroupKey(int groupKey) {
		groupKey_ = groupKey;
	}
	public void setKey(int key){
		key_ = key;
	}
	public ContactGroupRelation(int contactKey, int groupKey){
		setContactKey(contactKey);
		setGroupKey(groupKey);
	}
	
}
