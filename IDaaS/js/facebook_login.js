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

			getLoginTermAccessToken(short_term_access_token,userid);
		} else {
			console.log('User cancelled login or did not fully authorize.');
		}
	}, {scope: 'user_likes,user_videos'});

}

function getLoginTermAccessToken(short_term_access_token,userid)
{
	console.log("inside logn term");
	//Synchronous call to obtain long term access token
	var url = 'https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=' + appID + '&client_secret=' + appSecret + '&fb_exchange_token=' + short_term_access_token;
	var client = new XMLHttpRequest();  
	client.open("GET", url, false);
	client.setRequestHeader("Content-Type", "text/plain");
	client.send(null);
	if(client.status == 200 )
	{
		console.log(JSON.stringify(client.responseText));
		insertToken(client.responseText,userid);
		$("#afterAssociation").html("<font color='green'>Login Credentials verfied. Now doing the association. Please Wait...</font><br>");
		//$.post("/updatetoken",{service:"facebook",userid:userid,token:"asdfasd":expiry:252345},function(result){});
	}
	else
		{
		$("#afterAssociation").html("<font color='red'>Error Asscoaiting</font><br>");
	
		}

}

function insertToken(response,userid)
{
	var parsed  = (response.split("access_token=")[1]).split("&");
	var token=parsed[0];
	var expiry = parsed[1].split("expires=")[1];

	var http = new XMLHttpRequest();
	var url = "updatetoken";
	var params = "service=facebook&userid=" + userid + "&token=" + token + "&expiry=" + expiry;
	http.open("POST", url, true);

	//Send the proper header information along with the request
	http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	
	http.send(params);
	setTimeout(function () {
		location.reload();
		},500);

	//$.post("/updatetoken",{service:"facebook",userid:userid,token:"asdfasd":expiry:252345},function(result){});
}




