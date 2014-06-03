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

@Path("/user")
public class Subuser {

	private static String username;
	private static String password;
	private static String token;
	private static String sessionid;
	private static String parentid;

	@Path("/auth")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response subuser_authenticate(String body)
	{
		
		HashMap<String, String> map= new HashMap<String,String>();
		utilities utility = new utilities();
		JSONArray json = new JSONArray();
		JSONObject temp =  new JSONObject();
		try
		{
			map = utility.parseData(body);
			username = map.get("username");
			password = map.get("password");
			token = map.get("token");
			sessionid = map.get("sessionid");
			parentid = map.get("parent");

			System.out.print(parentid);
			//check for null entries
			if(username == null || password == null || token == null || sessionid == null || parentid == null)
			{
				temp = utility.buildErrorObject("Appropriate parameters not present in the request", 400, "Invalid Parameters");
				return Response.status(400).entity(temp).build();
			}
			//check token validity
			if(!utility.checkTokenValidity(token,parentid,sessionid))
			{
				temp = utility.buildErrorObject("Either the token has expired or is an invalid token or incorrect session", 401, "Invalid token or session");
				return Response.status(401).entity(temp).build();

			}

			//authenticate
			if(!utility.authenticate(username,password, "subuser"))
			{
				temp = utility.buildErrorObject("Invalid Username or Password", 401, "Authentication Failed");

				return Response.status(401).entity(temp).build();

			}

			//return Error response

			JSONArray resp = utility.generateSubuserJSONResponse(username);
			if(resp == null)
			{
				temp = utility.buildErrorObject("Invalid Username or Password", 500, " Internal Server Error");
				JSONObject error = utility.buildErrorObject("Something went wrong with the Server. Cannot fetch user information at this time. Please try again later", 500, "Internal Server Error");
				return Response.status(500).entity(error).build();
			}

			//return appropriate response
			return Response.status(200).entity(resp).build();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			temp = utility.buildErrorObject("Appropriate parameters not present in the request", 400, "Invalid Parameters");
			return Response.status(400).entity(temp).build();
		}

	}




}
