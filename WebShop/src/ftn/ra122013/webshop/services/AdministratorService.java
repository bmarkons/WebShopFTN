package ftn.ra122013.webshop.services;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


import ftn.ra122013.webshop.json.JSONParser;

@Path("/admin")
public class AdministratorService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Hello Admin";
	}
	
	@GET
	@Path("/getCountries")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCountries(){
		ArrayList<String> countries = new ArrayList<String>();
		countries.add("Serbia");
		countries.add("USA");
		return JSONParser.toJSON(countries);
	}
}
