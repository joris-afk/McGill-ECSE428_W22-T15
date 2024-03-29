package ca.mcgill.ecse428.RentOrBuy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

@Entity
public class ApplicationUser {

	//private Rob rob;
	private String username;
	private String password;
	private String fullname;
	private String address;
	private Cart cart;
	private List<Item> items;
	private List<Reservation> myReservations;
	private PurchaseHistory purchases;

	public ApplicationUser() {
		if (this.items == null) {
			this.items = new ArrayList<Item>();
		}
		if (this.myReservations == null) {
			this.myReservations = new ArrayList<Reservation>();
		}
		//rob.addCurrentExistingUser(this);
	}
	
	
	public ApplicationUser(String username, String password) {
		this.username = username;
		this.password = password;
		
		if (this.items == null) {
			this.items = new ArrayList<Item>();
		}
		if (this.myReservations == null) {
			this.myReservations = new ArrayList<Reservation>();
		}
		this.purchases = new PurchaseHistory(username+"'s history");
		//rob.addCurrentExistingUser(this);
	}
	
	public ApplicationUser(String username, String password, String fullname, String address) {
		this.username = username;
		this.address = address; 
		this.password = password;
		this.fullname = fullname;
		
		if (this.items == null) {
			this.items = new ArrayList<Item>();
		}
		if (this.myReservations == null) {
			this.myReservations = new ArrayList<Reservation>();
		}
		this.purchases = new PurchaseHistory(username+"'s history");
		//if(rob == null){
		//rob = new Rob();
		//}
		//rob.addCurrentExistingUser(this);
	}
	
	@Id
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@OneToMany
	public List<Item> getItems() {
		if (items==null) {
			items= new ArrayList<Item>();
		}
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void addItem(Item item) {
		this.items.add(item);
	}

	public void removeItem(Item item) {
		this.items.remove(item);
	}

	@OneToOne(cascade = {CascadeType.ALL})

	public Cart getCart() {
		if (cart==null){
			cart=new Cart();
		}
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public void addToCart(ItemInCart iic){
		getCart().addItemToCart(iic);
	}

	@OneToMany
	public List<Reservation> getReservations(){
		if (myReservations==null){
			myReservations=new ArrayList<Reservation>();
		}
		return myReservations;
	}

	public void setReservations(List<Reservation> reservations){
		this.myReservations=reservations;
	}

	public void addReservation(Reservation areservation){
		this.myReservations.add(areservation);
	}

	public void removeReservation(Reservation reservation){
		this.myReservations.remove(reservation);
	}

	@OneToOne
	public PurchaseHistory getPurchases(){
		if(this.purchases == null){
			this.purchases = new PurchaseHistory(username+"'s history");
		}
		return this.purchases;
	}

	public void addPurchase(Purchase newPurchase){
		if(this.purchases == null){
			this.purchases = new PurchaseHistory(username+"'s history");
		}
		this.purchases.addPurchase(newPurchase);
	}

	public void removePurchase(Purchase purchase){
		this.purchases.removePurchase(purchase);
	}

	public void setPurchases(PurchaseHistory newPurchases){
		this.purchases = newPurchases;
	}


	/*
	public Rob getRob() {
		return rob;
	}

	public void setRob(Rob rob) {
		this.rob = rob;
	}
*/

}
