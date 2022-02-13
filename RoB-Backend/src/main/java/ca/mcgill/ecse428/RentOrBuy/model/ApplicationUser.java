package ca.mcgill.ecse428.RentOrBuy.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class ApplicationUser {

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
	}

	@Id
	public String getUserame() {
		return this.username;
	}

	public void setUsername(String newUsername) {
		this.username = newUsername;
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

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}


}
