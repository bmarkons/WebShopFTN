package ftn.ra122013.webshop.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ftn.ra122013.webshop.beans.Administrator;
import ftn.ra122013.webshop.beans.User;
import ftn.ra122013.webshop.dao.WebShopDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private WebShopDAO DAO = WebShopDAO.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// User user = (User) session.getAttribute("user");

		// LOGOUT
		if (request.getParameter("logout").equals("true")) {
			try {
				session.invalidate();
			} catch (Exception ex) {
			}
			response.sendRedirect("index.html");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null) {
			if (user instanceof Administrator) {
				response.sendRedirect("admin.html");
			} else {
				response.sendRedirect("home.html");
			}
			return;
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username == null || password == null) {
			response.sendRedirect("index.html");
			return;
		}
		if (username.equals("") || password.equals("")) {
			response.sendRedirect("index.html");
			return;
		}
		User loginUser = DAO.getUser(username);
		if (loginUser != null) {
			if (loginUser.getPassword().equals(password)) {
				session.setAttribute("user", (Object) loginUser);
				if (loginUser instanceof Administrator) {
					response.sendRedirect("admin.html");
				} else {
					response.sendRedirect("home.html");
				}
				return;
			}
		}
		response.sendRedirect("index.html");
	}

}
