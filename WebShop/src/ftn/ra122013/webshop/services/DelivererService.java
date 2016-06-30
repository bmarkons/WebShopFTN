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

	WebShopDAO DAO = WebShopDAO.getInstance();

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

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
	public String addDeliverer(String delJson) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Administrator)) {
			return JSONParser.getSimpleResponse("ERROR");
		}
		// if(deliverer == null){
		// return JSONParser.getSimpleResponse("ERROR");
		// }
		// deliverer.setCode(DAO.generateDelivererCode());
		// DAO.addDeliverer(deliverer);
		// try {
		// JSONObject obj = new JSONObject(deliverer);
		// System.out.println(obj.getString("name"));
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		System.out.println(delJson);
		String name = null;
		String description = null;
		ArrayList<String> countries = null;
		ArrayList<Tariff> tariffs = null;
		try {
			JSONObject json = new JSONObject(delJson);
			name = json.getString("name");
			description = json.getString("description");
			countries = new ArrayList<String>();
			JSONArray countriesJson = json.getJSONArray("countries");
			for (int i = 0; i < countriesJson.length(); i++) {
				countries.add(countriesJson.getString(i));
			}
			tariffs = new ArrayList<Tariff>();
			JSONArray tariffsJson = json.getJSONArray("tariffs");
			for (int i = 0; i < tariffsJson.length(); i++) {
				JSONObject tariffJson = tariffsJson.getJSONObject(i);
				double minDimension = tariffJson.getDouble("minDimension");
				double maxDimension = tariffJson.getDouble("maxDimension");
				double minWeight = tariffJson.getDouble("minWeight");
				double maxWeight = tariffJson.getDouble("maxWeight");
				double price = tariffJson.getDouble("price");
				Tariff tariff = new Tariff(minDimension, maxDimension, minWeight, maxWeight, price);
				tariffs.add(tariff);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return JSONParser.getSimpleResponse("ERROR");
		}

		String code = DAO.generateDelivererCode();
		Deliverer deliverer = new Deliverer();
		deliverer.setName(name);
		deliverer.setCode(code);
		deliverer.setDescription(description);
		deliverer.setCountries(countries);
		deliverer.setTariffs(tariffs);
		DAO.addDeliverer(deliverer);
		
		return JSONParser.getSimpleResponse("OK");
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateDeliverer(String delJson) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Administrator)) {
			return JSONParser.getSimpleResponse("ERROR");
		}
		System.out.println(delJson);
		String name = null;
		String description = null;
		ArrayList<String> countries = null;
		ArrayList<Tariff> tariffs = null;
		String code = null;
		try {
			JSONObject json = new JSONObject(delJson);
			code = json.getString("code");
			name = json.getString("name");
			description = json.getString("description");
			countries = new ArrayList<String>();
			JSONArray countriesJson = json.getJSONArray("countries");
			for (int i = 0; i < countriesJson.length(); i++) {
				countries.add(countriesJson.getString(i));
			}
			tariffs = new ArrayList<Tariff>();
			JSONArray tariffsJson = json.getJSONArray("tariffs");
			for (int i = 0; i < tariffsJson.length(); i++) {
				JSONObject tariffJson = tariffsJson.getJSONObject(i);
				double minDimension = tariffJson.getDouble("minDimension");
				double maxDimension = tariffJson.getDouble("maxDimension");
				double minWeight = tariffJson.getDouble("minWeight");
				double maxWeight = tariffJson.getDouble("maxWeight");
				double price = tariffJson.getDouble("price");
				Tariff tariff = new Tariff(minDimension, maxDimension, minWeight, maxWeight, price);
				tariffs.add(tariff);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return JSONParser.getSimpleResponse("ERROR");
		}

		Deliverer deliverer = new Deliverer();
		deliverer.setCode(code);
		deliverer.setName(name);
		deliverer.setDescription(description);
		deliverer.setCountries(countries);
		deliverer.setTariffs(tariffs);
		
		if (DAO.updateDeliverer(deliverer.getCode(), deliverer)) {
			return JSONParser.getSimpleResponse("OK");
		} else {
			return JSONParser.getSimpleResponse("ERROR");
		}

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
