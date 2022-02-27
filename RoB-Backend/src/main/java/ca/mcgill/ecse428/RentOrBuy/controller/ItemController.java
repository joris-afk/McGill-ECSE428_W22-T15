package ca.mcgill.ecse428.RentOrBuy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.RentOrBuy.model.*;
import ca.mcgill.ecse428.RentOrBuy.service.*;
import ca.mcgill.ecse428.RentOrBuy.InvalidInputException;
import ca.mcgill.ecse428.RentOrBuy.RobApplication;
import ca.mcgill.ecse428.RentOrBuy.dto.*;


@CrossOrigin(origins = "*")
@RestController
public class ItemController {

    @Autowired
	private ItemService itemService;

    /*
     * Calling RESTful service endpoints
     * Create a new item
     * @return item dto
     */
    
    @PostMapping(value = { "/items/{name}", "/items/{name}/" })
	public ItemDto createItemDto(@PathVariable("name") String name,
			@RequestParam(name = "price") double price, 
			@RequestParam(name = "availableSizes") List<String> availableSizes)
		throws IllegalArgumentException {
		
		Item item = itemService.createItem(name, price, availableSizes);
		return convertToDto(item);
	}


    /*
     * @return a particular item
     */
    @GetMapping(value = { "/items/{name}", "/items/{name}/" })
    public ItemDto getItemByName(@PathVariable("name") String name) throws IllegalArgumentException{
        return convertToDto(itemService.getItemByName(name));
    }

    /*
     * @return all Items
     */    
    @GetMapping(value = { "/items", "/items/" })
	public List<ItemDto> getAllReservations() {
		List<ItemDto> itemDtos = new ArrayList<>();
		for (Item item : itemService.getAllItems()) {
			itemDtos.add(convertToDto(item));
		}
		return itemDtos;
	}
	
    /* 
     * Remove an item from database
     */    
    @DeleteMapping(value = { "/items/{name}", "/items/{name}/" })
	public void deleteItem(@PathVariable("name") String name) throws IllegalArgumentException {
		Item aItem = itemService.getItemByName(name);
        itemService.deleteItem(aItem);
	}
    
    @PatchMapping(value = { "/items/{name}", "/items/{name}/" })
    public ItemDto editItem(@PathVariable("name") String name, 
                @RequestParam(required = false, name = "price") String new_price,
			    @RequestParam(required = false, name = "AddedSize") String new_size,
			    @RequestParam(required = false, name = "RemovedSize") String old_size) throws IllegalArgumentException {
                    Item aItem = itemService.getItemByName(name);
                    if (new_price != null){
                        itemService.editItemPrice(aItem, Double.parseDouble(new_price));
                    }
                    if (new_size != null){
                        itemService.addItemSize(aItem, new_size);
                    }
                    if (old_size != null){
                        itemService.removeItemSize(aItem, old_size);
                    }
                    return convertToDto(aItem);
    }
        
    //Utility method
    private ItemDto convertToDto(Item item) {
		
		if (item == null) {
            throw new IllegalArgumentException("There is no such item");
        }
		
		ItemDto itemDto = new ItemDto(item.getName(), item.getPrice(), item.getAvailableSizes());
		return itemDto;
	}

    //Testing methods
    public static Item createItem(String username, String itemName, double price) throws InvalidInputException{
		Rob rob = RobApplication.getRob(); 
        ApplicationUser u = null;

		for (ApplicationUser user:rob.getExistingUsers()){
            if (username.equals(user.getUsername())){
                u=user;
            }
        }
        if (u == null){
            throw new InvalidInputException("must be logged in to add a product");
        }
        
        Item newProduct = new Item();
        newProduct.setName(itemName);
        newProduct.setPrice(price);
        newProduct.setAvailableSizes(null);
		
        for (Item produce: rob.getProducts()){
            if (itemName.equals(produce.getName())){
                throw new InvalidInputException("You've already added this item");
            }
        }
		rob.addProduct(newProduct);
		return newProduct;
	}

    public static Item addItem(String name, double price, List<String> sizes) throws InvalidInputException{
            Rob rob = RobApplication.getRob();

            System.out.println("Adding: "+name);
            if (name == null){
                throw new InvalidInputException("product needs a name");
            }
            if (price<0) {
                throw new InvalidInputException("product can't have a negative price");
            }
            if (sizes==null || sizes.isEmpty()) {
                throw new InvalidInputException("product needs a size");
            }
            for (Item product: rob.getProducts()){
                if (name.equals(product.getName())){
                throw new InvalidInputException("You've already added this item");
                }
            }

            Item newProduct = new Item();
            newProduct.setName(name);
            newProduct.setPrice(price);
            newProduct.setAvailableSizes(sizes);

            rob.addProduct(newProduct);
            return newProduct;
    }

    public static Item addItemSize(Item currItem, String newSize) throws InvalidInputException{
		Rob rob = RobApplication.getRob();
        System.out.println(rob.getProducts().size());
        for (Item it: rob.getProducts()) {
            System.out.println("item in rob:" + it.getName());
        }
        //check for valid inputs 
        if (currItem == null || currItem.getName() == null || currItem.getName().equals("")) {
			throw new IllegalArgumentException("Can't edit null item");
		}
		if (newSize == null || newSize.equals("")) {
			throw new IllegalArgumentException("Must specify one available size");
		}
		if (currItem.getAvailableSizes().contains(newSize)){
			throw new IllegalArgumentException("Cannot add duplicate size");
		}
        System.out.println("size to add: " + newSize + " for " + currItem.getName());

        //replace the item in 
        for (Item it: rob.getProducts()){
            System.out.println(it.getName());
            if (currItem.getName().equals(it.getName())){
                rob.deleteExistingProduct(it);
            }
        }

        currItem.addAvailableSize(newSize); //assuming the item is treated as new entity
        System.out.println("Added");
        rob.addProduct(currItem);

        return currItem;
	}

    public static Item removeItemSize(Item currItem, String oldSize) throws InvalidInputException{
		Rob rob = RobApplication.getRob(); 

        //check for valid inputs 
        if (currItem == null) {
			throw new IllegalArgumentException("Can't edit null item");
		}
		if (oldSize == null || oldSize.isEmpty()) {
			throw new IllegalArgumentException("Must specify one unavailable size");
		}

        //replace the item in 
        for (Item it: rob.getProducts()){
            if (currItem.getName().equals(it.getName())){
                rob.deleteExistingProduct(it);
            }
        }
        currItem.removeAvailableSize(oldSize); //assuming the item is treated as new entity

        rob.addProduct(currItem);

        return currItem;
	}

    public static Item updatePrice(Item currItem, double newPrice) throws InvalidInputException{
		Rob rob = RobApplication.getRob(); 

        //check for valid inputs 
        if (currItem == null) {
			throw new IllegalArgumentException("Can't edit null item");
		}
		if (newPrice < 0 ) {
			throw new IllegalArgumentException("Item price can't be negative");
		}

        //replace the item in 
        for (Item it: rob.getProducts()){
            if (currItem.getName().equals(it.getName())){
                rob.deleteExistingProduct(it);
            }
        }
        currItem.setPrice(newPrice); //assuming the item is treated as new entity

        rob.addProduct(currItem);

        return currItem;
	}
}