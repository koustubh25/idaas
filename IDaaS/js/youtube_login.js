var OAUTHURL    =   'https://accounts.google.com/o/oauth2/auth?';
var VALIDURL    =   'https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=';
var SCOPE       =   'https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email http://gdata.youtube.com';
var CLIENTID    =   '3435603744-vpvkoukg6aufnomn598nh5quqjnhlh9m.apps.googleusercontent.com';
var REDIRECT    =   'https://idaas-vcsui.dlinkddns.com:8443/IDaaS/';
var LOGOUT      =   'http://accounts.google.com/Logout';
var TYPE        =   'token';
var _url        =   OAUTHURL + 'scope=' + SCOPE + '&client_id=' + CLIENTID + '&redirect_uri=' + REDIRECT + '&response_type=' + TYPE;
var acToken;
var tokenType;
var expiresIn;
var user;
var loggedIn    =   false;
var dev_key = 'AIzaSyDzZvkvXT8gl5XnLMkccAsa3LqSQ1ksgnI';
var youtube_recos=[];
var recommended;
var index=0;
var duration_reco=0;


/*function youtube_auth(userid)
{
	var http = new XMLHttpRequest();
	var url = "updatetoken";
	var params = "service=youtube&userid=" + userid + "&token=" + "null";
	http.open("POST", url, true);

	//Send the proper header information along with the request
	http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

	http.send(params);
	setTimeout(function () {
		location.reload();
	},1000);
	
}


//Our Application uses OAuth.io 3rd party library 

OAuth.initialize('Xb8pFNzFPazxC8GtJ13b06WsggQ');

function youtube_auth(userid)
{
	
console.log("inside youtube");
OAuth.popup('youtube', function(error, result) {
  //handle error with error
  //use result.access_token in your API request
	$("#afterAssociation").html("<font color='green' align='center'>Login Credentials verfied. Now doing the association. Please Wait...</font><br>");
	pass_token_yt(result.access_token,userid,result.expires_in);
});
//	pass_token_yt('sfafs','koustubh25','32234');
}*/

function youtube_auth(userid)
{
	
	var win         =   window.open(_url, "windowname1", 'width=200, height=200'); 

	var pollTimer   =   window.setInterval(function() { 
		try {
			console.log(win.document.URL);
			if (win.document.URL.indexOf(REDIRECT) != -1) {
				window.clearInterval(pollTimer);
				var url =   win.document.URL;
				acToken =   gup(url, 'access_token');
				tokenType = gup(url, 'token_type');
				expiresIn = gup(url, 'expires_in');
				
				
				
					console.log("inside callback");
					pass_token_yt(acToken,userid,expiresIn);
					win.close();
				

			}
		} catch(e) {
		}
	}, 500);
	


}


function pass_token_yt(token,userid,expiry)
{
	console.log(token + userid + expiry);
	var http = new XMLHttpRequest();
	var url = "updatetoken";
	var params = "service=youtube&userid=" + userid + "&token=" + token + "&expiry=" + expiry;
	http.open("POST", url, true);

	//Send the proper header information along with the request
	http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	http.send(params);
	setTimeout(function () {
	location.reload();
	},1000);


}
function gup(url, name) {
	name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
	var regexS = "[\\#&]"+name+"=([^&#]*)";
	var regex = new RegExp( regexS );
	var results = regex.exec( url );
	if( results == null )
		return "";
	else
		return results[1];
}

function startLogoutPolling() {
	$('#loginText').show();
	$('#logoutText').hide();
	loggedIn = false;
	$('#uName').text('You are succesfully logged out of your Youtube account ');
	$('#imgHolder').attr('src', 'none.jpg');

}


//Using redirection (option 2)
//OAuth.redirect('soundcloud', "http://idaas-vcsui.dlinkddns.com:8080/IDaaS/loginSuccess.jsp");

