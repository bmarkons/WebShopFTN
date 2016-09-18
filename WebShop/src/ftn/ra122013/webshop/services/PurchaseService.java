package ftn.ra122013.webshop.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ftn.ra122013.webshop.beans.Administrator;
import ftn.ra122013.webshop.beans.Buyer;
import ftn.ra122013.webshop.beans.Product;
import ftn.ra122013.webshop.beans.Purchase;
import ftn.ra122013.webshop.beans.Seller;
import ftn.ra122013.webshop.beans.User;
import ftn.ra122013.webshop.dao.WebShopDAO;
import ftn.ra122013.webshop.json.JSONParser;

@Path("/purchase")
public class PurchaseService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	WebShopDAO DAO = WebShopDAO.getInstance();

	@PUT
	@Path("/add/{delivererCode}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String purchase(ArrayList<Product> products, @PathParam("delivererCode") String delivererCode) {
		// TODO check permission
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return JSONParser.getSimpleResponse("ERROR");
		}

		final ArrayList<Purchase> purchasess = makePurchases(products, delivererCode, (Buyer) user);

		Object retVal = new Object() {
			public String msg = "OK";
			public ArrayList<Purchase> purchases = purchasess;
		};

		return JSONParser.toJSON(retVal);
	}

	private ArrayList<Purchase> makePurchases(ArrayList<Product> products, String delivererCode, Buyer buyer) {
		HashMap<String, ArrayList<Product>> map = new HashMap<String, ArrayList<Product>>();
		for (Product product : products) {
			String storeCode = product.getStore();
			if (map.containsKey(storeCode)) {
				map.get(storeCode).add(product);
			} else {
				ArrayList<Product> storeProducts = new ArrayList<Product>();
				storeProducts.add(product);
				map.put(storeCode, storeProducts);
			}
		}
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		for (String storeCode : map.keySet()) {
			ArrayList<Product> boughtProducts = reduceQuantity(map.get(storeCode));
			if (boughtProducts.size() == 0)
				continue;

			Purchase purchase = new Purchase();
			purchase.setCode(DAO.generatePurchaseCode());
			purchase.setBuyer(buyer);
			purchase.setDeliverer(DAO.getDeliverer(delivererCode));
			purchase.setProducts(boughtProducts);
			purchase.setStore(DAO.getStore(storeCode));
			purchase.setProducts(map.get(storeCode));
			purchase.setTotalPrice(totalPrice(map.get(storeCode)));
			DAO.addPurchase(purchase);
			purchases.add(purchase);
		}
		return purchases;
	}

	private ArrayList<Product> reduceQuantity(ArrayList<Product> products) {
		for (Object product : products.toArray()) {
			if (!DAO.reduceQuantity((Product) product)) {
				products.remove(product);
			}
		}
		return products;
	}

	private double totalPrice(ArrayList<Product> products) {
		double totalPrice = 0;
		for (Product product : products) {
			totalPrice += product.getUnitPrice();
		}
		return totalPrice;
	}

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllPurchases() {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return JSONParser.getSimpleResponse("ERROR");
		}
		
		ArrayList<Purchase> purchases = null;
		if(user instanceof Administrator){
			purchases = DAO.getAllPurchases();
		}else if(user instanceof Buyer){
			ArrayList<Purchase> allPurchases = DAO.getAllPurchases();
			purchases = new ArrayList<Purchase>();
			for(Purchase purchase : allPurchases){
				if(purchase.getBuyer().getUsername().equals(user.getUsername())){
					purchases.add(purchase);
				}
			}
		}else if(user instanceof Seller){
			ArrayList<Purchase> allPurchases = DAO.getAllPurchases();
			purchases = new ArrayList<Purchase>();
			for(Purchase purchase : allPurchases){
				if(purchase.getStore().getSeller().equals(user.getUsername())){
					purchases.add(purchase);
				}
			}
		}
		
		return JSONParser.toJSON(purchases);
	}
}
