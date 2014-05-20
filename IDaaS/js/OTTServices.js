function disassociate(userid,service)
{
	$("#afterAssociation").html("<font color='green'>Disassociating, Please Wait...</font><br>");
	var http = new XMLHttpRequest();
	
	var url = "deletetoken";
	var params = "userid=" +  userid  + "&service=" + service;
	console.log(params);
	http.open("POST", url, true);

	//Send the proper header information along with the request
	http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	console.log(params);
	http.send(params);
	setTimeout(function () {
	location.reload();
	},500);
	
	


}