package service;
import service.Databaseio;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserManagement {


	public boolean authenticate(String userid,String password)
	{
		Databaseio dbio = new Databaseio();
		Connection con = dbio.getConnection();
		try{
		Statement stmt = con.createStatement();
        ResultSet rSet = stmt.executeQuery("select DefaultUser,password,householdID from household");
        
        while (rSet.next()) {
        	String existing_user=(rSet.getString("DefaultUser"));
        	String existing_password=(rSet.getString("password"));
        	if (userid.equals(existing_user) && password.equals(existing_password)){
        		rSet.close();
        		con.close();
    			return true;
    		}
        	}
        rSet.close();
		con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return false;
		
		
	}
	
}
