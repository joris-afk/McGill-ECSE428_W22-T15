package ca.mcgill.ecse428.RentOrBuy.dto;

import java.util.List;

public class ApplicationUserDto{

    private String username;
	private String password;
	private String fullname;
	private String address;
	private CartDto cart;
	private List<ItemDto> items;

    public ApplicationUserDto() {}

    public ApplicationUserDto(String username, String password, 
        String fullname, String address, CartDto cart, List<ItemDto> items){
            this.username=username;
            this.password=password;
            this.fullname=fullname;
            this.address=address;
            this.cart=cart;
            this.items=items;
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
}