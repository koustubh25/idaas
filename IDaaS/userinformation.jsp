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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link type="text/css" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/portal.css"/>
  
<title>Insert title here</title>
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

<br>

<%  
Databaseio dbio = new Databaseio();
Connection con = dbio.getConnection();
String userid = request.getParameter("userid");
try{
Statement stmt = con.createStatement();

ResultSet rSet = stmt.executeQuery("select * from user where userid='" + request.getParameter("userid") + "';");
rSet.next();

%>


<div class="userinformation">
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
	<div style="position:relative; text-align:right;"><a href="loginSuccess.jsp"> Back </a></div>
</div>	
	<%
	
	}

catch(Exception e)
{
	out.print("<p><font color=\"red\" >Something went wrong</font></p>");
	e.printStackTrace();
}
%>
</body>
</html>