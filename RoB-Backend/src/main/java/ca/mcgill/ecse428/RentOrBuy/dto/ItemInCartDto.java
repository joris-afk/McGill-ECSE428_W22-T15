package ca.mcgill.ecse428.RentOrBuy.dto;

public class ItemInCartDto {
    private ItemDto item;
	private int quantity;
	private String size;

    public ItemInCartDto(){
    }

    public ItemDto getItem(){
        return item;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getSize(){
        return size;
    }
}
