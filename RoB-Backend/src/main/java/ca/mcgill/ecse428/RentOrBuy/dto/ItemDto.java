package ca.mcgill.ecse428.RentOrBuy.dto;

import java.util.List;

public class ItemDto {
    private String name;
	private double price;
	private List<String> availableSizes;

    public ItemDto(){}

    public ItemDto(String name, double price, List<String> availableSizes){
        this.name=name;
        this.price=price;
        this.availableSizes=availableSizes;
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
}
