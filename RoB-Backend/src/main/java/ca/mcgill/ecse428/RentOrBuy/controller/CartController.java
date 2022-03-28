package ca.mcgill.ecse428.RentOrBuy.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;
import ca.mcgill.ecse428.RentOrBuy.dto.CartDto;
import ca.mcgill.ecse428.RentOrBuy.dto.ItemDto;
import ca.mcgill.ecse428.RentOrBuy.dto.ItemInCartDto;
import ca.mcgill.ecse428.RentOrBuy.model.Cart;
import ca.mcgill.ecse428.RentOrBuy.model.Item;
import ca.mcgill.ecse428.RentOrBuy.model.ItemInCart;
import ca.mcgill.ecse428.RentOrBuy.service.CartService;
import ca.mcgill.ecse428.RentOrBuy.service.ItemInCartService;
import ca.mcgill.ecse428.RentOrBuy.service.ItemService;

@CrossOrigin(origins = "*")
@RestController
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private ItemInCartService itemInCartService;
	@Autowired
	private ItemService itemService;

	@PostMapping(value = { "/cart/{cartId}", "/cart/{cartId}/" })
	public CartDto createCartDto(@PathVariable("cartId") Integer cartId) throws IllegalArgumentException {
		Cart aCart = null;
		ArrayList<ItemInCart> empty = new ArrayList<ItemInCart>();
		aCart = cartService.createCart(cartId, empty);
		return convertToDto(aCart);
	}

	@PutMapping(value = { "/cart/add/{cartId}", "/cart/add/{cartId}/" })
	public CartDto addItemToCartDto(@PathVariable("cartId") Integer cartId,
			@RequestParam(name = "itemName") String itemName,
			@RequestParam(name = "price") double price,
			@RequestParam(name = "size") String size) throws IllegalArgumentException {
		Cart cart = cartService.getCartByCartId(cartId);
		if (cart == null) {
			throw new IllegalArgumentException("This cart does not exist.");
		}
		Item item = itemService.getItemByName(itemName);
		if (item == null) {
			throw new IllegalArgumentException("This item does not exist.");
		}
		cart = cartService.addNewItemToCart(cart, item, 1, size);
		return convertToDto(cart);
	}
	
	@PutMapping(value = { "/cart/{cartId}/delete", "/cart/{cartId}/" })
	public CartDto deleteItemFromCartDto(@PathVariable("cartId") Integer cartId,
			@RequestParam(name = "itemInCartId") Integer itemInCartId) throws IllegalArgumentException {
		Cart cart = cartService.getCartByCartId(itemInCartId);
		if (cart == null) {
			throw new IllegalArgumentException("This cart does not exist.");
		}
		ItemInCart item = itemInCartService.getItemInCart(itemInCartId);
		if (item == null || item.getItem() == null || item.getItem().getName().equals(null)) {
			throw new IllegalArgumentException("This item does not exist.");
		}
		if (!cart.getCartItems().contains(item)) {
			throw new IllegalArgumentException("Item does not appear in cart");
		}
		cart = cartService.removeItemFromCart(cart, item);
		return convertToDto(cart);
	}
	
	/*
	 * Testing methods
	 */
	
	public static Cart createCart(Integer cartId) throws IllegalArgumentException {
		Cart aCart = null;
		ArrayList<ItemInCart> empty = new ArrayList<ItemInCart>();
		aCart = new Cart();
		aCart.setCartId(cartId);
		aCart.setCartItems(new ArrayList<ItemInCart>());
		return aCart;
	}

	public static Cart addItemToCart(Cart cart, Item codelessItem, String size, int quantity) throws IllegalArgumentException {
		if (cart == null) {
			throw new IllegalArgumentException("This cart does not exist.");
		}
		if (codelessItem == null ) {
			throw new IllegalArgumentException("This item does not exist.");
		}
		if (quantity<1){
			throw new IllegalArgumentException("You must order at least one of the product");
		}
		boolean invalidSize = true;
		for (String sizes:codelessItem.getAvailableSizes()){
			if (sizes.equals(size)) invalidSize = false;
		}
		if (invalidSize){
			throw new IllegalArgumentException("Unavailable size for this product");
		}

		cart.addItemToCart(codelessItem, quantity, size);
		return cart;
	}

	public static Cart addItemToCart(Cart cart, ItemInCart itemInCart) throws IllegalArgumentException {
		if (cart == null) {
			throw new IllegalArgumentException("This cart does not exist.");
		}
		if (itemInCart == null || itemInCart.getItem() == null || itemInCart.getItem().getName().equals(null)) {
			throw new IllegalArgumentException("This item does not exist.");
		}
		cart.addItemToCart(itemInCart);
		return cart;
	}
	
	public static Cart removeItemFromCart(Cart cart, ItemInCart itemInCart) throws IllegalArgumentException {
		if (cart == null) {
			throw new IllegalArgumentException("This cart does not exist.");
		}
		if (itemInCart == null || itemInCart.getItem() == null || itemInCart.getItem().getName().equals(null)) {
			throw new IllegalArgumentException("Item does not appear in cart");
		}
		if (!cart.getCartItems().contains(itemInCart)) {
			throw new IllegalArgumentException("Item does not appear in cart");
		}
		cart.removeItemFromCart(itemInCart);
		return cart;
	}
	
	private CartDto convertToDto(Cart cart) {
		if (cart == null) {
			throw new IllegalArgumentException("There is no such cart");
		}
		ArrayList<ItemInCartDto> items = new ArrayList<ItemInCartDto>();
		for (ItemInCart item : cart.getCartItems()) {
			ItemDto itemDto = new ItemDto(item.getItem().getName(), item.getItem().getPrice(), item.getItem().getAvailableSizes());
			ItemInCartDto itemInCartDto = new ItemInCartDto(itemDto, item.getQuantity(), item.getSize(), item.getItemInCartId());
			items.add(itemInCartDto);
		}
		CartDto cartDto = new CartDto(items, cart.getCartId());
		return cartDto;
	}
}
