package ca.mcgill.ecse428.RentOrBuy.dto;

public class PurchaseDto {

    private String orderId;
    private CartDto cart;
//    private ApplicationUserDto buyer;
    
    //constructors
    public PurchaseDto() {}
    public PurchaseDto(String orderId,CartDto cart){
        this.orderId = orderId;
//        this.buyer = buyer;
        this.cart = cart;
    }

    public String getOrderId(){
        return this.orderId;
    }

    public CartDto getCart(){
        return this.cart;    
    }

//    public ApplicationUserDto getBuyer(){
//        return this.buyer;
//    }
}
