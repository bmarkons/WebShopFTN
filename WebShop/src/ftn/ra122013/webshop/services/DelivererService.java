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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ftn.ra122013.webshop.beans.Administrator;
import ftn.ra122013.webshop.beans.Deliverer;
import ftn.ra122013.webshop.beans.Tariff;
import ftn.ra122013.webshop.beans.User;
import ftn.ra122013.webshop.dao.WebShopDAO;
import ftn.ra122013.webshop.json.JSONParser;

@Path("/deliverer")
public class DelivererService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	WebShopDAO DAO = WebShopDAO.getInstance();

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllDeliverers() {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			return JSONParser.getSimpleResponse("ERROR. Not logged in");
		}
		ArrayList<Deliverer> dels = DAO.getAllDeliverers();
		return JSONParser.toJSON(dels);
	}

	@PUT
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addDeliverer(Deliverer deliverer) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Administrator)) {
			return JSONParser.getSimpleResponse("ERROR");
		}

		if (deliverer.getName() == null || deliverer.getName().isEmpty()) {
			return JSONParser.getSimpleResponse("ERROR");
		}
		if (deliverer.getDimensionRatio() == -1 || deliverer.getWeightRatio() == -1 || deliverer.getMinPrice() == -1) {
			return JSONParser.getSimpleResponse("ERROR");
		}

		deliverer.setCode(DAO.generateDelivererCode());
		DAO.addDeliverer(deliverer);

		return JSONParser.getSimpleResponse("OK");
	}

	@POST
	@Path("/update/{delivererCode}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateDeliverer(Deliverer deliverer, @PathParam("delivererCode") String delivererCode) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Administrator)) {
			return JSONParser.getSimpleResponse("ERROR");
		}

		if (deliverer.getName() == null || deliverer.getName().isEmpty()) {
			return JSONParser.getSimpleResponse("ERROR");
		}
		if (deliverer.getDimensionRatio() == -1 || deliverer.getMinPrice() == -1 || deliverer.getWeightRatio() == -1) {
			return JSONParser.getSimpleResponse("ERROR");
		}

		DAO.updateDeliverer(delivererCode, deliverer);

		return JSONParser.getSimpleResponse("OK");

	}

	@DELETE
	@Path("/remove/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteDeliverer(@PathParam("code") String code) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Administrator)) {
			return JSONParser.getSimpleResponse("ERROR");
		}
		if (DAO.removeDeliverer(code)) {
			return JSONParser.getSimpleResponse("OK");
		} else {
			return JSONParser.getSimpleResponse("ERROR");
		}
	}
}
