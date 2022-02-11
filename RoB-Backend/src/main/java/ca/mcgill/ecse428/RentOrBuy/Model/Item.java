package ca.mcgill.ecse428.RentOrBuy.model;

import java.util.List;

public class Item {
	private String name;
	private double price;
	private List<String> availableSizes;
	
	public Item() {}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getAvailableSizes() {
		return availableSizes;
	}
	public void setAvailableSizes(List<String> availableSizes) {
		this.availableSizes = availableSizes;
	}
}
