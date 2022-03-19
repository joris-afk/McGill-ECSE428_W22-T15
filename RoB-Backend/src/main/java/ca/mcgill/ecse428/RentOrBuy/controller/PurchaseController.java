package ca.mcgill.ecse428.RentOrBuy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.RentOrBuy.dto.*;
import ca.mcgill.ecse428.RentOrBuy.model.*;
import ca.mcgill.ecse428.RentOrBuy.service.*;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ApplicationUserService applicationUserService;
    
    @Autowired
    private CartService cartService;

    @PostMapping(value = {"/purchase/{orderId}","/purchase/{orderId}/"})
    public PurchaseDto createPurchase(@PathVariable("orderId") String orderId, 
        @RequestParam(name = "cartId") Integer cartId,
        @RequestParam(name = "username") String username) throws IllegalArgumentException{
        
            ApplicationUser aUser = applicationUserService.getApplicationUserByUsername(username);
            Cart aCart = cartService.getCartByCartId(cartId);
            Purchase purchase = purchaseService.createPurchase(aUser, aCart);
            return convertToDto(purchase);
    }

    @GetMapping(value = {"/purchase/{orderId}","/purchase/{orderId}/"})
    public PurchaseDto getPurchaseByOrderId(@PathVariable("orderId") String orderId) throws IllegalArgumentException{
        return convertToDto(purchaseService.getPurchaseByOrderId(orderId));
    }

    @GetMapping(value = {"/purchase","/purchase/"})
    public List<PurchaseDto> getAllPurchase() throws IllegalArgumentException{
        List<Purchase> ps = purchaseService.getAllPurchase();
        List<PurchaseDto> psDto = new ArrayList<PurchaseDto>();
        for(Purchase p : ps){
            psDto.add(convertToDto(p));
        }
        return psDto;
    }

    @DeleteMapping(value = {"/purchase/{orderId}","/purchase/{orderId}/"})
    public void deletePurchase(@PathVariable("orderId") String orderId)throws IllegalArgumentException{
        Purchase p = purchaseService.getPurchaseByOrderId(orderId);
        purchaseService.deletePurchase(p);
    }

    public PurchaseDto convertToDto(Purchase purchase){
        if(purchase == null){
            throw new IllegalArgumentException("Cannot convert null Purchase to PurchaseDto");
        }
        ApplicationUserDto applicationUserDto = convertToDto(purchase.getBuyer());
        CartDto cartDto = convertToDto(purchase.getCart());
        PurchaseDto purchaseDto = new PurchaseDto(purchase.getOrderId(), applicationUserDto, cartDto);
        return purchaseDto;
    }

    private ApplicationUserDto convertToDto(ApplicationUser applicationUser) {
		
		if (applicationUser == null) {
            throw new IllegalArgumentException("There is no such User");
        }
		
		if (applicationUser.getCart() == null && applicationUser.getItems() == null) {
			
			ApplicationUserDto applicationUserDto = new ApplicationUserDto(applicationUser.getUsername(), applicationUser.getPassword(),
					applicationUser.getFullname(), applicationUser.getAddress(),
					null, null);
			
			return applicationUserDto;
		}
	
		CartDto cartDto = convertToDto(applicationUser.getCart());
		ArrayList<ItemDto> itemDto = new ArrayList<ItemDto>();
        for(Item item : applicationUser.getItems()){
            itemDto.add(convertToDto(item));
        }
		
		ApplicationUserDto applicationUserDto = new ApplicationUserDto(applicationUser.getUsername(), applicationUser.getPassword(),
				applicationUser.getFullname(), applicationUser.getAddress(),
				cartDto, itemDto);
		
		return applicationUserDto;
	}

    private CartDto convertToDto(Cart cart) {
		
		if (cart == null) {
            throw new IllegalArgumentException("There is no such cart");
        }
		if (cart.getCartId()<0) {
			throw new IllegalArgumentException("This cart has an invalid id");
		}
		
		ArrayList<ItemInCartDto> itemInCartDto = new ArrayList<ItemInCartDto>();
        for(ItemInCart iic : cart.getCartItems()){
            itemInCartDto.add(convertToDto(iic));
        }
		CartDto cartDto = new CartDto(itemInCartDto, cart.getCartId());
		return cartDto;
	}

    private ItemInCartDto convertToDto(ItemInCart itemInCart) {
		
		if (itemInCart == null) {
            throw new IllegalArgumentException("There is no such item in cart");
        }
		if (itemInCart.getItemInCartId()<0) {
			throw new IllegalArgumentException("This cart item has an invalid id");
		}
		
		ItemDto itemDto = convertToDto(itemInCart.getItem());
		ItemInCartDto itemInCartDto = new ItemInCartDto(itemDto, itemInCart.getQuantity(), itemInCart.getSize(), itemInCart.getItemInCartId());
		return itemInCartDto;
	}

    private ItemDto convertToDto(Item item) {
		
		if (item == null) {
            throw new IllegalArgumentException("There is no such item");
        }
		
		ItemDto itemDto = new ItemDto(item.getName(), item.getPrice(), item.getAvailableSizes());
		return itemDto;
	}
}

