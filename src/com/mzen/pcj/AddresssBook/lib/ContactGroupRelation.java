package com.mzen.pcj.AddresssBook.lib;

public class ContactGroupRelation {
	int contactKey_;
	int groupKey_;


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
	
	public ContactGroupRelation(int contactKey, int groupKey){
		setContactKey(contactKey);
		setGroupKey(groupKey);
	}
	
}
