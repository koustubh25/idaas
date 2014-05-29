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
*/
function youtube_auth(userid)
{
	/*
console.log("inside youtube");
OAuth.popup('youtube', function(error, result) {
  //handle error with error
  //use result.access_token in your API request
	$("#afterAssociation").html("<font color='green' align='center'>Login Credentials verfied. Now doing the association. Please Wait...</font><br>");
	pass_token_yt(result.access_token,userid,result.expires_in);
});*/
	pass_token_yt('sfafs','koustubh25','32234');
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


//Using redirection (option 2)
//OAuth.redirect('soundcloud', "http://idaas-vcsui.dlinkddns.com:8080/IDaaS/loginSuccess.jsp");

