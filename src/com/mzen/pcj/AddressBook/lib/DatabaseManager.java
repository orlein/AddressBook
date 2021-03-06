package com.mzen.pcj.AddressBook.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseManager {
	static String myDriver = "org.gjt.mm.mysql.Driver";
	static String myUrl = "jdbc:mysql://210.115.36.129:3306/";
	static Connection conn;
	
	static String databaseName = "pcj_test";
	public static void makeTable(){
		try{
		Statement st = conn.createStatement();
		
		st.executeQuery("USE " + databaseName);
		 st.executeUpdate("create table contact("+
					"id int unsigned not null,"+
				    "PRIMARY KEY (id),"+
				    "name varchar(20) not null,"+
				    "gender char(1) not null,"+
				    "phoneNumber varchar(15) not null,"+
				    "address varchar(30) not null,"+
				    "email varchar(30) not null,"+
				    "memo  varchar(255) not null);"
				    );
			st.executeUpdate("CREATE TABLE grouptable("+
	    "id int unsigned not null,"+
		"PRIMARY KEY (id),"+
	    "name varchar(20) not null);"
	    );
			st.executeUpdate("CREATE TABLE ContactGroupRelation("+
	    "id int unsigned not null,"+
		"PRIMARY KEY (id),"+
	    "contactID int unsigned not null,"+
		"groupID int unsigned not null,"+
	    "FOREIGN KEY(contactID) references contact(id),"+
		"FOREIGN KEY(groupID) references grouptable(id));"
	    );
		 
		st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void init(String userName, String password){
		try {
			Class.forName(myDriver);
			conn = DriverManager.getConnection(myUrl, userName, password);
			Statement st = conn.createStatement();
			st.executeQuery("USE "+databaseName);
			st.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	public static ContactManager loadContactFromDB(){
		ContactManager cm = new ContactManager();
		try{
			Statement st = conn.createStatement();
		    String query;
		    ResultSet rs;
		    query = "SELECT * FROM contact"; 
		    rs = st.executeQuery(query);
		    while (rs.next()){
		        int id_ = rs.getInt("id");
		        String name_ = rs.getString("name");
		        String gender_ = rs.getString("gender");
		        String phoneNumber_ = rs.getString("phoneNumber");
		        String address_ = rs.getString("address");
		        String email_ = rs.getString("email");
		        String memo_ = rs.getString("memo");
		        
		        Contact contact = new Contact(id_, name_, gender_, phoneNumber_, address_, email_, memo_);
		        cm.add(contact);
		        
		        // print the results
		        //System.out.format("%s, %s, %s, %s, %s, %s\n", id_, name_, gender_, phoneNumber_, address_, email_, memo_);
		    }
		    st.close();
	    }
	    catch (Exception e){
	    	System.err.println("Got an exception! ");
	    	System.err.println(e.getMessage());
	    }
		return cm;
	}
	public static GroupManager loadGroupFromDB(){
		GroupManager gm = new GroupManager();
		try{
			Statement st = conn.createStatement();
		    String query; 
		    ResultSet rs;
		    query = "SELECT * FROM grouptable"; 
		    rs = st.executeQuery(query);
		    while(rs.next()){
		    	int id_ = rs.getInt("id");
		    	String name_ = rs.getString("name");
		    	//System.out.format("%s, %s\n",id_,name_);
		    	Group group = new Group(id_, name_);
		    	gm.add(group);
		    }
		    st.close();
		}catch(Exception e){
				System.err.println("Got an exception! ");
				System.err.println(e.getMessage());
		}
		return gm;
	}
	public static ContactGroupRelationManager loadCGRFromDB(ContactManager cm, GroupManager gm){
		ContactGroupRelationManager cgrm = new ContactGroupRelationManager(cm,gm);
		try{
			Statement st = conn.createStatement();
			String query;
			ResultSet rs;
			query = "SELECT * FROM ContactGroupRelation";
			rs = st.executeQuery(query);
			while(rs.next()){
				int id_ = rs.getInt("id");
				int contactID_ = rs.getInt("contactID");
				int groupID_ = rs.getInt("groupID");
				ContactGroupRelation cgr = new ContactGroupRelation(id_, contactID_,groupID_);
		    	//System.out.format("%s, %s, %s\n",id_,contactID_,groupID_);
				cgrm.add(cgr);
			}
			st.close();
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return cgrm;
	}
	
	public static void saveContactIntoDB(Contact contact){
		try{
			Statement st = conn.createStatement();
			String query;
			int id_ = contact.getId();
			String name_ = contact.getName();
			String gender_ = contact.getGender();
			String phoneNumber_ = contact.getPhoneNumber();
			String address_ = contact.getAddress();
			String email_ = contact.getEmail();
			String memo_ = contact.getMemo();
			query = "INSERT INTO contact (id, name, gender, phoneNumber,address, email, memo) values ("+
			id_ + "," +
			"\"" +name_ + "\"," +
			"\"" +gender_ + "\"," +
			"\"" +phoneNumber_ + "\"," +
			"\"" +address_ + "\"," +
			"\"" +email_ + "\"," +
			"\"" +memo_ + "\");";
			FileManager.logOutput(query);
			st.executeUpdate(query);
			st.close();
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	public static void saveContactIntoDB(ContactManager cm){
		try{
			Contact[] contacts = cm.makeIntoArray();
			
			for(int i=0; i<contacts.length; i++){
				saveContactIntoDB(contacts[i]);
			}
			//
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	
	public static void saveGroupIntoDB(Group group){
		try{
			Statement st = conn.createStatement();
			String query;
			//st.executeUpdate("delete grouptable;");
		
			int id_ = group.getId();
			String name_ = group.getName();
			query = "INSERT INTO grouptable (id, name) values ("+
			id_ + "," +
			"\"" + name_ + "\");";
			FileManager.logOutput(query);
			st.executeUpdate(query);
			st.close();
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	public static void saveGroupIntoDB(GroupManager gm){
		try{
			Group[] groups = gm.makeIntoArray();
			for(int i=0; i<groups.length; i++){
				saveGroupIntoDB(groups[i]);
			}
			
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	
	public static void saveCGRIntoDB(ContactGroupRelation cgr){
		try{
			Statement st = conn.createStatement();
			String query;
			//st.executeUpdate("delete contactgrouprelation;");
			int id_ = cgr.getId();
			int contactId_ = cgr.getContactId();
			int groupId_ = cgr.getGroupId();
			query = "INSERT INTO ContactGroupRelation (id, contactID, groupID) values ("+
			id_ + "," +
			contactId_ + ","+
			groupId_ + ");";
			FileManager.logOutput(query);
			st.executeUpdate(query);
			st.close();
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	public static void saveCGRIntoDB(ContactGroupRelationManager cgrm){
		try{
			ContactGroupRelation[] cgrs = cgrm.makeIntoArray();
			for(int i=0; i<cgrs.length; i++){
				saveCGRIntoDB(cgrs[i]);
			}
		}catch(Exception e){
			
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	
	public static void removeContactFromDB(Contact contact){
		try{
			Statement st = conn.createStatement();
			String query;
			query = "DELETE FROM ContactGroupRelation where contactID = " + contact.getId();
			st.executeUpdate(query);
			FileManager.logOutput(query);
			query = "DELETE FROM contact where ID = " + contact.getId();
			st.executeUpdate(query);
			FileManager.logOutput(query);
			st.close();
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	public static void removeGroupFromDB(Group group){
		try{
			Statement st = conn.createStatement();
			String query;
			query = "DELETE FROM ContactGroupRelation where groupID = " + group.getId();
			st.executeUpdate(query);
			FileManager.logOutput(query);
			query = "DELETE FROM grouptable where ID = " + group.getId();
			st.executeUpdate(query);
			FileManager.logOutput(query);
			st.close();
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	
	public static void editContactFromDB(Contact contact, Contact.Attributes att){
		try{
			Statement st = conn.createStatement();
			String query;
			switch(att){
			case NAME:
				query = "UPDATE contact SET name = \""+ contact.getName() +"\" where id = " + contact.getId()+";";
				break;				
			case GENDER:
				query = "UPDATE contact SET gender = \""+ contact.getGender() +"\" where id = " + contact.getId()+";";
				break;
			case PHONENUMBER:
				query = "UPDATE contact SET phonenumber = \""+ contact.getPhoneNumber() +"\" where id = " + contact.getId()+";";
				break;
			case ADDRESS:
				query = "UPDATE contact SET address = \""+ contact.getAddress() +"\" where id = " + contact.getId()+";";
				break;
			case EMAIL:
				query = "UPDATE contact SET email = \""+ contact.getEmail() +"\" where id = " + contact.getId()+";";
				break;
			case MEMO:
				query = "UPDATE contact SET memo = \""+ contact.getMemo() +"\" where id = " + contact.getId()+";";
				break;
			default:
				query = "";
				break;
			}
			st.executeUpdate(query);
			FileManager.logOutput(query);
			st.close();
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	public static void editGroupFromDB(Group group){
		try{
			Statement st = conn.createStatement();
			String query;
			query = "UPDATE grouptable SET name = \""+group.getName()+"\"WHERE id = "+group.getId();
			st.executeUpdate(query);
			st.close();
			FileManager.logOutput(query);
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	public static void editCGRFromDB(ContactGroupRelation cgr){
		try{
			Statement st = conn.createStatement();
			String query;
			query = "UPDATE ContactGroupRelation SET contactID = "+cgr.getContactId()+"AND groupID = "+cgr.getGroupId()
					+"WHERE id = "+cgr.getId();
			st.executeUpdate(query);
			st.close();
			FileManager.logOutput(query);
			
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}                                                          
	}
	
	public static void endConn(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
