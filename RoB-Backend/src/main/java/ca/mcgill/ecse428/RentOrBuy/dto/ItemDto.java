package ca.mcgill.ecse428.RentOrBuy.dto;

import java.util.ArrayList;
import java.util.List;

public class ItemDto {
    private String name;
	private double price;
	private List<String> availableSizes;
	private boolean rentable; //if true then the item is exclusive for rent
	private boolean soldOrRented;	// if true, the item cannot be gotten by anyone elses

    public ItemDto(){
		availableSizes = new ArrayList<String>();
	}

    public ItemDto(String name, double price, List<String> availableSizes){
        this.name=name;
        this.price=price;
        this.availableSizes=availableSizes;
		rentable = false;
		soldOrRented = false;
    }

	public ItemDto(String name, double price, List<String> availableSizes, boolean rentable, boolean status){
		this.name=name;
		this.price=price;
		this.availableSizes=availableSizes;
		this.rentable = rentable;
		soldOrRented = rentable;
		soldOrRented = status;
	}

    public double getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getAvailableSizes() {
		return availableSizes;
	}

	public boolean getRentable(){
		return rentable;
	}

	public boolean getStatus(){
		return soldOrRented;
	}
}
