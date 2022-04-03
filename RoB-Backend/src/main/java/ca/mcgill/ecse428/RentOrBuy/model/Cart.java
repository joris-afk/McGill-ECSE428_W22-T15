package ca.mcgill.ecse428.RentOrBuy.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ca.mcgill.ecse428.RentOrBuy.RobApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Cart {
	
	private List<ItemInCart> cartItems;
	private Integer cartId;
	

	
	public Cart() {
		if (this.cartItems == null) {
			this.cartItems = new ArrayList<ItemInCart>();
		}
		
		//assign a random id for item
		Random rand=new Random();
		int int_random=rand.nextInt(3000);
		Rob rob=RobApplication.getRob();
		List<Integer> cartid=new ArrayList<Integer>();
		for (Cart cart: rob.getCarts()) {
			cartid.add(cart.getCartId());
		}
		
		while (cartid.contains(int_random)){
			int_random=rand.nextInt(3000);
		}
		cartId=int_random;
		rob.addCart(this);
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
		if (cartItems != null) this.cartItems = cartItems;
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

		//get random id for iic
		Random rand=new Random();
		int int_random=rand.nextInt(3000);
		Rob rob=RobApplication.getRob();
		if (rob.getIICs()==null){
			
		}
		List<Integer> iicID=new ArrayList<Integer>();
		for (ItemInCart aIIC: rob.getIICs()) {
			iicID.add(aIIC.getItemInCartId());
		}

		while (iicID.contains(int_random)){
			int_random=rand.nextInt(3000);
		}
		itemInCart.setItemInCartId(int_random);

		this.cartItems.add(itemInCart);
	}
	
}
