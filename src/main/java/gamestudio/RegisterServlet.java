package gamestudio;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import services.UserService;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	UserService userservice;
	@Inject
	User user;
	private boolean checkLog = false;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String name = null;
		String password = null;
		// String sName = request.getParameter("sName");
		// String rName = request.getParameter("rName");
		// String sPassword = request.getParameter("sPassword");
		// String r1Password = request.getParameter("r1Password");
		// String r2Password = request.getParameter("r2Password");
		String signOut = request.getParameter("signOut");
		String clickRegister = request.getParameter("clickRegister");
		if (request.getSession(false).getAttribute("user") != null) {
			user = (User) request.getSession(false).getAttribute("user");
			name = user.getName();
			password = user.getPasswd();
			System.out.println("vytvorena session a spravilo user");
		} else if (checkLog) {
			request.getSession().setAttribute("user", user);
		}

		// if (name == null) {
		// name = "";
		// }
		// if (password == null) {
		// password = "";
		// }
		// if (!"".equals(name) && !"".equals(password)) {
		// user = new User();
		// user.setName(name);
		// user.setPasswd(password);
		// request.getSession().setAttribute("user", user);
		// }
		if ("true".equals(signOut)) {
			request.getSession().removeAttribute("user");
			user = null;
		}
		if (request.getSession(false).getAttribute("user") != null && !"true".equals(clickRegister)) {
			try {
				if (userservice.findUser(user.getName(), user.getPasswd())) {
					name = user.getName();
					password = user.getPasswd();
					out.println("<div class=\"col-md-3 col-md-offset-1\" style=\"margin-top:20px;\">");
					out.println("Vitaj " + name + ".<br><a href=\"?signOut=true\">[sign out]</a>");
					out.println("</div>");// col-md-3
				}
			} catch (Exception e) {
				System.out.println("Empty database!");
				out.println("<div class=\"col-md-12 center\" style=\"margin-top:20px;\">");
				out.println("<span style=\"color:red\">User is not in the database!</span><br>");
				out.println(
						"<a href=\"register.jsf?clickRegister=true\" class=\"btn btn-primary\" role=\"button\">Back</a>");
				out.println("</div>");// col-md-3
			}
		} else if (!"true".equals(clickRegister)) {
			out.println("<div class=\"col-md-3 col-md-offset-1\" style=\"margin-top:20px;\">");
			out.println("Please <a href=\"register.jsf?clickRegister=true\">sign in.</a>");
			out.println("</div>");// col-md-3
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	

}
