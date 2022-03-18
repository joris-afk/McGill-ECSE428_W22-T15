package ca.mcgill.ecse428.RentOrBuy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.RentOrBuy.dao.*;
import ca.mcgill.ecse428.RentOrBuy.model.*;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Transactional
	public Cart createCart(List<ItemInCart> cartItems) {
		Cart cart = null;
		//if cartItems is null, then Cart initializes an empty list of items for an empty cart
		//if cartItems is empty, then Cart is empty
		cart = new Cart();
		cart.setCartItems(cartItems);
		cartRepository.save(cart);
		return cart;
	}

	
	public Cart getCartByCartId(Integer cartId){
		return cartRepository.findCartByCartId(cartId);
	}
}
