function dailymotion_auth(userid)
{
	console.log("finally inside");
	DM.init({apiKey: 'aefdbac397e2986d60e8', status: true, cookie: true});

	DM.login(function(response)
			{
		if (response.session)
		{
			//Login check;
			var token = response.session.access_token;
			var expiry = response.session.expires_at;
			pass_token_dm(token,expiry,userid);

		}
		else
		{
			// user is not logged in
		}
			}, {scope: 'read write'});

}


function pass_token_dm(short_term_token,expiry,userid)
{
	
	
	var http = new XMLHttpRequest();
	var url = "updatetoken";
	var params = "service=dailymotion&userid=" + userid + "&token=" + short_term_token + "&expiry=" + expiry;
	http.open("POST", url, true);

	//Send the proper header information along with the request
	http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

	http.send(params);
	$("#afterAssociation").html("<font color='green' align='center'>Login Credentials verfied. Now doing the association. Please Wait...</font><br>");
	setTimeout(function () {
		location.reload();
	},1000);
}