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
  <title>Bootstrap Test</title>
  <link type="text/css" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"/>
</head>
<div class="container" style="margin-top:30px">
<div class="col-md-4 col-md-offset-4">
    <div class="panel panel-default">
  <div class="panel-heading"><h3 class="panel-title"><strong>Sign in </strong></h3></div>
  <div class="panel-body">
   <form role="form" action="login" method="post">
  <div class="form-group">
    <label>Username</label>
    <input type="text" class="form-control" style="border-radius:0px" name="userid" id="userid" placeholder="Enter Username">
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Password <a href="/sessions/forgot_password">(forgot password)</a></label>
    <input type="password" class="form-control" style="border-radius:0px" name="password" id="password" placeholder="Password">
  </div>
  <button type="submit" class="btn btn-sm btn-default">Sign in</button>
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
</body>
</html>