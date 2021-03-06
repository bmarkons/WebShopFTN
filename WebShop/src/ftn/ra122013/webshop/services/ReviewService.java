package ftn.ra122013.webshop.services;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ftn.ra122013.webshop.beans.Buyer;
import ftn.ra122013.webshop.beans.Product;
import ftn.ra122013.webshop.beans.ProductReview;
import ftn.ra122013.webshop.beans.Review;
import ftn.ra122013.webshop.beans.StoreReview;
import ftn.ra122013.webshop.beans.User;
import ftn.ra122013.webshop.dao.WebShopDAO;
import ftn.ra122013.webshop.json.JSONParser;

@Path("/review")
public class ReviewService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	WebShopDAO DAO = WebShopDAO.getInstance();

	@PUT
	@Path("/store/add/{storeCode}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addStoreReview(StoreReview review, @PathParam("storeCode") String storeCode) {
		// TODO check access
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return JSONParser.getSimpleResponse("ERROR. Not logged in");
		}

		review.setCode(DAO.generateReviewCode());
		review.setBuyer((Buyer) user);
		review.setDate(new Date());
		final Review r = review;
		Object retVal = new Object() {
			public String msg = "OK";
			public String code = r.getCode();
			public Date date = r.getDate();
			public Buyer buyer = r.getBuyer();
		};
		String okMsg = JSONParser.toJSON(retVal);
		String errorMsg = JSONParser.getSimpleResponse("ERROR");
		return DAO.addReview(storeCode, review) ? okMsg : errorMsg;
	}

	@PUT
	@Path("/product/add/{productCode}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addProductReview(ProductReview review, @PathParam("productCode") String productCode) {
		// TODO check access
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return JSONParser.getSimpleResponse("ERROR. Not logged in");
		}

		review.setCode(DAO.generateReviewCode());
		review.setBuyer((Buyer) user);
		review.setDate(new Date());
		final Review r = review;
		Object retVal = new Object() {
			public String msg = "OK";
			public String code = r.getCode();
			public Date date = r.getDate();
			public Buyer buyer = r.getBuyer();
		};
		String okMsg = JSONParser.toJSON(retVal);
		String errorMsg = JSONParser.getSimpleResponse("ERROR");
		return DAO.addReview(productCode, review) ? okMsg : errorMsg;
	}

	@POST
	@Path("/update/{code}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateReview(String editedComment, @PathParam("code") String code) {
		// TODO check access
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return JSONParser.getSimpleResponse("ERROR. Not logged in");
		}

		String msg = DAO.editCommentReview(code, editedComment) ? "OK" : "ERROR";
		return JSONParser.getSimpleResponse(msg);
	}

	@DELETE
	@Path("/delete/{code}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteStoreReview(@PathParam("code") String code) {
		// TODO check access
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return JSONParser.getSimpleResponse("ERROR. Not logged in");
		}
		
		String msg = DAO.removeReview(code) ? "OK" : "ERROR";
		return JSONParser.getSimpleResponse(msg);
	}

	@PUT
	@Path("/addproduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void addProductReview() {

	}

	@POST
	@Path("/updateproduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void updateProductReview() {

	}

	@DELETE
	@Path("/deleteproduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void deleteProductReview() {

	}

	@POST
	@Path("/rate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void rateReview() {

	}
}
