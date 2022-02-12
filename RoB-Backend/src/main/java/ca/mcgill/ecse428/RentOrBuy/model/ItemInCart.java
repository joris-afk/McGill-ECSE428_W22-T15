package ca.mcgill.ecse428.RentOrBuy.model;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemInCart {
	private Item item;
	private int quantity;
	private String size;
	
	public ItemInCart() {}
	
	@Id
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
}
