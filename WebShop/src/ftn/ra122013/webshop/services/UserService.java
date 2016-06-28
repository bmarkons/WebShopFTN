package ftn.ra122013.webshop.services;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ftn.ra122013.webshop.beans.*;
import ftn.ra122013.webshop.dao.*;
import ftn.ra122013.webshop.json.JSONParser;

@Path(value = "/user")
public class UserService {
	WebShopDAO DAO = WebShopDAO.getInstance();
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path(value = "/test")
	@Produces(value = { "text/plain" })
	public String test() {
		return "Hello Jersey";
	}

	@GET
	@Path("/me")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser() {
		HttpSession session = this.request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "null";
		}
		User me = DAO.getUser(user.getUsername());
		return JSONParser.toJSON(me);
	}

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllUsers() {
		HttpSession session = this.request.getSession();
		if (session.getAttribute("user") == null) {
			SimpleResponse response = new SimpleResponse("ERROR");
			return JSONParser.toJSON(response);
		}

		ArrayList<User> users = DAO.getAllUsers();
		return JSONParser.toJSON(users);
	}

	@GET
	@Path("/get/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(@PathParam("username") String username) {
		HttpSession session = this.request.getSession();
		if (session.getAttribute("user") == null) {
			return "ERROR. Not logged in";
		}

		User user = DAO.getUser(username);
		return JSONParser.toJSON(user);
	}

	@POST
	@Path(value = "/register")
	@Consumes(value = { "application/x-www-form-urlencoded" })
	@Produces(value = { "text/plain" })
	public String register(@FormParam(value = "username") String username,
			@FormParam(value = "password") String password, @FormParam(value = "address") String address,
			@FormParam(value = "country") String country, @FormParam(value = "email") String email,
			@FormParam(value = "name") String name, @FormParam(value = "surname") String surname,
			@FormParam(value = "telephone") String telephone, @FormParam(value = "type") String type) {
		if (username == null || password == null || email == null || type == null) {
			return "ERROR";
		}
		if (username.equals("") || password.equals("") || email.equals("") || type.equals("")) {
			return "ERROR";
		}
		if (this.DAO.existsUser(username)) {
			return "ERROR";
		}

		if (type.equals("buyer")) {
			Buyer newBuyer = new Buyer(username, password, address, country, email, name, surname, telephone);
			this.DAO.addUser(newBuyer);
			return "OK";
		}

		if (type.equals("seller")) {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user instanceof Administrator) {
				Seller newSeller = new Seller(username, password, address, country, email, name, surname, telephone);
				DAO.addUser(newSeller);
				return "OK";
			}
		}

		return "ERROR";

	}

	@POST
	@Path(value = "/login")
	@Consumes(value = { "application/x-www-form-urlencoded" })
	@Produces(value = { "text/plain" })
	public String login(@FormParam(value = "username") String username,
			@FormParam(value = "password") String password) {
		if (username == null || password == null) {
			return "ERROR";
		}
		if (username.equals("") || password.equals("")) {
			return "ERROR";
		}
		HttpSession session = this.request.getSession();
		if (session.getAttribute("user") != null) {
			return "Already logged in";
		}
		User loginUser = this.DAO.getUser(username);
		if (loginUser != null) {
			if (loginUser.getPassword().equals(password)) {
				session.setAttribute("user", (Object) loginUser);
				return "OK";
			}
		}
		return "Unreckognized user";
	}

	@DELETE
	@Path("/remove/{username}")
	@Consumes(value = { "application/x-www-form-urlencoded" })
	@Produces(MediaType.APPLICATION_JSON)
	public String removeUser(@PathParam(value = "username") String username) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user instanceof Administrator || user.getUsername().equals(username)) {
			if (DAO.removeUser(username)) {
				return JSONParser.getSimpleResponse("OK");
			}
		}
		return JSONParser.getSimpleResponse("ERROR");
	}
	
	@POST
	@Path("/update")
	@Consumes(value = { "application/x-www-form-urlencoded" })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateUser(@FormParam(value = "username") String username,
			@FormParam(value = "password") String password, @FormParam(value = "address") String address,
			@FormParam(value = "country") String country, @FormParam(value = "email") String email,
			@FormParam(value = "name") String name, @FormParam(value = "surname") String surname,
			@FormParam(value = "telephone") String telephone){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(!user.getUsername().equals(username)){
			return JSONParser.getSimpleResponse("ERROR");
		}
		if (username == null || password == null || email == null) {
			return "ERROR";
		}
		if (username.equals("") || password.equals("") || email.equals("")) {
			return "ERROR";
		}
		DAO.updateUser(username,password,address,country,email,name,surname,telephone);
		return JSONParser.getSimpleResponse("OK");
	}
}
