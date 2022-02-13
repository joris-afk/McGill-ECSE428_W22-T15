package ca.mcgill.ecse428.RentOrBuy.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Cart {
	
	private List<ItemInCart> cartItems;
	private Integer cartId;
	
	public Cart() {
		if (this.cartItems.equals(null)) {
			this.cartItems = new ArrayList<ItemInCart>();
		}
	}
	
	@Id
	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	@OneToMany
	public List<ItemInCart> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<ItemInCart> cartItems) {
		this.cartItems = cartItems;
	}
	
	public void addItemToCart(ItemInCart itemInCart) {
		this.cartItems.add(itemInCart);
	}
	
	public void removeItemFromCart(ItemInCart itemInCart) {
		this.cartItems.remove(itemInCart);
	}
	
	public void addItemToCart(Item item, int quantity, String size) {
		ItemInCart itemInCart = new ItemInCart();
		itemInCart.setItem(item);
		itemInCart.setQuantity(quantity);
		itemInCart.setSize(size);
		this.cartItems.add(itemInCart);
	}
	
}
