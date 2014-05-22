package service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class jerseyTestClient 
{
	public String appID = "1509102699311592";
	public String short_term_access_token="CAAVchVpScegBAEs85V7lxEnQqKyKGX8hPwZB4XaxDzXcZBNct4PZA7hfOeZC6g5ulJrOnrbXuC9Rrw2I14m2B4HEZCs4ttZAJOYx2jLQtvIj5vF24KEUKMAAdUqdXxZCQjbxANvqgd3KBYruxH2xBpZBn98qKUk84BCZCJMh4izmVjlWeGRXR1018";
	 public String  appSecret = "2df0ce2288cb9c39f1e20105681e7b8a";

	public void get_long_term_tokens()
	{
	
		
		Client client = Client.create();
		WebResource webResource = client.resource("https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=\'" + this.appID + "\'&client_secret=\'" + this.appSecret + "\'&fb_exchange_token=\'" + this.short_term_access_token);
		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
		System.out.println("Get Operation");
		String output = response.getEntity(String.class);
		System.out.println(output);

	}


}