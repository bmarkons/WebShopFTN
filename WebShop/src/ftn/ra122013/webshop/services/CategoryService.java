package ftn.ra122013.webshop.services;

import java.io.IOException;
import java.util.ArrayList;

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
import ftn.ra122013.webshop.json.JSONParser;

@Path("/category")
public class CategoryService {

	

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	WebShopDAO DAO = WebShopDAO.getInstance();

	@GET
	@Path("/get/{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategory(@PathParam("name") String categoryName) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			return "ERORR. NOT LOGGED IN";
		}

		Category category = DAO.getCategory(categoryName);
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
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCategories() {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			return JSONParser.getSimpleResponse("NOT LOGGED IN");
		}

		CategoryTree cTree = DAO.getAllCategories();
		ArrayList<Category> categories = cTree.getRoots();

		String retJson = JSONParser.toJSON(categories);
		System.out.println(retJson);

		return retJson;
	}

	@PUT
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addCategory(Category category) {
		HttpSession session = request.getSession();
		if (!(session.getAttribute("user") instanceof Administrator)) {
			return JSONParser.getSimpleResponse("ERORR. NOT ADMIN");
		}

		// Validacija unosa; Ime obavezno!
		if (category.getName() == null) {
			return JSONParser.getSimpleResponse("ERORR");
		}
		if (category.getName().equals("")) {
			return JSONParser.getSimpleResponse("ERORR");
		}
		if (DAO.existCategory(category.getName())) {
			return JSONParser.getSimpleResponse("ERORR");
		}
		if(category.getParent() == null){
			category.setParent(new Category(null, "root"));
		}
		
		if (DAO.addCategory(category, category.getParent().getName())) {
			return JSONParser.getSimpleResponse("Category '" + category.getName() + "' has been added.");
		} else {
			return JSONParser.getSimpleResponse("Error");
		}
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCategory(Category category) {
		HttpSession session = request.getSession();
		if (!(session.getAttribute("user") instanceof Administrator)) {
			return "ERORR. NOT ADMIN";
		}
		if(category.getName() == null){
			return JSONParser.getSimpleResponse("ERROR");
		}
		if(category.getName().equals("")){
			return JSONParser.getSimpleResponse("ERROR");
		}
		if(category.getParent() == null){
			category.setParent(new Category(null, "root"));
		}
		DAO.updateCategory(category.getName(), category);
		return JSONParser.getSimpleResponse("OK");
	}

	@DELETE
	@Path("/remove/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteCategory(@PathParam("name") String categoryName) {
		HttpSession session = request.getSession();
		if (!(session.getAttribute("user") instanceof Administrator)) {
			return JSONParser.getSimpleResponse("ERORR. NOT ADMIN");
		}
		Category removed = DAO.removeCategory(categoryName);
		if (removed == null) {
			return JSONParser.getSimpleResponse("ERROR. Category " + categoryName + " not found.");
		} else {
			return JSONParser.getSimpleResponse("Category '" + categoryName + "' removed.");
		}
	}
}
