var appID = '1509102699311592';
var appSecret = '2df0ce2288cb9c39f1e20105681e7b8a';

function facebook_auth(userid)
{
	//Initialize

	FB.init({
		appId      : appID,
		xfbml      : true,
		version    : 'v2.0'
	});

	FB.login(function(response) {
		if (response.authResponse) {
			var short_term_access_token =   FB.getAuthResponse()['accessToken']; 
			// Obtain short term access token. Use this to obtain long term access token (60 Days)

			getLoginTermAccessToken(short_term_access_token);
		} else {
			console.log('User cancelled login or did not fully authorize.');
		}
	}, {scope: 'user_likes,user_videos'});

}

function getLoginTermAccessToken(short_term_access_token)
{
	console.log("inside logn term");
	/*  $.ajax({
		    url: 'https://graph.facebook.com/oauth/access_token',
		    type: 'GET',
		    data: 'grant_type=fb_exchange_token&client_id=' + appID + '&client_secret=' + appSecret + '&fb_exchange_token=' + short_term_access_token, 
		    success: function(response) { alert(reponse); }
		});*/
	var url = 'https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=' + appID + '&client_secret=' + appSecret + '&fb_exchange_token=' + short_term_access_token;
	var client = new XMLHttpRequest();  
	client.open("GET", url, false);
	client.setRequestHeader("Content-Type", "text/plain");
	client.send(null);
	if(client.status == 200 )
	{
		console.log(client.responseText);
	}
	else
		{
		
		
		}

}
