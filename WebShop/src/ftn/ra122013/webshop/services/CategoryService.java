package ftn.ra122013.webshop.services;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import ftn.ra122013.webshop.beans.*;
import ftn.ra122013.webshop.dao.*;

@Path("/category")
public class CategoryService {

	WebShopDAO DAO = WebShopDAO.getInstance();

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/get/{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategory(@PathParam("name") String cateogoryName) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			return "ERORR. NOT LOGGED IN";
		}

		Category category = DAO.getCategory(cateogoryName);
		ObjectMapper maper = new ObjectMapper();
		String jsonCategory = null;
		try {
			jsonCategory = maper.writeValueAsString(category);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonCategory;
	}

	@GET
	@Path("/getAll")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCategories() {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			return "ERORR. NOT LOGGED IN";
		}

		CategoryTree categories = DAO.getAllCategories();
		ObjectMapper maper = new ObjectMapper();
		String jsonCategory = null;
		try {
			jsonCategory = maper.writeValueAsString(categories);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonCategory;
	}

	@PUT
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String addCategory(@FormParam("parent") String parent, @FormParam("name") String name,
			@FormParam("description") String description) {
		HttpSession session = request.getSession();
		if (!(session.getAttribute("user") instanceof Administrator)) {
			return "ERORR. NOT ADMIN";
		}

		// Validacija unosa; Ime obavezno!
		if (name == null) {
			return "ERROR";
		}
		if (name.equals("")) {
			return "ERROR";
		}
		if (DAO.existCategory(name)) {
			return "ERROR";
		}

		DAO.addCategory(new Category(description, name), parent);
		return "OK";
	}

	@POST
	@Path("/update/{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCategory(@PathParam("name") String categoryName, @FormParam("description") String description,
			@FormParam("parent") String parentName) {
		HttpSession session = request.getSession();
		if (!(session.getAttribute("user") instanceof Administrator)) {
			return "ERORR. NOT ADMIN";
		}

		Category updatedCategory = new Category();
		updatedCategory.setDescription(description);
		updatedCategory.setParent(new Category(null, parentName));
		DAO.updateCategory(categoryName, updatedCategory);
		return "OK";
	}

	@DELETE
	@Path("/delete/{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCategory(@PathParam("name") String categoryName) {
		HttpSession session = request.getSession();
		if (!(session.getAttribute("user") instanceof Administrator)) {
			return "ERORR. NOT ADMIN";
		}

		Category removed = DAO.removeCategory(categoryName);
		if (removed == null) {
			return "ERROR. Category " + categoryName + " not found.";
		} else {
			return "OK";
		}
	}
}
