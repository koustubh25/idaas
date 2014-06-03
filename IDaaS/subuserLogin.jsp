<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="service.Databaseio,java.sql.Connection,java.sql.ResultSet,java.sql.Statement,java.sql.DriverManager" %>
    
    <%

if (session.getAttribute("username")== null)
		{
	response.sendRedirect("Login.jsp");
		}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <!-- Favicon -->
 <link rel="icon"  type="image/png"  href="pics/logos/cisco_vsmall.png">
 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <!-- Load OAuth.io Library -->
 <script type="text/javascript" src="js/OAuthio/oauth.js"> </script>
 
 <!-- Load soundcloud login-->
 <script type="text/javascript" src="js/soundcloud_login.js"></script>
 
  <!-- Loading Facebook Javascript SDK -->
 <script type="text/javascript" src="//connect.facebook.net/en_US/sdk.js"></script>
 <script type="text/javascript" src="js/facebook_login.js"></script>
 
 <!-- Loading Dailymotion Javascript SDK -->
 <script src="https://api.dmcdn.net/all.js"></script>
 <script type="text/javascript" src="js/dailymotion_login.js"></script>
 
 <!-- Youtube -->
  <script type="text/javascript" src="js/youtube_login.js"></script>
 
 <!-- Generic OTT functions -->
 <script type="text/javascript" src="js/OTTServices.js"></script>
 

  <link type="text/css" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/portal.css"/>
  
<title>Manage Social Accounts</title>
</head>
<body>

<nav class="navbar navbar-default" role="navigation" style="top:10%;position:relative;">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#"><img src="pics/logos/Cisco_logo.svg" style="position:relative;height:100%;"></a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
       <div class="title">
     User Management Portal </div>
      </ul>
        <ul class="nav navbar-nav navbar-right">
        <li><a href="logout">logout</a></li>
      
      </ul>
        <ul class="nav navbar-nav navbar-right">
        <li><div class="loggedinuser" id="username">&nbsp;&nbsp;&nbsp;<%=session.getAttribute("username") %> logged in </div></li>
      
      </ul>
  
      <form class="navbar-form navbar-right" role="search">
          <input type="text" class="form-control" placeholder="Search">
        
        <button type="submit" class="btn btn-default" disabled>Submit</button>
      </form>
    
		</div>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<br>
<div class="userinformation">
<table class="table table-bordered table-striped datatable" style="height:100%;position:relative;">
<thead>
<tr>
<th>Household name</th>
<th>User ID</th>
<th>Facebook Account</th>
<th>Youtube Account</th>
<th>Dailymotion Account</th>
<th>SoundCloud Account</th>

</tr>
</thead>
<tbody>
<%  
Databaseio dbio = new Databaseio();
Connection con = dbio.getConnection();
try{
Statement stmt = con.createStatement();
ResultSet rSet = stmt.executeQuery(" select * from user a,social b,household c where a.userid=b.userid and a.userid='" + session.getAttribute("username")+"'");

while (rSet.next()) {
out.println("<tr> ");

out.println("<td>");
out.println(rSet.getString("DefaultUser"));
out.println("</td>");

out.println("<td>");
out.println(rSet.getString("userid"));
out.println("</td>");

if(rSet.getString("facebook_token")==null)
{
	
	out.println("<td><button class='btn btn-danger'  onclick='facebook_auth(\"" + rSet.getString("userid") + "\")'>");
	out.println("Associate");
	out.println("</button></td>");
}
else
{
	
	out.println("<td><button class='btn btn-success' onclick='disassociate(\"" + rSet.getString("userid") + "\",\"facebook\""  +  ")'>");
	out.println("Disassociate");
	out.println("</button></td>");
}
	
if(rSet.getString("youtube_token")==null)
{
	
	out.println("<td><button class='btn btn-danger'  onclick='youtube_auth(\"" + rSet.getString("userid") + "\")'>");
	out.println("Associate");
	out.println("</button></td>");
}
else
{
	
	out.println("<td><button class='btn btn-success'  onclick='disassociate(\"" + rSet.getString("userid") + "\",\"youtube\""  +  ")'>");
	out.println("Disassociate");
	out.println("</button></td>");
}

if(rSet.getString("dailymotion_token")==null)
{
	
	out.println("<td><button class='btn btn-danger'  onclick='dailymotion_auth(\"" + rSet.getString("userid") + "\")'>");
	out.println("Associate");
	out.println("</button></td>");
}
else
{
	
	out.println("<td><button class='btn btn-success'  onclick='disassociate(\"" + rSet.getString("userid") + "\",\"dailymotion\""  +  ")'>");
	out.println("Disassociate");
	out.println("</button></td>");
}

if(rSet.getString("soundcloud_token")==null)
{
	
	out.println("<td><button class='btn btn-danger'  onclick='soundcloud_auth(\"" + rSet.getString("userid") + "\")'>");
	out.println("Associate");
	out.println("</button></td>");
}
else
{
	
	out.println("<td><button class='btn btn-success'  onclick='disassociate(\"" + rSet.getString("userid") + "\",\"soundcloud\""  +  ")'>");
	out.println("Disassociate");
	out.println("</button></td>");
}


out.println("</tr>");
}

}
catch(Exception e)
{
	e.printStackTrace();
	out.println("<font color='red'>Something went wrong</font>");
}

%>


<td></td>
<td colspan="6" align="center"></td>
</tr>
</tbody>
</table>



<%  

String userid = request.getParameter("userid");
try{
Statement stmt = con.createStatement();

ResultSet rSet = stmt.executeQuery("select * from user where userid='" + request.getParameter("userid") + "';");
rSet.next();

%>



<font style="color:#0B173B; font-family:sans serif; font-size:35px;"> <%=rSet.getString("name") %></font>
<table class='table borderless'>
	</tbody>
<tr>
<td>Profile Picture</td>
<td><img src='<%=rSet.getString("prof_pic")%>'></td>
</tr>

<tr>
<td>UserID</td>
<td><%=rSet.getString("userid") %></td>
</tr>
<tr>
<td>E-Mail</td>
<td><%=rSet.getString("email") %></td>
</tr>
<tr>
<td>Birth date</td>
<td><%=rSet.getString("birthday") %></td>
</tr>
<tr>
<td>Current Location</td>
<td><%=rSet.getString("current_location") %></td>
</tr>
<tr>
<td>Date Created</td>
<td><%=rSet.getString("creation_date") %></td>
</tr>
<tr>
<td>Last Update</td>
<td><%=rSet.getString("last_update") %></td>
</tr>


</tbody>
	</table>
	
</div>	
	<%
	rSet.close();
	con.close();
	}

catch(Exception e)
{
	out.print("<p><font color=\"red\" >Something went wrong</font></p>");
	e.printStackTrace();
}
%>


</div>

</body>
</html>