package ftn.ra122013.webshop.services;

import java.util.ArrayList;

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

import org.apache.tomcat.util.codec.binary.StringUtils;

import ftn.ra122013.webshop.beans.Administrator;
import ftn.ra122013.webshop.beans.Buyer;
import ftn.ra122013.webshop.beans.Seller;
import ftn.ra122013.webshop.beans.Store;
import ftn.ra122013.webshop.beans.User;
import ftn.ra122013.webshop.dao.WebShopDAO;
import ftn.ra122013.webshop.json.JSONParser;

@Path("/store")
public class StoreService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	WebShopDAO DAO = WebShopDAO.getInstance();

	@POST
	@Path("/rate/{storeCode}/{rate}")
	public String rateProduct(@PathParam("storeCode") String storeCode, @PathParam("rate") int rate) {
		// check permission
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		DAO.rateStore(storeCode, rate, (Buyer) user);

		return JSONParser.getSimpleResponse("OK");
	}

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllStores() {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			return JSONParser.getSimpleResponse("You must log in first.");
		}
		ArrayList<Store> stores = DAO.getAllStores();
		return JSONParser.toJSON(stores);
	}

	@PUT
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addstore(Store store) {
		HttpSession session = request.getSession();
		if (!(session.getAttribute("user") instanceof Administrator)) {
			return JSONParser.getSimpleResponse("ERORR. NOT ADMIN");
		}
		if (store.getName() == null || store.getName().equals("")) {
			return JSONParser.getSimpleResponse("ERROR");
		}
		if (DAO.addStore(store)) {
			System.out.println("Store added");
			return JSONParser.getSimpleResponse("OK");
		} else {
			return JSONParser.getSimpleResponse("ERROR");
		}
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateStore(Store store) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Administrator) && !DAO.isAuthorizedSeller(user, store.getCode())) {
			return JSONParser.getSimpleResponse("ERORR. YOU ARE NOT ADMIN OR AUTHORIZED SELLER.");
		}

		DAO.updateStore(store);
		return JSONParser.getSimpleResponse("OK");
	}

	@DELETE
	@Path("/remove/{code}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String removeStore(@PathParam("code") String code) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Administrator) && !DAO.isAuthorizedSeller(user, code)) {
			return JSONParser.getSimpleResponse("ERORR. YOU ARE NOT ADMIN OR AUTHORIZED SELLER.");
		}
		if (DAO.removeStore(code)) {
			return JSONParser.getSimpleResponse("Store '" + code + "' has been removed.");
		} else {
			return JSONParser.getSimpleResponse("ERROR");
		}
	}

	@POST
	@Path("/rate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void rateStore() {

	}

}
