<%@ page import="service.Databaseio,java.sql.Connection,java.sql.ResultSet,java.sql.Statement,java.sql.DriverManager" %>
<%

if (session.getAttribute("username")== null)
		{
	response.sendRedirect("Login.jsp");
		}

%>
<!DOCTYPE html>
<html lang="en">
 <head>
 <script type="text/javascript" src="//connect.facebook.net/en_US/sdk.js"></script>
 <script type="text/javascript" src="js/facebook_login.js"></script>
  <title>Bootstrap Test</title>
  <link type="text/css" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"/>
  <link type="text/css" rel="stylesheet" href="css/portal.css"/>
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
      <a class="navbar-brand" href="#">Cisco</a>
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
        
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
    
		</div>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div style="left:10%;right:10%;height:10%;position:absolute;">

<font style="color:#0B173B; font-size:25px;">Current Users in the Household</font>


	<table class="table table-bordered table-striped datatable" style="height:100%;position:relative;">
<thead>
<tr>
<th>Select</th>
<th>User</th>
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
ResultSet rSet = stmt.executeQuery("select user.display_name,social.* from `social` LEFT JOIN `user` on user.userid=social.userid;");

while (rSet.next()) {
out.println("<tr> <td><input type='checkbox'></td><td>");

out.println("<a href='userinformation.jsp?userid=" + rSet.getString("userid") + "'>" +  rSet.getString("display_name")+ "</a>");

out.println("</td><td>");
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
	
	out.println("<td><button class='btn btn-success'>");
	out.println("Disassociate");
	out.println("</button></td>");
}
	
if(rSet.getString("youtube_token")==null)
{
	
	out.println("<td><button class='btn btn-danger'>");
	out.println("Associate");
	out.println("</button></td>");
}
else
{
	
	out.println("<td><button class='btn btn-success'>");
	out.println("Disassociate");
	out.println("</button></td>");
}

if(rSet.getString("dailymotion_token")==null)
{
	
	out.println("<td><button class='btn btn-danger'>");
	out.println("Associate");
	out.println("</button></td>");
}
else
{
	
	out.println("<td><button class='btn btn-success'>");
	out.println("Disassociate");
	out.println("</button></td>");
}

if(rSet.getString("soundcloud_token")==null)
{
	
	out.println("<td><button class='btn btn-danger'>");
	out.println("Associate");
	out.println("</button></td>");
}
else
{
	
	out.println("<td><button class='btn btn-success'>");
	out.println("Disassociate");
	out.println("</button></td>");
}


out.println("</tr>");
}
rSet.close();
con.close();
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
<button class="btn btn-success"><b>+</b> Add User</button> 
<button class="btn btn-danger"><b>-</b> Delete User </button>
</div>

	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
  
</body>
</html>