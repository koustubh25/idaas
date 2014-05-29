function test()
{
	console.log("inside");
	var http = new XMLHttpRequest();
	var url = "https://www.google.com/accounts/AuthSubRequest";
	var params = "next=http://idaas-vcsui.dlinkddns.com:8080/IDaaS/loginSuccess.jsp&scope=offline&session=1&secure=0";
	http.open("GET", url, true);

	//Send the proper header information along with the request
	http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	http.send(params);
	/*setTimeout(function () {
		location.reload();
	},1000);
*/

}