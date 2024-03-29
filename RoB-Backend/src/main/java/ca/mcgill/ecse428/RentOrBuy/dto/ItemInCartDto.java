package ca.mcgill.ecse428.RentOrBuy.dto;

public class ItemInCartDto {
    private ItemDto item;
	private int quantity;
	private String size;
    private Integer itemInCartId;

    public ItemInCartDto(){
    }
    
    public ItemInCartDto(ItemDto item, int quantity, String size, Integer itemInCartId){
    	
    	this.item = item;
    	this.quantity = quantity;
    	this.size = size;
        this.itemInCartId=itemInCartId;
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

    public Integer getItemInCartId(){
        return itemInCartId;
    }
}
