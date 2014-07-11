
package utlities;

import java.net.URLDecoder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;


import db.Databaseio;

public class utilities {


	String username;
	String password;
	String token;


	public class toJSON {

		public JSONArray toJSONArray(ResultSet rs) throws Exception{

			JSONObject obj = new JSONObject();
			JSONArray json = new JSONArray();
			return json;
		}

	}

	public String generateToken(String username)
	{
		// Get username from mysql

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//get current date time with Date()
		Date date = new Date();

		System.out.println(dateFormat.format(date));

		String key = UUID.randomUUID().toString().toUpperCase() + 
				"|" + "blendedservices" +
				"|" + username + 
				"|" + dateFormat.format(date);
		System.out.println("key = " + key + "date=" + dateFormat.format(date));
		StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();

		jasypt.setAlgorithm("PBEWithMD5AndTripleDES");
		jasypt.setPassword("CiscoCTAO");
		String authenticationToken = jasypt.encrypt(key);
		System.out.println("Authentication token encrypted is" + authenticationToken);

		System.out.println("Authenctication oken decrypted is" + jasypt.decrypt(authenticationToken));
		/*

		String dateStart = "01/14/2012 09:29:58";
		String dateStop = "01/14/2012 10:31:48";

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
		d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);


			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);

			Date now = new Date();
			//DateTime now = new DateTime(format.parse(DateTime.now().toString()));

			System.out.println(format.format(now));

			System.out.print(Days.daysBetween(dt2, dt1).getDays() + " days, ");
			System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
			System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
			System.out.print(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");

		} catch (Exception e) {
			e.printStackTrace();
		}
		 */

		return authenticationToken;
	}


	public HashMap<String,String> parseData(String requestbody) throws Exception
	{

		HashMap<String, String>  map = new HashMap<String, String>
		
		();
		String[] pairs = requestbody.split("\\&");

		for (int i=0; i<pairs.length; i++) {

			String[] fields = pairs[i].split("=");

			String name = URLDecoder.decode(fields[0], "UTF-8");

			if(!(name.equals("username") || name.equals("password") || name.equals("sessionid") || name.equals("token") || name.equals("parent")))
			{
				throw new Exception();
			}
			String value = URLDecoder.decode(fields[1], "UTF-8");
			if(name.equals("username"))
			{
				map.put("username",value);
			}
			else if(name.equals("password"))
			{
				map.put("password",value);
			}
			else if(name.equals("token"))
			{
				map.put("token",value);
			}
			else if(name.equals("sessionid"))
			{
				map.put("sessionid",value);
			}
			else if(name.equals("parent"))
			{
				map.put("parent",value);
			}



		}

		return map;
	}

	@SuppressWarnings("deprecation")
	public boolean authenticate(String username, String password,String loginas)
	{

		Databaseio dbio = new Databaseio();
		Connection con = dbio.getConnection();

		if(loginas.equals("head"))
		{
			try{
				Statement stmt = con.createStatement();
				ResultSet rSet = stmt.executeQuery("select DefaultUser,password,householdID from household");

				while (rSet.next()) {
					String existing_user=(rSet.getString("DefaultUser"));
					String existing_password=(rSet.getString("password"));
					if (username.equals(existing_user) && password.equals(existing_password)){
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
		else if(loginas.equals("subuser"))
		{
			try{

				//check if the auth token is valid iry
				String query;
				Statement stmt = con.createStatement();
				query = "select userid,password from user where userid='" + username + "' and password='" + password + "';"; 
				System.out.print(query);
				ResultSet rSet = stmt.executeQuery(query);
				rSet.last();

				//check if the number of records returned is 1
				if(rSet.getRow() != 1)
				{
					return false;

				}
				return true;	
			}
			catch(Exception e)
			{
				System.out.print("Error authenticating sub user");
				e.printStackTrace();
				return false;
			}


		}
		return false;
	}

	public JSONObject buildErrorObject(String message,int code, String type)
	{
		JSONObject error = new JSONObject();
		JSONObject finalError = new JSONObject();
		try
		{
			error.put("message", message);
			error.put("code", code);
			error.put("type", type);
			finalError.put("error", error);
		}
		catch(Exception e)
		{
			System.out.print("Error creating error object");
			e.printStackTrace();
			return null;

		}
		return finalError;

	}

	public boolean checkTokenValidity(String token, String parentid, String sessionid)
	{
		try{

			//check if the auth token is valid iry

			Databaseio dbio = new Databaseio();
			Connection con = dbio.getConnection();

			String query;
			Statement stmt = con.createStatement();
			query = "select token_expiry from session where sessionid=" + sessionid + " and authtoken='" + token + "' and householdID=(select householdID from household where DefaultUser='" + parentid + "');"; 
			System.out.print(query);
			ResultSet rSet = stmt.executeQuery(query);
			rSet.last();

			//check if the number of records returned is 1
			if(rSet.getRow() != 1)
			{
				return false;

			}

			//check the token expiry
			String token_expiry = (rSet.getString("token_expiry"));
			token_expiry = token_expiry.substring(0,token_expiry.length()-2);
			token_expiry=token_expiry.replace("-", "/");
			System.out.print(token_expiry);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//get current date time with Date()
			Date date = new Date();

			String now = dateFormat.format(date);

			Date d1 = null;
			Date d2 = null;

			try {

				d1 = dateFormat.parse(token_expiry);

				d2 = dateFormat.parse(now);


				System.out.print("token expiry = " + d1);
				System.out.print("current date = " + d2);

				DateTime expiry_token = new DateTime(d1);
				DateTime current_date = new DateTime(d2);


				//Return if token has already expired
				if(!expiry_token.isAfter(current_date))
				{

					return false;
				}
				return true;

			} catch (Exception e) {
				System.out.print("Error validating the token");
				e.printStackTrace();
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

	public JSONArray generateSubuserJSONResponse(String username)
	{
		Databaseio dbio = new Databaseio();
		Connection con = dbio.getConnection();
		String query;
		try
		{
			Statement stmt = con.createStatement();
			query = "select user.* , social.* from user,social where user.userid='" + username + "' and social.userid='" + username + "';"; 
			System.out.print(query);
			ResultSet rSet = stmt.executeQuery(query);
			rSet.last();

			//get user information

			String  prof_pic= rSet.getString("prof_pic");
			String email = rSet.getString("email");
			String householdID = rSet.getString("householdID");
			String userid = rSet.getString("userid");
			String name = rSet.getString("name");
			String gender = rSet.getString("gender");
			String birthday = rSet.getString("birthday");
			String current_location = rSet.getString("current_location");
			String creation_date = rSet.getString("creation_date");
			String last_update = rSet.getString("last_update");
			String parental_rating = rSet.getString("parental_rating");
			String pincode = rSet.getString("pincode");

			//get social account information
			String facebook_token = rSet.getString("facebook_token");
			String facebook_token_expiry = rSet.getString("facebook_token_expiry");
			String soundcloud_token = rSet.getString("soundcloud_token");
			//String soundcloud_token_expiry = rSet.getString("soundcloud_token_expiry");
			String youtube_token = rSet.getString("youtube_token");
			String youtube_token_expiry = rSet.getString("youtube_token_expiry");
			String dailymotion_token = rSet.getString("dailymotion_token");
			String dailymotion_token_expiry =  rSet.getString("dailymotion_token_expiry");

			JSONObject personal = new JSONObject();

			//construct JSON Response
			personal.put("profileAvatar", prof_pic);
			personal.put("email", email);
			personal.put("householdID", householdID);
			personal.put("loginName", userid);
			personal.put("gender", gender);
			personal.put("curentLocation", current_location);
			personal.put("Created", creation_date);
			personal.put("lastUpdate", last_update);
			personal.put("parentalRating", parental_rating);
			personal.put("pincode", pincode);

			JSONObject social = new JSONObject();

			JSONObject facebook = new JSONObject();

			facebook.put("accessToken", facebook_token!=null?facebook_token:"Not present");
			facebook.put("accessTokenExpiration", facebook_token_expiry!=null?facebook_token_expiry:"Not present");

			JSONObject dailymotion = new JSONObject();

			dailymotion.put("accessToken", dailymotion_token!=null?dailymotion_token:"Not present");
			dailymotion.put("accessTokenExpiration", dailymotion_token_expiry!=null?dailymotion_token_expiry:"Not present");

			JSONObject youtube = new JSONObject();

			youtube.put("accessToken", youtube_token!=null?youtube_token:"Not present");
			youtube.put("accessTokenExpiration", youtube_token_expiry!=null?youtube_token_expiry:"Not present");

			JSONObject soundcloud = new JSONObject();

			soundcloud.put("accessToken", soundcloud_token!=null?soundcloud_token:"Not present");
			soundcloud.put("accessTokenExpiration", soundcloud_token!=null?"Non expiring":"Not present");

			social.put("facebook", facebook);
			social.put("dailymotion", dailymotion);
			social.put("youtube", youtube);
			social.put("soundcloud", soundcloud);

			JSONObject temp1 = new JSONObject();
			JSONObject temp2 = new JSONObject();
			
			temp1.put("social", social);
			temp1.put("personal", personal);
			temp1.put("status","OK");
			JSONArray subuser = new JSONArray();
			subuser.put(temp1);



			con.close();

			return subuser;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			return null;

		}

	}

}
