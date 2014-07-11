<!DOCTYPE html>
<%
//System.out.println(session.getAttribute("username"));
System.out.println("random");
if (session.getAttribute("username")!=null && session.getAttribute("username")!="invalid")
{
	response.sendRedirect("loginSuccess.jsp");
}

%>

<html lang="en">
 <head>
 <link type="text/css" rel="stylesheet" href="css/portal.css"/>
 <script type="text/javascript" src="js/generic.js"></script>
  <title>User Management Portal - VCS UI</title>
  <link type="text/css" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"/>
  
   <!-- Favicon -->
 <link rel="icon"  type="image/png"  href="pics/logos/cisco_vsmall.png">
 
</head>
<body>
<h1><center> <div style="font-family:initial">Welcome to Cisco CTAO Video Cloud Services  - User Managment Portal</div></center></h1><br>
<div class="container" style="margin-top:30px">
<div class="col-md-4 col-md-offset-4">
    <div class="panel panel-default">
  <div class="panel-heading"><h3 class="panel-title"><strong>Sign in </strong></h3></div>
  <div class="panel-body">
   <form role="form" action="login" method="post">
  <div class="form-group">
  
   <label>Login as </label><br>
  <select name="loginas" id="loginas" onchange="checkusertype()">
   <option value="moh" id="moh" >
  Member of Household
  </option>
  <option value="hoh" id="hoh" selected="selected">
  Head of household
  </option>
 
  </select>
  <br><br>
  <div id="headlogin"></div>
    <label>Username</label>
    <input type="text" class="form-control" style="border-radius:0px" name="userid" id="userid" placeholder="Enter Username">
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Password <a href="#">(forgot password)</a></label>
    <input type="password" class="form-control" style="border-radius:0px" name="password" id="password" placeholder="Password">
  </div>
  <button type="submit" class="btn btn-sm btn-default">Sign in</button><br><br>
  
  <%
  if (session.getAttribute("username")=="invalid")
  {
	out.print("<p><font color=\"red\" >Invalid user</font></p>");
	session.setAttribute("username", null);
  }	
%>	


</form>
  </div>
</div>
</div>
</div>
<div class="footer">Copyright © 2014 Cisco and/or its affiliates. All rights reserved.</div>
</body>
</html>