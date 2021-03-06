package com.pcj.AddresssBook.lib;



public class Contact {

	private int key_;
	private String name_;
	private String gender_; //only M and F
		public static final String Male = "M";
		public static final String Female = "F";
	private String phoneNumber_;
	private String address_;
	private String email_;
	private String memo_;
	
	public int getKey(){
		return key_;
	}
	public String getName(){
		return name_;
	}
	public String getGender() {
		return gender_;
	}
	public String getPhoneNumber(){
		return phoneNumber_;
	}
	public String getAddress(){
		return address_;
	}
	public String getEmail(){
		return email_;
	}
	public String getMemo(){
		return memo_;
	}
	
	
	
	public void setName(String name){
		name_ = name;
	}
	public void setGender(String gender_) {		
		this.gender_ = gender_;
	}
	public void setPhoneNumber(String phoneNumber){
		phoneNumber_ =phoneNumber; 
	}
	public void setAddress(String address){
		address_ = address; 
	}
	public void setEmail(String email){
		email_ = email;
	}
	public void setMemo(String memo){
		memo_ = memo;
	}
	
	public void setKey(int key){
		key_ = key;
	}
	
	
	
	
	public Contact(String name, String gender, String phoneNumber, String address,
			String email, String memo){
		setName(name);
		setGender(gender);
		setPhoneNumber(phoneNumber);
		setAddress(address);
		setEmail(email);
		setMemo(memo);
	}
	public Contact (String[] array){
		if (array.length == 6){
			setName(array[0]);
			setGender(array[1]);
			setPhoneNumber(array[2]);
			setAddress(array[3]);
			setEmail(array[4]);
			setMemo(array[5]);
		}
	}
	
	
	public Contact(String name, String gender, String phoneNumber, String address,
			String email, String memo, String groupName){
		setName(name);
		setGender(gender);
		setPhoneNumber(phoneNumber);
		setAddress(address);
		setEmail(email);
		setMemo(memo);
		
	}
	
	public boolean isContaining(String something){
		if (something == name_ ||
				something == gender_ ||
				something == phoneNumber_ ||
				something == address_ ||
				something == email_)
		{
			return true;
		}else{
			return false;
		}
	}
}
