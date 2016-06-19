package ftn.ra122013.webshop.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloService {
	
	@GET
	@Path("/")
	public String sayHello(){
		return "Hello World from REST";
	}
}
