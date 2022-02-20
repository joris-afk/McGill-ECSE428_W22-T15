package ca.mcgill.ecse428.RentOrBuy.controller;

public class ItemInCartController {

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.RentOrBuy.model.*;
import ca.mcgill.ecse428.RentOrBuy.service.*;
import ca.mcgill.ecse428.RentOrBuy.InvalidInputException;
import ca.mcgill.ecse428.RentOrBuy.RobApplication;
import ca.mcgill.ecse428.RentOrBuy.dto.*;


@CrossOrigin(origins = "*")
@RestController
public class ItemInCartController {
    
    @Autowired
	private ItemInCartService IICService;

    @Autowired
    private ItemService itemService;

    @PostMapping(value = { "/itemsInCart/{itemInCartId}", "/itemsInCart/{itemInCartId}/" })
	public ItemInCartDto createItemInCart(@PathVariable("itemInCartId") Integer itemInCartId,
            @RequestParam(name = "item name") String itemName,
			@RequestParam(name = "quantity") int quantity, 
			@RequestParam(name = "size") String size)
		throws IllegalArgumentException {
		
        Item aItem = null;
        aItem = itemService.getItemByName(itemName);


		ItemInCart IIC = IICService.createItemInCart(aItem, quantity, size);
		return convertToDto(IIC);
	}


    @GetMapping(value = { "/itemsInCart/{itemInCartId}", "/itemsInCart/{itemInCartId}/" })
    public ItemInCartDto getItemInCartByName(@PathVariable("itemInCartId") Integer itemInCartId) throws IllegalArgumentException{
        return convertToDto(IICService.getItemInCart(itemInCartId));
    }
    
    @GetMapping(value = { "/itemsInCart", "/itemsInCart/" })
	public List<ItemInCartDto> getAllReservations() {
		List<ItemInCartDto> IICDtos = new ArrayList<>();
		for (ItemInCart IIC : IICService.getAllItemsInCart()) {
			IICDtos.add(convertToDto(IIC));
		}
		return IICDtos;
	}

    @DeleteMapping(value = { "/itemsInCart/{itemInCartId}", "/itemsInCart/{itemInCartId}/" })
	public void deleteItem(@PathVariable("itemInCartId") Integer itemInCartId) throws IllegalArgumentException {
		ItemInCart aIIC = IICService.getItemInCart(itemInCartId);
        IICService.deleteItem(aIIC);
	}

    private ItemDto convertToDto(Item item) {
		
		if (item == null) {
            throw new IllegalArgumentException("There is no such item");
        }
		
		ItemDto itemDto = new ItemDto(item.getName(), item.getPrice(), item.getAvailableSizes());
		return itemDto;
	}

    private ItemInCartDto convertToDto(ItemInCart itemInCart) {
		
		if (itemInCart == null) {
            throw new IllegalArgumentException("There is no such item in cart");
        }
		if (itemInCart.getItemInCartId()<0) {
			throw new IllegalArgumentException("This cart item has an invalid id");
		}
		
		ItemDto itemDto = convertToDto(itemInCart.getItem());
		ItemInCartDto itemInCartDto = new ItemInCartDto(itemDto, itemInCart.getQuantity(), itemInCart.getSize(), itemInCart.getItemInCartId());
		return itemInCartDto;
	}
}
