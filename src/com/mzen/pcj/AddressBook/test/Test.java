package com.mzen.pcj.AddressBook.test;


import java.util.ArrayList;

import com.mzen.pcj.AddressBook.lib.Contact;
import com.mzen.pcj.AddressBook.lib.ContactGroupRelation;
import com.mzen.pcj.AddressBook.lib.ContactGroupRelationManager;
import com.mzen.pcj.AddressBook.lib.ContactManager;
import com.mzen.pcj.AddressBook.lib.Group;
import com.mzen.pcj.AddressBook.lib.GroupManager;

public class Test {
	ContactManager cm_ = new ContactManager();
	GroupManager gm_ = new GroupManager();
	ContactGroupRelationManager cgrm_ = new ContactGroupRelationManager(cm_,gm_);
	
	public void makeData(ContactManager cm, GroupManager gm, ContactGroupRelationManager cgrm){
		gm.add(new Group(gm_.getLatestId()+1, "Visitor"));
		gm.add(new Group(gm_.getLatestId()+1,"Customer"));
		cm.add(new Contact(cm_.getLatestId()+1,
				"Vera Mccoy",
				Contact.Female,
				"886649065861",
				"1168 Najafabad Parkway",
				"vera@kabul.afghanistan",
				"example 1"
				));
		cgrm.add(new ContactGroupRelation(cgrm_.getLatestId()+1,cm.getLatest().getId(),1));
		cm.add(new Contact(cm_.getLatestId()+1,
				"Mario Cheatham",
				Contact.Male,
				"406784385440",
				"1924 Shimonoseki Drive",
				"mario@batna.algeria",
				"example 2"
				));
		cgrm.add(new ContactGroupRelation(cgrm_.getLatestId()+1,cm.getLatest().getId(),1));
		cgrm.add(new ContactGroupRelation(cgrm_.getLatestId()+1,cm.getLatest().getId(),2));
		cm.add(new Contact(cm_.getLatestId()+1,
				"Judy Gray",
				Contact.Female,
				"107137400143",
				"1031 Daugavpils Parkway",
				"judy@bchar@algeria",
				"example 3"
				));
		cgrm.add(new ContactGroupRelation(cgrm_.getLatestId()+1,cm.getLatest().getId(),2));
		cm.add(new Contact(cm_.getLatestId()+1,
				"June Carroll",
				Contact.Female,
				"506134035434",
				"757 Rustenburg Avenue",
				"june@Skikda.algeria",
				"example 1"
				));
		cgrm.add(new ContactGroupRelation(cgrm_.getLatestId()+1,cm.getLatest().getId(),2));
		
		
	}
	public void retrieveData(){
		cm_.loadFromJsonFile("AddressBook0.JSON");
		cm_.add(new Contact(cm_.getLatestId()+1,
				"Anthony Schwab",
				Contact.Male,
				"478229987054",
				"1892 Nabereznyje Telny Lane",
				"Anthony@Tafuna.samoa",
				"example 2"
				));
		cm_.saveAllIntoJsonFile();
	}
	public ArrayList<Contact> getListFromCM(){
		return cm_.getList();
	}
	public ContactManager getCm(){
		return cm_;
	}
}
