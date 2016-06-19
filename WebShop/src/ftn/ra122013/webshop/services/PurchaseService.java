package ftn.ra122013.webshop.services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/purchase")
public class PurchaseService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@PUT
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void purchase() {

	}
	
	@DELETE
	@Path("/delete")
	public void deletePurchase(){
		
	}
}
