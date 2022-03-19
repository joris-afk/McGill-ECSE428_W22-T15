package ca.mcgill.ecse428.RentOrBuy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.RentOrBuy.dao.PurchaseRepository;
import ca.mcgill.ecse428.RentOrBuy.model.ApplicationUser;
import ca.mcgill.ecse428.RentOrBuy.model.Cart;
import ca.mcgill.ecse428.RentOrBuy.model.Purchase;

@Service
public class PurchaseService {
    
    @Autowired 
    private PurchaseRepository purchaseRepository;

    @Transactional
    public Purchase createPurchase(ApplicationUser buyer, Cart cart){
        if(buyer==null){
            throw new IllegalArgumentException("Have to assign a buyer to a purchase");
        }
        if(cart==null){
            throw new IllegalArgumentException("Cannot purchase an empty cart");
        }

        Purchase purchase = new Purchase();
        purchase.setBuyer(buyer);
        purchase.setCart(cart);
        purchaseRepository.save(purchase);
        return purchase;

    }

    @Transactional
    public void deletePurchase(Purchase purchase){
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