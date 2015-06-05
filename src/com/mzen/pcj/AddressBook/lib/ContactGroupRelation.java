package com.mzen.pcj.AddressBook.lib;

public class ContactGroupRelation {
	int id_;
	int contactId_;
	int groupId_;


	public int getId(){
		return id_;
	}
	public int getContactId() {
		return contactId_;
	}
	public void setContactId(int contactId) {
		contactId_ = contactId;
	}
	public int getGroupId() {
		return groupId_;
	}
	public void setGroupId(int groupId) {
		groupId_ = groupId;
	}
	public void setId(int id){
		id_ = id;
	}
	public ContactGroupRelation(int id, int contactId, int groupId){
		setId(id);
		setContactId(contactId);
		setGroupId(groupId);
	}
	
}
