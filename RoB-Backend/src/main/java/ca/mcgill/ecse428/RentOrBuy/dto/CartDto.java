package ca.mcgill.ecse428.RentOrBuy.dto;

import java.util.ArrayList;
import java.util.List;

public class CartDto {
    private List<ItemInCartDto> cartItems;
    private Integer cartId;

    public CartDto(){
      if (this.cartItems.equals(null)) {
			  this.cartItems = new ArrayList<ItemInCartDto>();
		  }
    }

    public CartDto(List<ItemInCartDto> cartItems,Integer cartId){
      this.cartItems=cartItems;
      this.cartId=cartId;
    }

    public List<ItemInCartDto> getCartItems() {
		  return cartItems;
	  }

    public Integer getcartId(){
      return cartId;
    }
}
