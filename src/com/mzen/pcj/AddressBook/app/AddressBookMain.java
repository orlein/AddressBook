package com.mzen.pcj.AddressBook.app;
public class AddressBookMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserInterface ui = new UserInterface();
		Thread th = new Thread(ui);
		th.start();
	}
}
