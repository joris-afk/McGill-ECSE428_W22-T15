package ca.mcgill.ecse428.RentOrBuy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.RentOrBuy.dao.ApplicationUserRepository;
import ca.mcgill.ecse428.RentOrBuy.dao.CartRepository;
import ca.mcgill.ecse428.RentOrBuy.dao.PurchaseHistoryRepository;
import ca.mcgill.ecse428.RentOrBuy.dao.PurchaseRepository;
import ca.mcgill.ecse428.RentOrBuy.model.ApplicationUser;
import ca.mcgill.ecse428.RentOrBuy.model.Cart;
import ca.mcgill.ecse428.RentOrBuy.model.ItemInCart;
import ca.mcgill.ecse428.RentOrBuy.model.Purchase;

@Service
public class PurchaseService {
    
    @Autowired 
    private PurchaseRepository purchaseRepository;
    
    @Autowired 
    private ApplicationUserRepository auRepository;
    
    @Autowired 
    private CartRepository cartRepository;

    @Autowired
    private PurchaseHistoryRepository pHRepository;

    @Transactional
    public Purchase createPurchase(String orderId, ApplicationUser buyer, Cart cart){
    	if(orderId == null) {
    		throw new IllegalArgumentException("Order Id cannot be empty");
    	}
    	List<Purchase> ps = (List<Purchase>) purchaseRepository.findAll();
    	for(Purchase p : ps) {
    		if(p.getOrderId().equals(orderId)) {
    			throw new IllegalArgumentException("Have to assign a unique orderId to a purchase");
    		}
    	}
        if(buyer==null){
            throw new IllegalArgumentException("Have to assign a buyer to a purchase");
        }
        if(cart==null){
            throw new IllegalArgumentException("Cannot purchase an empty cart");
        }

        Purchase purchase = new Purchase();
        purchase.setOrderId(orderId);
//        purchase.setBuyer(buyer);
        purchase.setCart(cart);
        purchaseRepository.save(purchase);
        // save changes to application user database and cart database      
        buyer.addPurchase(purchase);        
        cart.setCartItems(new ArrayList<ItemInCart>());
        buyer.setCart(cart);
        pHRepository.save(buyer.getPurchases());
        auRepository.save(buyer);
        cartRepository.save(cart);
        return purchase;

    }

    @Transactional
    public void deletePurchase(Purchase purchase, String username){
    	ApplicationUser au = auRepository.findApplicationUserByUsername(username);
    	au.removePurchase(purchase);
    	auRepository.save(au);
        purchaseRepository.delete(purchase);
    }

    @Transactional
    public Purchase getPurchaseByOrderId(String orderId){
        return purchaseRepository.findPurchaseByOrderId(orderId);
    }

    @Transactional
    public List<Purchase> getAllPurchase(){
        return (List<Purchase>)purchaseRepository.findAll();
    }

}
