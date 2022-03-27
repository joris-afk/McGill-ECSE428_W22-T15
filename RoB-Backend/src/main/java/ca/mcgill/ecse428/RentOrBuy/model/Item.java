package ca.mcgill.ecse428.RentOrBuy.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Item {
	
	private String name;
	private double price;
	private List<String> availableSizes;
	private boolean rentable; //if true then the item is exclusive for rent
	private boolean soldOrRented;	// if true, the item cannot be gotten by anyone elses

	
	public Item() {
		availableSizes = new ArrayList<String>();
		soldOrRented = false;
	}

	public Item(String name, double price, List<String> availableSizes){
		this.name=name;
		this.price=price;
		this.availableSizes=availableSizes;
	}

	public Item(String name, double price, List<String> availableSizes, boolean rentable){
		this.name=name;
		this.price=price;
		this.availableSizes=availableSizes;
		this.rentable = rentable;
		soldOrRented = false;
	}
	
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
	public void addAvailableSizes(List<String> availableSizes) {
		if (this.availableSizes == null) {
			availableSizes = new ArrayList<String>();
		}
		this.availableSizes.addAll(availableSizes);
	}
	public void addAvailableSize(String availableSize) {
		if (this.availableSizes == null) {
			this.availableSizes = new ArrayList<String>();
		}
		this.availableSizes.add(availableSize);
	}
	public void removeAvailableSize(String unavailableSize) {
		this.availableSizes.remove(unavailableSize);
	}
	public void removeAvailableSizes(List<String> unavailableSizes) {
		this.availableSizes.removeAll(unavailableSizes);
	}	

	public void setRentable(boolean forRent){
		rentable = forRent;
	}

	public void setSoldRented(boolean SOR){
		soldOrRented = SOR;
	}
	
	public boolean getRentable(){
		return rentable;
	}

	public boolean getStatus(){
		return soldOrRented;
	}
}
