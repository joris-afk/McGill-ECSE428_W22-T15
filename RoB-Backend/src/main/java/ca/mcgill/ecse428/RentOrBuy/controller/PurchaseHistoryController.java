package ca.mcgill.ecse428.RentOrBuy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.checkerframework.checker.units.qual.A;
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
public class PurchaseHistoryController {
    
    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    //assume that new purchase histories are created blank
    @PostMapping(value = { "/purchaseHistory/{historyOwner}", "/purchaseHistory/{historyOwner}/" })
    public PurchaseHistoryDto createPurchaseHistory(@PathVariable("historyOwner") String historyOwner) 
    throws IllegalArgumentException {

        PurchaseHistory aPH = purchaseHistoryService.createPurchaseHistory(historyOwner, new ArrayList<Purchase>());
        return convertToDto(aPH);
    }

    @GetMapping(value = { "/purchaseHistory/{historyOwner}", "/purchaseHistory/{historyOwner}/" })
    public PurchaseHistoryDto getPurchaseHistory(@PathVariable("historyOwner") String historyOwner) 
    throws IllegalArgumentException {
        PurchaseHistory aPH = purchaseHistoryService.getPurchaseHistoryByHistoryOwner(historyOwner);
        return convertToDto(aPH);
    }

    @DeleteMapping(value = { "/purchaseHistory/{historyOwner}", "/purchaseHistory/{historyOwner}/" })
    public void deletePurchaseHistory(@PathVariable("historyOwner") String historyOwner) 
    throws IllegalArgumentException {
        purchaseHistoryService.deleteHistory(
        purchaseHistoryService.getPurchaseHistoryByHistoryOwner(historyOwner));
    }



    private PurchaseHistoryDto convertToDto(PurchaseHistory purchaseHistory) {
		if (purchaseHistory == null) {
            throw new IllegalArgumentException("There is no history");
        }

        List<PurchaseDto> purchaseList = new ArrayList();
        for (Purchase p:purchaseHistory.getPurchases()){
            purchaseList.add(convertToDto(p));
        }
		
	    PurchaseHistoryDto purchaseHistoryDto = new PurchaseHistoryDto(purchaseHistory.getHistoryOwner(), purchaseList);

		return purchaseHistoryDto;
	}

    private PurchaseDto convertToDto(Purchase purchase){
        if(purchase == null){
            throw new IllegalArgumentException("Cannot convert null Purchase to PurchaseDto");
        }

        CartDto cartDto = convertToDto(purchase.getCart());
        PurchaseDto purchaseDto = new PurchaseDto(purchase.getOrderId(), cartDto);
        return purchaseDto;
    }

    private ApplicationUserDto convertToDto(ApplicationUser applicationUser) {
		
		if (applicationUser == null) {
            throw new IllegalArgumentException("There is no such User");
        }
		
		if (applicationUser.getCart() == null && applicationUser.getItems() == null
				&& applicationUser.getReservations() == null && applicationUser.getPurchases() == null) {
			
			ApplicationUserDto applicationUserDto = new ApplicationUserDto(applicationUser.getUsername(), applicationUser.getPassword(),
					applicationUser.getFullname(), applicationUser.getAddress(),
					null, null, null, null);
			
			return applicationUserDto;
		}
				
		CartDto cartDto = convertToDto(applicationUser.getCart());
		List<ItemDto> itemDto = new ArrayList<ItemDto>();
		for(Item i : applicationUser.getItems()) {
			itemDto.add(convertToDto(i));
		}
		List<ReservationDto> resDto = new ArrayList<ReservationDto>();
		for(Reservation r : applicationUser.getReservations()) {
			resDto.add(convertToDto(r));
		}
		List<PurchaseDto> purDto = new ArrayList<PurchaseDto>();
		for(Purchase p : applicationUser.getPurchases().getPurchases()) {
			purDto.add(convertToDto(p));
		}
	
		ApplicationUserDto applicationUserDto = new ApplicationUserDto(applicationUser.getUsername(), applicationUser.getPassword(),
				applicationUser.getFullname(), applicationUser.getAddress(),
				cartDto, itemDto, resDto, new PurchaseHistoryDto( applicationUser.getUsername()+"'s history",purDto));
		return applicationUserDto;
	}
    
    private ReservationDto convertToDto(Reservation aReservation){
        if (aReservation == null){
            throw new IllegalArgumentException("this resevation doesn't exist");
        }

        ReservationDto aReservationDto = new ReservationDto(aReservation.getReservationId(),
        convertToDto(aReservation.getItem()),  aReservation.getQuantity());
        return aReservationDto;
    }

    private CartDto convertToDto(Cart cart) {
		
		if (cart == null) {
            throw new IllegalArgumentException("There is no such cart");
        }
		if (cart.getCartId()<0) {
			throw new IllegalArgumentException("This cart has an invalid id");
		}
		
		ArrayList<ItemInCartDto> itemInCartDto = new ArrayList<ItemInCartDto>();
        for(ItemInCart iic : cart.getCartItems()){
            itemInCartDto.add(convertToDto(iic));
        }
		CartDto cartDto = new CartDto(itemInCartDto, cart.getCartId());
		return cartDto;
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

    private ItemDto convertToDto(Item item) {
		
		if (item == null) {
            throw new IllegalArgumentException("There is no such item");
        }
		
		ItemDto itemDto = new ItemDto(item.getName(), item.getPrice(), item.getAvailableSizes());
		return itemDto;
	}
}
