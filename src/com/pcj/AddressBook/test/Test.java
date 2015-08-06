package com.pcj.AddressBook.test;


import java.util.ArrayList;

import com.pcj.AddresssBook.lib.Contact;
import com.pcj.AddresssBook.lib.ContactGroupRelation;
import com.pcj.AddresssBook.lib.ContactGroupRelationManager;
import com.pcj.AddresssBook.lib.ContactManager;
import com.pcj.AddresssBook.lib.Group;
import com.pcj.AddresssBook.lib.GroupManager;

public class Test {
	ContactManager cm_ = new ContactManager();
	GroupManager gm_ = new GroupManager();
	ContactGroupRelationManager cgrm_ = new ContactGroupRelationManager(cm_,gm_);
	
	public void makeData(ContactManager cm, GroupManager gm, ContactGroupRelationManager cgrm){
		gm.add(new Group("Visitor"));
		gm.add(new Group("Customer"));
		cm.add(new Contact(
				"Vera Mccoy",
				Contact.Female,
				"886649065861",
				"1168 Najafabad Parkway",
				"vera@kabul.afghanistan",
				"example 1"
				));
		cgrm.add(new ContactGroupRelation(cm.getLatest().getKey(),1));
		cm.add(new Contact(
				"Mario Cheatham",
				Contact.Male,
				"406784385440",
				"1924 Shimonoseki Drive",
				"mario@batna.algeria",
				"example 2"
				));
		cgrm.add(new ContactGroupRelation(cm.getLatest().getKey(),1));
		cgrm.add(new ContactGroupRelation(cm.getLatest().getKey(),2));
		cm.add(new Contact(
				"Judy Gray",
				Contact.Female,
				"107137400143",
				"1031 Daugavpils Parkway",
				"judy@bchar@algeria",
				"example 3"
				));
		cgrm.add(new ContactGroupRelation(cm.getLatest().getKey(),2));
		cm.add(new Contact(
				"June Carroll",
				Contact.Female,
				"506134035434",
				"757 Rustenburg Avenue",
				"june@Skikda.algeria",
				"example 1"
				));
		cgrm.add(new ContactGroupRelation(cm.getLatest().getKey(),2));
		
		
	}
	public void retrieveData(){
		cm_.loadFromJsonFile("AddressBook0.JSON");
		cm_.add(new Contact(
				"Anthony Schwab",
				Contact.Male,
				"478229987054",
				"1892 Nabereznyje Telny Lane",
				"Anthony@Tafuna.samoa",
				"example 2",
				"Visitor"
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
