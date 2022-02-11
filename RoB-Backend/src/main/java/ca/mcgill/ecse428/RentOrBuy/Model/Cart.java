package ca.mcgill.ecse428.RentOrBuy.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

public class Cart {
	private List<ItemInCart> cartItems;
	
	public Cart() {}

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
