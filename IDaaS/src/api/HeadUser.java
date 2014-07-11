package api;

import utlities.utilities;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

import db.Databaseio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



@Path("/head")
public class HeadUser {


	public static String username;
	protected static String password;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getVersion()
	{
		return Response.status(400).entity("Missing functionality. Try using '/head/auth' in a POST request with appropriate parameters").build();
	}

	@Path("/auth")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response head_authenticate(String body)
	{
		System.out.println(body);
		String token=null;
		boolean authenticated = false;
		int householdID=0;
		JSONArray json = new JSONArray();
		JSONArray head = new JSONArray();
		JSONArray subuser = new JSONArray();
		JSONObject headObj = new JSONObject();

		JSONObject temp1 = new JSONObject();
		JSONObject temp2 = new JSONObject();
		HashMap<String, String> map= new HashMap<String,String>();
		utilities utility = new utilities();


		try{
			map = utility.parseData(body);
			username = map.get("username");
			password = map.get("password");
			if(username == null || password == null)
			{
				temp1 = utility.buildErrorObject("Username or Password attributes not present", 400, "Invalid Parameters");
				return Response.status(400).entity(temp1).build();
			}
		}  
		catch(Exception e)
		{
			System.out.println("Error parsing url encoded data");
			e.printStackTrace();

			temp1 = utility.buildErrorObject("Username or Password attributes not present", 400, "Invalid Parameters");
			return Response.status(400).entity(temp1).build();

		}
		//check creds
		authenticated = utility.authenticate(username, password,"head");
		if(!authenticated)
		{
			temp1 = utility.buildErrorObject("Invalid Username or Password", 401, "Authentication Failed");

			return Response.status(401).entity(temp1).build();

		}
		
		System.out.println("username is" +  username + " password is " + password);

		//Genrate access token
		token = utility.generateToken(username);

		//Get householdID corresponding to the user

		Databaseio dbio = new Databaseio();
		Connection con = dbio.getConnection();
		try{
			Statement stmt = con.createStatement();
			String query = "select * from household where DefaultUser='" + username + "';";
			ResultSet rSet = stmt.executeQuery(query);
			rSet.next();
			householdID  = 	rSet.getInt("householdID");

			//insert token into database


			headObj.put("householdID", householdID);
			headObj.put("defaultUser", rSet.getString("DefaultUser"));
			headObj.put("operatorID", rSet.getInt("operatorID"));
			headObj.put("subscriptionTier", rSet.getString("subscription_tier"));
			headObj.put("status" , rSet.getString("status"));
			headObj.put("maxDevices" ,rSet.getString("max_devices"));
			headObj.put("creationDate",rSet.getString("creation_date"));
			headObj.put("lastUpdate", rSet.getString("last_update"));
			headObj.put("accessToken", token);
			headObj.put("profPic", rSet.getString("prof_pic"));

			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			Calendar cal = Calendar.getInstance();
			cal.setTime(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, 60);


			//------
			query = "insert into session(householdID,authtoken,token_expiry) values(" + householdID + ",'" + token + "','" + format.format(cal.getTime()) + "');";

			int result = stmt.executeUpdate(query);	
			if(result < 1 )
			{
				System.out.println("Error inserting token in the Database");
				return Response.status(500).entity("Error settting up the Session. Please contact admin").build();

			}
			
			//Obtain Session ID
			
			query = "select sessionid from session where authtoken='" + token + "';";

			rSet = stmt.executeQuery(query);
			rSet.next();
			headObj.put("sessionID", rSet.getString("sessionid"));
			temp1.put("head",headObj);
			rSet.close();
			con.close();
		}	
		catch(Exception e)
		{
			System.out.println("Error fetching householdID or session ID");
			e.printStackTrace();
		}


		//Get sub user details
		con = dbio.getConnection();
		try{
			Statement stmt = con.createStatement();

			String query = "select userid,prof_pic,name from user where householdID = " + Integer.toString(householdID) + ";";

			ResultSet rSet = stmt.executeQuery(query);
			while (rSet.next()) {

				//add subuser objects
				JSONObject subuserObj = new JSONObject();

				subuserObj.put("profileAvatar", rSet.getString("prof_pic"));
				subuserObj.put("userID", rSet.getString("userid"));
				subuserObj.put("name", rSet.getString("name"));

				subuser.put(subuserObj);


			}
			
			
			
			
			
			temp1.put("subusers", subuser);
			temp1.put("status","OK");
			json.put(temp1);
			rSet.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Response.status(200).entity(json).build();
	}
}
