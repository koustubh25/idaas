package service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Token 
{
	public String facebook_appID = "1509102699311592";
	public String  facebook_appSecret = "2df0ce2288cb9c39f1e20105681e7b8a";
	String url=null;
	public String get_longterm_token(String token,String service)
	{


		Client client = Client.create();
		if(service.equals("facebook"))
			url = "https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=" + this.facebook_appID + "&client_secret=" + this.facebook_appSecret + "&fb_exchange_token=" + token;
		System.out.println(url);	
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;

	}


}