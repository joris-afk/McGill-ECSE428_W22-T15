package ca.mcgill.ecse428.RentOrBuy.model;

import java.util.List;
import java.util.ArrayList;

public class Rob {
	
	private List<ApplicationUser> currentLoggedInUsers;
	private List<ApplicationUser> existingUsers;
	private List<Item> products;
	private List<Cart> carts;
	private ArrayList<Reservation> reservations;
	private List<Purchase> purchases;
	private List<ItemInCart> itemsInCart;
	private List<PurchaseHistory> purchaseHistories;
	
    public Rob() {
    	this.currentLoggedInUsers = new ArrayList<ApplicationUser>();
    	this.existingUsers = new ArrayList<ApplicationUser>();
    	this.products = new ArrayList<Item>();
    	this.carts = new ArrayList<Cart>();
		this.reservations = new ArrayList<Reservation>();
		this.purchases = new ArrayList<Purchase>();
		this.purchaseHistories = new ArrayList<PurchaseHistory>();
    }

    public List<PurchaseHistory> getPurchaseHistories(){
		return this.purchaseHistories;
	}

	public void addPurchaseHistory(PurchaseHistory aPurchaseHistory){
		this.purchaseHistories.add(aPurchaseHistory);
	}

	public void removeAPurchaseHistory(PurchaseHistory aPurchaseHistory){
		this.purchaseHistories.remove(aPurchaseHistory);
	}
	
    public List<Cart> getCarts(){
    	if (carts==null) {
    		carts=new ArrayList<Cart>();
    	}
    	return carts;
    }
    
    public void addCart(Cart cart) {
    	carts.add(cart);
    }
    
	public List<ApplicationUser> getCurrentLoggedInUsers() {
		if (currentLoggedInUsers==null){
			currentLoggedInUsers=new ArrayList<ApplicationUser>();
		}
		return currentLoggedInUsers;
	}
	
	public void addCurrentLoggedInUser(ApplicationUser applicationUser) {
		if (currentLoggedInUsers==null){
			currentLoggedInUsers=new ArrayList<ApplicationUser>();
		}
		this.currentLoggedInUsers.add(applicationUser);
	}
	
	public void removeCurrentLoggedInUser(ApplicationUser applicationUser) {
		this.currentLoggedInUsers.remove(applicationUser);
	}
	
	public void setCurrentLoggedInUsers(List<ApplicationUser> currentLoggedInUsers) {
		this.currentLoggedInUsers = currentLoggedInUsers;
	}

	public List<ApplicationUser> getExistingUsers() {
		if (existingUsers==null){
			existingUsers=new ArrayList<ApplicationUser>();
		}
		return existingUsers;
	}

	public void setExistingUsers(List<ApplicationUser> existingUsers) {
		this.existingUsers = existingUsers;
	}
	
	public void addCurrentExistingUser(ApplicationUser applicationUser) {
		if (existingUsers==null){
			existingUsers=new ArrayList<ApplicationUser>();
		}
		this.existingUsers.add(applicationUser);
	}
	
	public ApplicationUser logInUser(String user, String pass) {
		for (ApplicationUser u : existingUsers) {
			if (u.getPassword().equals(pass) && u.getUsername().equals(user)) {
				currentLoggedInUsers.add(u);
				return u;
			}
		}
		return null;
	}

	public Item addProduct(Item aProduct){
		if (this.products == null) this.products = new ArrayList<Item>();
		this.products.add(aProduct);
		return aProduct;
	}

	public void deleteExistingProduct(Item aProduct){
		this.products.remove(aProduct);
	}

	public List<Item> getProducts(){
		if (this.products == null) this.products = new ArrayList<Item>();
		return products;
	}
	
	public List<Purchase> getPurchases(){
		return this.purchases;
	}

	public void addPurchase(Purchase newPurchase){
		this.purchases.add(newPurchase);
	}
	
	public void delete() {
		existingUsers = null;
		currentLoggedInUsers = null;
		products=null;
	}

	public List<Reservation> getReservations(){
		if (this.reservations==null){
			this.reservations=new ArrayList<Reservation>();
		}
		return this.reservations;
	}

	public void addReservation(Reservation newReservation){
		this.reservations.add(newReservation);
	}
	

	public List<ItemInCart> getIICs(){
		if (this.itemsInCart==null){
			this.itemsInCart=new ArrayList<ItemInCart>();
		}
		return this.itemsInCart;
	}

	public void addIIC(ItemInCart aIIC){
		this.itemsInCart.add(aIIC);
	}

	public void removeIIC(ItemInCart aIIC){
		this.itemsInCart.remove(aIIC);
	}


}
