package ca.mcgill.ecse428.RentOrBuy.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.List;
import java.util.Set;

@Entity
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
	
	@Id
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ElementCollection
	public List<String> getAvailableSizes() {
		return availableSizes;
	}
	public void setAvailableSizes(List<String> availableSizes) {
		this.availableSizes = availableSizes;
	}
}
