package ca.mcgill.ecse428.RentOrBuy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Entity;

@Entity
public class ApplicationUser {

	private Rob rob;
	private String username;
	private String password;
	private String fullname;
	private String address;
	private Cart cart;
	private List<Item> items;

	public ApplicationUser() {
		if (this.items == null) {
			this.items = new ArrayList<Item>();
		}
		rob.addCurrentExistingUser(this);
	}
	
	
	public ApplicationUser(String username, String password) {
		this.username = username;
		this.password = password;
		
		if (this.items == null) {
			this.items = new ArrayList<Item>();
		}
		rob.addCurrentExistingUser(this);
	}
	
	public ApplicationUser(String username, String password, String fullname, String address) {
		this.username = username;
		this.address = address; 
		this.password = password;
		this.fullname = fullname;
		
		if (this.items == null) {
			this.items = new ArrayList<Item>();
		}
		if(rob == null){
			rob = new Rob();
		}
		rob.addCurrentExistingUser(this);
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

	@OneToOne
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Rob getRob() {
		return rob;
	}

	public void setRob(Rob rob) {
		this.rob = rob;
	}


}
