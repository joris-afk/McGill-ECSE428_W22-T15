package ca.mcgill.ecse428.RentOrBuy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse428.RentOrBuy.dao.*;
import ca.mcgill.ecse428.RentOrBuy.model.*;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ItemInCartRepository IICRepository;
	
	@Transactional
	public Cart createCart(int cartId, List<ItemInCart> cartItems) {
		Cart cart = null;
		//if cartItems is null, then Cart initializes an empty list of items for an empty cart
		//if cartItems is empty, then Cart is empty
		cart = new Cart();
		cart.setCartId(cartId);
		if (cartItems == null) {
			cartItems = new ArrayList<ItemInCart>();
		}
		cart.setCartItems(cartItems);
		cartRepository.save(cart);
		return cart;
	}

	@Transactional
	public Cart addNewItemToCart(Cart cart, Item item, int quantity, String size){
		ItemInCart IIC = new ItemInCart();

		if (quantity<1){
			throw new IllegalArgumentException("You must order at least one of the product");
		}
		boolean invalidSize = true;
		for (String sizes:item.getAvailableSizes()){
			if (sizes.equals(size)) invalidSize = false;
		}
		if (invalidSize){
			throw new IllegalArgumentException("Unavailable size for this product");
		}
		IIC.setItem(item);
		IIC.setQuantity(quantity);
		IIC.setSize(size);

		//randomly assign a IIC-ID
		Random rand=new Random();
		int potentialID=rand.nextInt(3000);
		while(IICRepository.findItemInCartByItemInCartId(potentialID)!=null){
			potentialID=rand.nextInt(3000);
		}
		IIC.setItemInCartId(potentialID);
		IICRepository.save(IIC);
		return addItemToCart(cart, IIC);
	}
	
	@Transactional
	public Cart addItemToCart(Cart cart, ItemInCart item) {
		cart.addItemToCart(item);
		cartRepository.save(cart);
		return cart;
	}
	
	@Transactional
	public Cart removeItemFromCart(Cart cart, ItemInCart item) {
		cart.removeItemFromCart(item);
		cartRepository.save(cart);
		return cart;
	}
	
	@Transactional
	public Cart getCartByCartId(Integer cartId){
		return cartRepository.findCartByCartId(cartId);
	}
}
