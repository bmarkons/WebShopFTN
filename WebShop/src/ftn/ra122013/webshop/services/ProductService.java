package ftn.ra122013.webshop.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.*;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import ftn.ra122013.webshop.beans.Administrator;
import ftn.ra122013.webshop.beans.Buyer;
import ftn.ra122013.webshop.beans.Product;
import ftn.ra122013.webshop.beans.User;
import ftn.ra122013.webshop.dao.WebShopDAO;
import ftn.ra122013.webshop.json.Image;
import ftn.ra122013.webshop.json.JSONParser;

@Path("/product")
public class ProductService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	WebShopDAO DAO = WebShopDAO.getInstance();

	@POST
	@Path("/rate/{productCode}/{rate}")
	public String rateProduct(@PathParam("productCode") String productCode, @PathParam("rate") int rate) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Buyer)) {
			return JSONParser.getSimpleResponse("ERROR");
		}

		DAO.rateProduct(productCode, rate, (Buyer) user);

		return JSONParser.getSimpleResponse("OK");
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

		if (DAO.addProduct(storeCode, product, ctx)) {
			final Product p = product;
			Object retVal = new Object() {
				public String msg = "OK";
				public String code = p.getCode();
			};
			return JSONParser.toJSON(retVal);
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
	public String removeProduct(@PathParam("code") String code) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!(user instanceof Administrator) && !DAO.isAuthorizedSeller(user, code)) {
			return JSONParser.getSimpleResponse("ERORR. YOU ARE NOT ADMIN OR AUTHORIZED SELLER.");
		}

		if (DAO.removeProduct(code, ctx)) {
			return JSONParser.getSimpleResponse("OK");
		} else {
			return JSONParser.getSimpleResponse("ERROR");
		}
	}

	@POST
	@Path("/uploadImages")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String uploadImages(FormDataMultiPart multiPart) {
		// TODO: verify authorization

		// product code
		FormDataBodyPart codeField = multiPart.getField("code");
		String productCode = codeField.getValue();

		// image files
		List<FormDataBodyPart> fields = multiPart.getFields("imagefiles");
		ArrayList<InputStream> imageStreams = new ArrayList<InputStream>();
		for (FormDataBodyPart field : fields) {
			InputStream is = field.getValueAs(InputStream.class);
			imageStreams.add(is);
		}

		// save image files
		saveFiles(imageStreams, productCode);

		return JSONParser.getSimpleResponse("OK");
	}

	@POST
	@Path("/setVideoUrl")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String uploadFile(FormDataMultiPart multiPart) {
		// TODO: verify authorization
		// product code
		FormDataBodyPart codeField = multiPart.getField("code");
		String productCode = codeField.getValue();

		// video file
		FormDataBodyPart videoField = multiPart.getField("videoUrl");
		String videoUrl = null;
		if (videoField != null) {
			videoUrl = videoField.getValue();
		}

		// set video url
		Product product = DAO.getProduct(productCode);
		product.setVideoUrl(videoUrl);

		return JSONParser.getSimpleResponse("OK");
	}

	@GET
	@Path("/getmedia/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getImages(@PathParam("code") String code) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return JSONParser.getSimpleResponse("ERROR");
		}

		HashMap<String, String> imgMap = new HashMap<String, String>();

		File folder = new File(ctx.getRealPath("/media/" + code));
		File[] mediaFiles = folder.listFiles();
		if (mediaFiles == null) {
			return "";
		}
		for (File media : mediaFiles) {
			if (media.getName().contains(".image")) {
				try {
					String imgBase64 = Base64.encodeBase64String(Files.readAllBytes(media.toPath()));
					imgMap.put(media.getName(), imgBase64);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		ArrayList<Image> imgs = new ArrayList<Image>();
		for (String name : imgMap.keySet()) {
			Image i = new Image();
			i.name = name;
			i.data = imgMap.get(name);
			imgs.add(i);
		}
		final ArrayList<Image> slike = imgs;
		Object retVal = new Object() {
			public ArrayList<Image> images = slike;
		};

		return JSONParser.toJSON(retVal);
	}

	@DELETE
	@Path("/removeimg/{code}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeImage(@PathParam("code") String code, @PathParam("name") String name) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Product product = DAO.getProduct(code);
		String storeCode = product.getStore().getCode();
		if (!(user instanceof Administrator) && !DAO.isAuthorizedSeller(user, storeCode)) {
			return JSONParser.getSimpleResponse("ERROR");
		}

		if (product.removeImage(name, ctx)) {
			return JSONParser.getSimpleResponse("Image '" + name + "' removed.");
		} else {
			return JSONParser.getSimpleResponse("not removed");
		}
	}

	private void saveFiles(ArrayList<InputStream> imageStreams, String productCode) {
		Product product = DAO.getProduct(productCode);
		if (imageStreams.isEmpty() || product == null) {
			return;
		}

		for (InputStream is : imageStreams) {
			product.addImage(is, ctx);
		}
	}

}
