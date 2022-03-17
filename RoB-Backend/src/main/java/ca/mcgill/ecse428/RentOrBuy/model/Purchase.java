package ca.mcgill.ecse428.RentOrBuy.model;

import java.util.ArrayList;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class Purchase {

    private String orderId;
    private Cart cart;
    private ApplicationUser buyer;

    //constructors
    public Purchase() {}
    public Purchase(ApplicationUser buyer, Cart cart){
        this.buyer = buyer;
        this.cart = cart;
    }

    @Id
    public String getOrderId(){
        return this.orderId;
    }

    public void setOrderId(String newOrderId){
        this.orderId = newOrderId;
    }

    @OneToMany
    public Cart getCart(){
        return this.cart;    
    }

    public void setCart(Cart newCart){
        this.cart = newCart;
    }

    @OneToOne
    public ApplicationUser getBuyer(){
        return this.buyer;
    }

    public void setBuyer(ApplicationUser newBuyer){
        this.buyer = newBuyer;
    }
}
