<<<<<<< HEAD:src/com/pcj/AddressBook/app/AddressBookMain.java
package com.pcj.AddressBook.app;





=======
package com.mzen.pcj.AddressBook.app;
>>>>>>> 6f02998ea1e9ee37004180f6504d819314a14994:src/com/mzen/pcj/AddressBook/app/AddressBookMain.java
public class AddressBookMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserInterface ui = new UserInterface();
		
		Thread th = new Thread(ui);
		th.start();
	}
}
