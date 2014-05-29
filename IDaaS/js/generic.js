function checkusertype()
{
	var loginas = document.getElementById('loginas');
	var headLogin = document.getElementById("headlogin");
	var selectedLoginAs=(loginas.options[loginas.selectedIndex].value);
	if(selectedLoginAs=="moh")
	{
		
		headLogin.innerHTML = '<label>Household Name</label><input type="text" class="form-control" style="border-radius:0px" name="householdid" id="householdid" placeholder="Enter Household ID">';
		
	}
	else if(selectedLoginAs=="hoh")
	{
		headLogin.innerHTML = ""
		
	}
	
}