//Our Application uses OAuth.io 3rd party library 

OAuth.initialize('Xb8pFNzFPazxC8GtJ13b06WsggQ');

function soundcloud_auth(userid)
{

OAuth.popup('soundcloud', function(error, result) {
  //handle error with error
  //use result.access_token in your API request
	pass_token_sc(result.access_token,userid)
});

function pass_token_sc(token,userid)
{
	
	var http = new XMLHttpRequest();
	var url = "updatetoken";
	var params = "service=soundcloud&userid=" + userid + "&token=" + token + "&expiry=infinity";
	http.open("POST", url, true);

	//Send the proper header information along with the request
	http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	http.send(params);
	$("#afterAssociation").html("<font color='green' align='center'>Login Credentials verfied. Now doing the association. Please Wait...</font><br>");
	setTimeout(function () {
		location.reload();
	},1000);


}


//Using redirection (option 2)
//OAuth.redirect('soundcloud', "http://idaas-vcsui.dlinkddns.com:8080/IDaaS/loginSuccess.jsp");

}