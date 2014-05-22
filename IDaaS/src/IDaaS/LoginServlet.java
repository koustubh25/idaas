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
		
	
		
		String userId, password ;
		HttpSession session=null;
		userId = request.getParameter("userid");
		password = request.getParameter("password");

		UserManagement loginservice = new UserManagement();
		session = request.getSession();
		boolean result = loginservice.authenticate(userId, password);
		if(result)
		{
			session.setAttribute("username", userId);
			response.sendRedirect("loginSuccess.jsp");
		
		}
		else
		{
			session.setAttribute("username","invalid");
			System.out.print("Invalide message");
			response.sendRedirect("Login.jsp");
	}
		}

}
