package ftn.ra122013.webshop.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import ftn.ra122013.webshop.beans.*;

@SuppressWarnings({ "unchecked" })
public class WebShopDAO {

	private static WebShopDAO instance;

	private static String usersPath = "Users.dat";
	private static String categoriesPath = "Categories.bin";
	private static String deliverersPath = "Deliverers.bin";
	private static String purchasesPath = "Purchases.bin";
	private static String storesPath = "Stores.bin";

	private File usersFile = new File(usersPath);
	private File catFile = new File(categoriesPath);
	private File delFile = new File(deliverersPath);
	private File purFile = new File(purchasesPath);
	private File storesFile = new File(storesPath);

	public static WebShopDAO getInstance() {
		if (instance == null) {
			instance = new WebShopDAO();
		}
		return instance;
	}

	private WebShopDAO() {
		try {
			if (!usersFile.exists()) {
				usersFile.createNewFile();
				ArrayList<User> users = new ArrayList<User>();
				users.add(new Administrator("admin", "admin", null, null, null, null, null, null));
				save(usersFile, users);
			}
			if (!catFile.exists()) {
				catFile.createNewFile();
				save(catFile, new CategoryTree());
			}
			if (!delFile.exists()) {
				delFile.createNewFile();
				save(delFile, new ArrayList<Deliverer>());
			}
			if (!purFile.exists()) {
				purFile.createNewFile();
				save(purFile, new ArrayList<Purchase>());
			}
			if (!storesFile.exists()) {
				storesFile.createNewFile();
				save(storesFile, new ArrayList<Store>());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void save(File file, Object collection) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(collection);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Object load(File file) {
		Object loaded = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			loaded = ois.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loaded;
	}

	/*** CATEGORIES ***/
	public boolean existCategory(String name) {
		CategoryTree categories = (CategoryTree) load(catFile);
		Category category = categories.findCategory(name);
		if (category == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean addCategory(Category category, String parentName) {
		CategoryTree categories = (CategoryTree) load(catFile);
		if (categories.add(parentName, category)) {
			save(catFile, categories);
			return true;
		} else {
			return false;
		}

	}

	public Category removeCategory(String categoryName) {
		CategoryTree categories = (CategoryTree) load(catFile);
		Category removed = categories.remove(categoryName);
		save(catFile, categories);
		return removed;
	}

	public void updateCategory(String categoryName, Category updatedCategory) {
		CategoryTree categories = (CategoryTree) load(catFile);
		Category category = categories.findCategory(categoryName);
		if (category != null) {
			category.setDescription(updatedCategory.getDescription());
			if (!category.getParent().equals(updatedCategory.getParent())) {
				// TODO prevezi kategoriju
			}
		}
		save(catFile, categories);
	}

	public Category getCategory(String name) {
		CategoryTree categories = (CategoryTree) load(catFile);
		return categories.findCategory(name);
	}

	public CategoryTree getAllCategories() {
		return (CategoryTree) load(catFile);
	}

	/*** USERS ***/
	public void addUser(User newUser) {
		ArrayList<User> users = (ArrayList<User>) load(usersFile);
		users.add(newUser);
		System.out.println("Registrovan novi korisnik: " + newUser);
		save(usersFile, users);
	}

	public void updateUser(String username, String password, String address, String country, String email, String name,
			String surname, String telephone) {
		ArrayList<User> users = (ArrayList<User>) load(usersFile);
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				user.setPassword(password);
				user.setAddress(address);
				user.setCountry(country);
				user.setEmail(email);
				user.setName(name);
				user.setSurname(surname);
				user.setTelephone(telephone);
			}
		}
		save(usersFile, users);
	}

	public boolean removeUser(String username) {
		ArrayList<User> users = (ArrayList<User>) load(usersFile);
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				users.remove(user);

				save(usersFile, users);
				return true;
			}
		}
		save(usersFile, users);
		return false;
	}

	public ArrayList<User> getAllUsers() {
		return (ArrayList<User>) load(usersFile);
	}

	public User getUser(String username) {
		ArrayList<User> users = (ArrayList<User>) load(usersFile);
		for (User user : users) {
			System.out.println(user.getUsername());
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public boolean existsUser(String username) {
		if (getUser(username) == null) {
			return false;
		} else {
			return true;
		}
	}

	/*** DELIVERERS ***/
	public String generateDelivererCode() {
		String uniqueID = null;
		do {
			uniqueID = UUID.randomUUID().toString();
		} while (existsDeliverer(uniqueID));
		return uniqueID;
	}

	public boolean existsDeliverer(String code) {
		ArrayList<Deliverer> deliverers = (ArrayList<Deliverer>) load(delFile);
		for (Deliverer del : deliverers) {
			if (del.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	public Deliverer getDeliverer(String code) {
		ArrayList<Deliverer> deliverers = (ArrayList<Deliverer>) load(delFile);
		for (Deliverer del : deliverers) {
			if (del.getCode().equals(code)) {
				return del;
			}
		}
		return null;
	}

	public ArrayList<Deliverer> getAllDeliverers() {
		return (ArrayList<Deliverer>) load(delFile);
	}

	public void addDeliverer(Deliverer newDeliverer) {
		ArrayList<Deliverer> deliverers = (ArrayList<Deliverer>) load(delFile);
		deliverers.add(newDeliverer);
		save(delFile, deliverers);
	}

	public boolean removeDeliverer(String code) {
		ArrayList<Deliverer> deliverers = (ArrayList<Deliverer>) load(delFile);
		for (Deliverer del : deliverers) {
			if (del.getCode().equals(code)) {
				deliverers.remove(del);

				save(delFile, deliverers);
				return true;
			}
		}
		return false;
	}

	public boolean updateDeliverer(String code, Deliverer updatedDel) {
		ArrayList<Deliverer> deliverers = (ArrayList<Deliverer>) load(delFile);
		for (Deliverer del : deliverers) {
			if (del.getCode().equals(code)) {
				int index = deliverers.indexOf(del);
				deliverers.set(index, updatedDel);

				save(delFile, deliverers);
				return true;
			}
		}
		return false;
	}

	/*** STORES ***/
	public String generateStoreCode() {
		String uniqueID = null;
		do {
			uniqueID = UUID.randomUUID().toString();
		} while (existsStore(uniqueID, null));
		return uniqueID;
	}

	public boolean existsStore(String code, String name) {
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		for (Store store : stores) {
			if (store.getCode().equals(code) || store.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public Store getStore(String code) {
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		for (Store store : stores) {
			if (store.getCode().equals(code)) {
				return store;
			}
		}
		return null;
	}

	public ArrayList<Store> getAllStores() {
		return (ArrayList<Store>) load(storesFile);
	}

	public boolean addStore(Store newStore) {
		if (existsStore(null, newStore.getName())) {
			return false;
		}
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		newStore.setCode(generateStoreCode());
		stores.add(newStore);
		save(storesFile, stores);
		return true;
	}

	public void updateStore(Store updatedStore) {
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		for (Store store : stores) {
			if (store.getCode().equals(updatedStore.getCode())) {
				store.setAddress(updatedStore.getAddress());
				store.setCountry(updatedStore.getCountry());
				store.setEmail(updatedStore.getEmail());
				store.setTelephone(updatedStore.getTelephone());
				store.setSeller(updatedStore.getSeller());

				save(storesFile, stores);
			}
		}
	}

	public boolean removeStore(String code) {
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		for (Store store : stores) {
			if (store.getCode().equals(code)) {
				stores.remove(store);

				save(storesFile, stores);
				return true;
			}
		}
		return false;
	}

	/*** PRODUCTS ***/
	public String generateProductCode() {
		String uniqueID = null;
		do {
			uniqueID = UUID.randomUUID().toString();
		} while (existsProduct(uniqueID));
		return uniqueID;
	}

	private boolean existsProduct(String code) {
		ArrayList<Product> products = getAllProducts();
		for (Product product : products) {
			if (product.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	public boolean addProduct(String storeCode, Product product) {
		product.setCode(generateProductCode());
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		for (Store store : stores) {
			if (store.getCode().equals(storeCode)) {
				store.addProduct(product);
				product.setStore(storeCode);

				save(storesFile, stores);
				return true;
			}
		}
		return false;
	}

	public boolean removeProduct(String productCode) {
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		for (Store store : stores) {
			for (Product product : store.getProducts()) {
				if (product.getCode().equals(productCode)) {
					store.removeProduct(product);

					save(storesFile, stores);
					return true;
				}
			}
		}
		return false;

	}

	public Product getProduct(String storeCode, String productCode) {
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		for (Store store : stores) {
			if (store.getCode().equals(storeCode)) {
				for (Product product : store.getProducts()) {
					if (product.getCode().equals(productCode)) {
						return product;
					}
				}
			}
		}
		return null;
	}

	public ArrayList<Product> getAllProducts() {
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		ArrayList<Product> products = new ArrayList<Product>();
		for (Store store : stores) {
			products.addAll(store.getProducts());
		}
		return products;
	}

	public boolean updateProduct(String storeCode, Product updatedProduct) {
		String productCode = updatedProduct.getCode();
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		for (Store store : stores) {
			if (store.getCode().equals(storeCode)) {
				ArrayList<Product> products = store.getProducts();
				for (Product product : products) {
					if (product.getCode().equals(productCode)) {
						product.setName(updatedProduct.getName());
						product.setDimension(updatedProduct.getDimension());
						product.setCountry(updatedProduct.getOriginCountry());
						product.setProducer(updatedProduct.getProducer());
						product.setUnitPrice(updatedProduct.getUnitPrice());
						product.setCategoryName(updatedProduct.getCategoryName());
						product.setQuantity(updatedProduct.getQuantity());

						save(storesFile, stores);
						return true;
					}
				}
			}
		}
		return false;
	}

	/*** PURCHASES ***/
	public String generatePurchaseCode() {
		String uniqueID = null;
		do {
			uniqueID = UUID.randomUUID().toString();
		} while (existsPurchase(uniqueID));
		return uniqueID;
	}

	private boolean existsPurchase(String code) {
		ArrayList<Purchase> purchases = getAllPurchases();
		for (Purchase purchase : purchases) {
			if (purchase.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	public boolean addPurchase(Purchase purchase) {
		ArrayList<Purchase> purchases = (ArrayList<Purchase>) load(purFile);
		for (Purchase p : purchases) {
			if (p.getCode().equals(purchase.getCode())) {
				return false;
			}
		}
		purchases.add(purchase);
		save(purFile, purchases);
		return true;
	}

	public ArrayList<Purchase> getAllPurchases() {
		return (ArrayList<Purchase>) load(purFile);
	}

	public boolean addComplaint(String purCode, String complaint) {
		ArrayList<Purchase> purchases = (ArrayList<Purchase>) load(purFile);
		for (Purchase purchase : purchases) {
			if (purchase.getCode().equals(purCode)) {
				purchase.setComplaint(complaint);

				save(purFile, purchases);
				return true;
			}
		}
		return false;
	}

	/*** REVIEWS ***/
	public String generateReviewCode() {
		String uniqueID = null;
		do {
			uniqueID = UUID.randomUUID().toString();
		} while (existsReview(uniqueID));
		return uniqueID;
	}

	public boolean existsReview(String code) {
		ArrayList<Review> reviews = getAllReviews();
		for (Review review : reviews) {
			if (review.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Review> getAllReviews() {
		ArrayList<Review> reviews = new ArrayList<Review>();
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		for (Store store : stores) {
			reviews.addAll(store.getReviews());
			for (Product product : store.getProducts()) {
				reviews.addAll(product.getReviews());
			}
		}
		return reviews;
	}

	public ArrayList<Review> getStoreReviews(String code) {
		ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
		for (Store store : stores) {
			if (store.getCode().equals(code)) {
				return store.getReviews();
			}
		}
		return null;
	}

	public ArrayList<Review> getProductReview(String code) {
		ArrayList<Product> products = getAllProducts();
		for (Product product : products) {
			if (product.getCode().equals(code)) {
				return product.getReviews();
			}
		}
		return null;
	}

	public boolean addReview(String code, Review review) {
		if (review instanceof StoreReview) {
			ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
			for (Store store : stores) {
				if (store.getCode().equals(code)) {
					store.addReview(review);

					save(storesFile, stores);
					return true;
				}
			}
		} else if (review instanceof ProductReview) {
			ArrayList<Store> stores = (ArrayList<Store>) load(storesFile);
			for (Store store : stores) {
				for (Product product : store.getProducts()) {
					if (product.getCode().equals(code)) {
						product.addReview(review);

						save(storesFile, stores);
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean removeReview(String code) {
		ArrayList<Store> stores = getAllStores();
		for (Store store : stores) {
			for (Review review : store.getReviews()) {
				if (review.getCode().equals(code)) {
					store.removeReview(code);

					save(storesFile, stores);
					return true;
				}
			}
			for (Product product : store.getProducts()) {
				for (Review review : product.getReviews()) {
					if (review.getCode().equals(code)) {
						product.removeReview(code);

						save(storesFile, stores);
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean editCommentReview(String code, String comment) {
		ArrayList<Store> stores = getAllStores();
		for (Store store : stores) {
			for (Review review : store.getReviews()) {
				if (review.getCode().equals(code)) {
					review.setComment(comment);
					review.setDate(new Date());

					save(storesFile, stores);
					return true;
				}
			}
			for (Product product : store.getProducts()) {
				for (Review review : product.getReviews()) {
					if (review.getCode().equals(code)) {
						review.setComment(comment);
						review.setDate(new Date());

						save(storesFile, stores);
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isAuthorizedSeller(User user, String storeCode) {
		if (!(user instanceof Seller)) {
			return false;
		}
		Store store = getStore(storeCode);
		if (store.getSeller().equals(user.getUsername())) {
			return true;
		}
		return false;
	}
}
