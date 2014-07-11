package IDaaS;

import java.io.IOException;


import java.io.PrintWriter;

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
import service.Token;

/**
 * Servlet implementation class LoginServlet
 */
public class UpdateTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Token long_term_token = new Token();


		String userid = request.getParameter("userid");
		String service = request.getParameter("service");
		String token = request.getParameter("token");
		
		String expiry = null;
		boolean updated=false;
		System.out.println(service);
		//boolean updated=this.insertToken(service,userid,token,Integer.parseInt(expiry));
		if (service.equals("facebook"))
		{
			String parts[]= long_term_token.get_longterm_token(token,service).split("&expires=");
			expiry=parts[1];
			token =  parts[0].split("access_token=")[1];
		}	
		else if (service.equals("dailymotion"))
		{
			//converting from milliseconds to seconds and then back to String
			expiry=Integer.toString((Integer.parseInt(request.getParameter("expiry"))/1000));
			System.out.println(userid + expiry);
			
		}
		else if (service.equals("youtube"))
		{
			//expiry=Integer.toString((Integer.parseInt(request.getParameter("expiry"))));
			//System.out.println(userid + expiry);
			
			//long_term_token.get_longterm_token(null,"youtube");
			expiry=request.getParameter("expiry");
			System.out.println("inside youtube");
			
			updated=this.insertToken("youtube",userid,token,Integer.parseInt(expiry));
			//System.out.println("exiting ling term token");
			
		}
		else if (service.equals("soundcloud"))
		{
			
			expiry=request.getParameter("expiry");
			updated=this.insertToken(service,userid,token,0);
		
			
		}
		if(!service.equals("soundcloud"))
		{
			System.out.println("check youtube");
			System.out.println(expiry);
			updated=this.insertToken(service,userid,token,Integer.parseInt(expiry));
		}
			//updated=false;
		if(updated==false)
		{
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=UTF-8");
			out.println("<html> Error with the Association . Click <a href='loginsuccess.jsp'>back</a> to return</html>");
		}
		else
		{
			//	response.sendRedirect("loginSuccess.jsp");
			response.setHeader("Refresh", "1");
		}

	}


	public boolean insertToken(String service, String userid, String token, int expiry)
	{
		System.out.println("inside insert token" + expiry);
		try{
			
			Databaseio dbio = new Databaseio();
			Connection con = dbio.getConnection();
			Statement stmt = con.createStatement();
			String query=null;
			if(service.equals("soundcloud"))
			{
				 query="update social set " + service + "_token='" + token + "'," + service + "_token_expiry=\'0000-00-00\' where userid='" + userid + "'";
				
			}
				else
				{
					 query="update social set " + service + "_token='" + token + "'," + service + "_token_expiry=ADDDATE(current_timestamp, INTERVAL " + expiry +  " second) where userid='" + userid + "'";
				}
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
