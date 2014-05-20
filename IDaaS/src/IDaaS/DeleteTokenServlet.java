package IDaaS;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.Databaseio;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;


/**
 * Servlet implementation class LoginServlet
 */
public class DeleteTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String userid = request.getParameter("userid");
		String service = request.getParameter("service");
		
		System.out.println(userid + service);
		boolean updated=this.removeToken(service,userid);
		//updated=false;
		if(updated==false)
		{
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=UTF-8");
			out.println("Chek the Parameters you passed");
		}
		else
		{
		//	response.sendRedirect("loginSuccess.jsp");
			response.setHeader("Refresh", "1");
		}
		
	}


public boolean removeToken(String service, String userid)
	{
	
		try{
			Databaseio dbio = new Databaseio();
			Connection con = dbio.getConnection();
			Statement stmt = con.createStatement();
			String query="update social set " + service + "_token=null," + service + "_token_expiry=null where userid='" + userid + "'";
			System.out.println(query);
			stmt.executeUpdate(query);
			return true;
		}
		
		catch(Exception e){
			System.out.println("Error inserting token in the Database. Assocition not done");
			e.printStackTrace();
			return false;
		}
		
	}
}
