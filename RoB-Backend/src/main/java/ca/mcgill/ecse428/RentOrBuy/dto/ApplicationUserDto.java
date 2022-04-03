package ca.mcgill.ecse428.RentOrBuy.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse428.RentOrBuy.model.Purchase;
import ca.mcgill.ecse428.RentOrBuy.model.Reservation;

public class ApplicationUserDto{

    private String username;
	private String password;
	private String fullname;
	private String address;
	private CartDto cart;
	private List<ItemDto> items;
    private List<ReservationDto> myReservations;
	private PurchaseHistoryDto purchases;

    public ApplicationUserDto() {}

    public ApplicationUserDto(String username, String password, 
        String fullname, String address, CartDto cart, List<ItemDto> items,
        List<ReservationDto> myReservations, PurchaseHistoryDto purchases){
            this.username=username;
            this.password=password;
            this.fullname=fullname;
            this.address=address;
            this.cart=cart;
            this.items=items;
            this.myReservations = myReservations;
            this.purchases = purchases;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword() {
		return password;
	}

    public String getFullname() {
		return fullname;
	}

    public String getAddress() {
		return address;
	}

    public List<ItemDto> getItems() {
		return items;
	}

    public CartDto getCart() {
		return cart;
	}
    
	public List<ReservationDto> getReservations(){
		return myReservations;
	}
	
	public PurchaseHistoryDto getPurchases(){
		return purchases;
	}
}