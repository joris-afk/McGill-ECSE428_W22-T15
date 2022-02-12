package ca.mcgill.ecse428.RentOrBuy.dto;

import java.util.ArrayList;
import java.util.List;

public class CartDto {
    private List<ItemInCartDto> cartItems;

    public CartDto(){
        if (this.cartItems.equals(null)) {
			this.cartItems = new ArrayList<ItemInCartDto>();
		}
    }

    public CartDto(List<ItemInCartDto> cartItems){
        this.cartItems=cartItems;
    }

    public List<ItemInCartDto> getCartItems() {
		return cartItems;
	}
}
