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
public class ItemInCartService {
	
	@Autowired
	private ItemInCartRepository itemInCartRepository;
	
	@Transactional
	public ItemInCart createItemInCart(Item item, int quantity, String size) {
		
		ItemInCart itemInCart = null;
		
		if (item == null) {
			throw new IllegalArgumentException("Must specify an item");
		}
		if (quantity <= 0) {
			throw new IllegalArgumentException("Item in cart must have a quantity above 0");
		}
		if (size==null || size.isEmpty()) {
			throw new IllegalArgumentException("Item size can't be empty");
		}
		
		itemInCart = new ItemInCart();
		itemInCart.setItem(item);
		itemInCart.setQuantity(quantity);
		itemInCart.setSize(size);
		itemInCartRepository.save(itemInCart);
		return itemInCart;
	}

	@Transactional
	public ItemInCart getItemInCart(Integer itemInCartId){
		ItemInCart aIIC = itemInCartRepository.findItemInCartByItemInCartId(itemInCartId);
		return aIIC;
	}

	@Transactional
	public List<ItemInCart> getAllItemsInCart(){
		return (List<ItemInCart>) itemInCartRepository.findAll();
	}

	@Transactional
	public ItemInCart deleteItem(ItemInCart aIIC){
		itemInCartRepository.delete(aIIC);
		aIIC = null;
		return aIIC;
	}
}
