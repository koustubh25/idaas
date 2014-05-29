package IDaaS;

import java.io.IOException;
import service.jerseyTestClient;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.UserManagement;


/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	
		
		String userId, password,loginas ;
		HttpSession session=null;
		boolean result=false;
		userId = request.getParameter("userid");
		password = request.getParameter("password");
		loginas = request.getParameter("loginas");
		UserManagement loginservice = new UserManagement();
		session = request.getSession();
		if(loginas.equals("hoh"))
		{
		result = loginservice.authenticate(userId, password,loginas,null);
		}
		else
		{
			String householdid = request.getParameter("householdid");
			result = loginservice.authenticate(userId, password,loginas,householdid);
		}
			if(result)
		{
			session.setAttribute("username", userId);
			if(loginas.equals("hoh"))
			response.sendRedirect("loginSuccess.jsp");
			else
				response.sendRedirect("subuserLogin.jsp?userid=" + userId);
		}
		else
		{
			session.setAttribute("username","invalid");
			System.out.print("Invalide message");
			response.sendRedirect("Login.jsp");
	}
		}

}
