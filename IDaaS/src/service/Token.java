package service;

import com.google.gdata.client.*;

import com.google.gdata.client.youtube.*;
import com.google.gdata.data.*;
import com.google.gdata.data.geo.impl.*;
//import com.google.gdata.data.media.*;

//import com.google.gdata.data.media.mediarss.*;
import com.google.gdata.client.http.*;
import com.google.gdata.data.youtube.*;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.io.File;
import java.net.URL;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Token 
{
	public String facebook_appID = "1509102699311592";
	public String  facebook_appSecret = "2df0ce2288cb9c39f1e20105681e7b8a";
	public String dailymotion_appID = "aefdbac397e2986d60e8";
	public String youtube_clientID = "505707172661-q2naf0ldkar5m6bqugadkpo62mfac9kg.apps.googleusercontent.com";
	public String youtube_developer_key = "";
	String url=null;
	public String get_longterm_token(String token,String service)
	{

		Client client = Client.create();
		if(service.equals("facebook"))
			url = "https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=" + this.facebook_appID + "&client_secret=" + this.facebook_appSecret + "&fb_exchange_token=" + token;
		else if(service.equals("youtube"))
		{

			this.getYoutubeAccessToken();
		}
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;

	}

	public String getYoutubeAccessToken()
	{

		//YouTubeService service = new YouTubeService(youtube_clientID, "AIzaSyDq9JjVW9OsTzQbPkywWSyhHhyqJQkRePU");

		System.out.println("modified youtube");
		String temp=null;
		//temp=AuthSubUtil.getRequestUrl("http://idaas-vcsui.dlinkddns.com:8080/IDaaS/loginSuccess.jsp","http://gdata.youtube.com",false,true);
		System.out.println("someting");
		//System.out.println(requestUrl);
		return null;
	}


}