package service;

import java.sql.Connection;
import java.sql.DriverManager; 

public class Databaseio {
	
	public Connection getConnection()
	{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Loaded driver"); 
		}
		catch(Exception e){
			System.out.println("Failed to load the jdbc Driver");
			e.printStackTrace();
		}
		try{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/identity_information", "root", "koustubh");
		System.out.println("Connected to MySQL"); 
		return con;
		}
		catch(Exception e)
		{
			System.out.println("Connection unsuccesful to mysql");
			e.printStackTrace();
		}
		return null;
		
	}
	
	
}
