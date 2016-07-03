package ftn.ra122013.webshop.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;

import ftn.ra122013.webshop.beans.Administrator;
import ftn.ra122013.webshop.beans.Product;
import ftn.ra122013.webshop.beans.User;
import ftn.ra122013.webshop.dao.WebShopDAO;
import ftn.ra122013.webshop.json.JSONParser;

@Path("/product")
public class ProductService {

	WebShopDAO DAO = WebShopDAO.getInstance();

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@POST
	@Path("/rate")
	public void rateProduct() {

	}

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllProducts() {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return JSONParser.getSimpleResponse("ERROR");
		}

		ArrayList<Product> products = DAO.getAllProducts();
		String retVal = JSONParser.toJSON(products);
		return retVal;
	}

	@PUT
	@Path("/add/{storeCode}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addProduct(Product product, @PathParam("storeCode") String storeCode) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Administrator) && !DAO.isAuthorizedSeller(user, storeCode)) {
			return JSONParser.getSimpleResponse("ERORR. YOU ARE NOT ADMIN OR AUTHORIZED SELLER.");
		}

		if (DAO.addProduct(storeCode, product)) {
			return JSONParser.getSimpleResponse("OK");
		} else {
			return JSONParser.getSimpleResponse("ERROR");
		}
	}

	@POST
	@Path("/update/{storeCode}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateProduct(Product product, @PathParam("storeCode") String storeCode) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Administrator) && !DAO.isAuthorizedSeller(user, storeCode)) {
			return JSONParser.getSimpleResponse("ERORR. YOU ARE NOT ADMIN OR AUTHORIZED SELLER.");
		}

		if (DAO.updateProduct(storeCode, product)) {
			return JSONParser.getSimpleResponse("OK");
		} else {
			return JSONParser.getSimpleResponse("ERROR");
		}
	}

	@DELETE
	@Path("/remove/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeProduct(@PathParam("code") String code){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Administrator) && !DAO.isAuthorizedSeller(user, code)) {
			return JSONParser.getSimpleResponse("ERORR. YOU ARE NOT ADMIN OR AUTHORIZED SELLER.");
		}
		
		if(DAO.removeProduct(code)){
			return JSONParser.getSimpleResponse("OK");
		}else{
			return JSONParser.getSimpleResponse("ERROR");
		}
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String upFile(FormDataMultiPart multiPart) {
		System.out.println("marko");
		return JSONParser.getSimpleResponse("OK");
	}
}
