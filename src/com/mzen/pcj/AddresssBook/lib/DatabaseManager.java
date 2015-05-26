package com.mzen.pcj.AddresssBook.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseManager {
	static String myDriver = "org.gjt.mm.mysql.Driver";
	static String myUrl = "jdbc:mysql://127.0.0.1:3306/";
	static Connection conn;
	public static void makeTable(){
		try{
		Statement st = conn.createStatement();
		st.executeQuery("create table contact("+
	"id int unsigned not null,"+
    "PRIMARY KEY (id),"+
    "name varchar(20) not null,"+
    "gender char(1) not null,"+
    "phoneNumber varchar(15) not null,"+
    "address varchar(30) not null,"+
    "email varchar(30) not null,"+
    "memo  varchar(255) not null);"
    );
		st.executeQuery("CREATE TABLE grouptable("+
    "id int unsigned not null,"+
	"PRIMARY KEY (id),"+
    "name varchar(20) not null);"
    );
		st.executeQuery("CREATE TABLE ContactGroupRelation("+
    "id int unsigned not null,"+
	"PRIMARY KEY (relationid),"+
    "contactID int unsigned not null,"+
	"groupID int unsigned not null,"+
    "FOREIGN KEY(contactID) references contact(id),"+
	"FOREIGN KEY(groupID) references grouptable(id));"
    );
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void init(String userName, String password){
		try {
			Class.forName(myDriver);
			conn = DriverManager.getConnection(myUrl, userName, password);
			
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
		    String query = "USE addressbook"; 
		    ResultSet rs = st.executeQuery(query);
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
		        
		        Contact contact = new Contact(name_, gender_, phoneNumber_, address_, email_, memo_);
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
		    String query = "USE addressbook"; 
		    ResultSet rs = st.executeQuery(query);
		    query = "SELECT * FROM grouptable"; 
		    rs = st.executeQuery(query);
		    while(rs.next()){
		    	int id_ = rs.getInt("id");
		    	String name_ = rs.getString("name");
		    	//System.out.format("%s, %s\n",id_,name_);
		    	Group group = new Group(name_);
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
			String query = "USE addressbook";
			ResultSet rs = st.executeQuery(query);
			query = "SELECT * FROM contactgrouprelation";
			rs = st.executeQuery(query);
			while(rs.next()){
				int id_ = rs.getInt("id");
				int contactID_ = rs.getInt("contactID");
				int groupID_ = rs.getInt("groupID");
				ContactGroupRelation cgr = new ContactGroupRelation(contactID_,groupID_);
		    	//System.out.format("%s, %s, %s\n",id_,contactID_,groupID_);
				cgrm.add(cgr);
			}
		}catch(Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return cgrm;
	}
	
	public static void saveContactIntoDB(Contact contact){
		try{
			Statement st = conn.createStatement();
			String query = "USE addressbook";
			st.executeQuery(query);
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
			String query = "USE addressbook";
			st.executeQuery(query);
			//st.executeUpdate("delete grouptable;");
		
			int id_ = group.getId();
			String name_ = group.getName();
			query = "INSERT INTO grouptable (id, name) values ("+
			id_ + "," +
			"\"" + name_ + "\");";
			FileManager.logOutput(query);
			st.executeUpdate(query);
			
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
			String query = "USE addressbook";
			st.executeQuery(query);
			//st.executeUpdate("delete contactgrouprelation;");
			int id_ = cgr.getId();
			int contactId_ = cgr.getContactId();
			int groupId_ = cgr.getGroupId();
			query = "INSERT INTO contactGroupRelation (id, contactID, groupID) values ("+
			id_ + "," +
			contactId_ + ","+
			groupId_ + ");";
			FileManager.logOutput(query);
			st.executeUpdate(query);
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
	
	
	public static void endConn(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
