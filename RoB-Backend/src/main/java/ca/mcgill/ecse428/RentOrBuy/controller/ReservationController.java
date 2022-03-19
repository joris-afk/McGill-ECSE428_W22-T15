package ca.mcgill.ecse428.RentOrBuy.controller;

import java.util.ArrayList;
import java.util.List;

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
import ca.mcgill.ecse428.RentOrBuy.controller.ApplicationUserController;


@CrossOrigin(origins = "*")
@RestController
public class ReservationController {
    
    @Autowired
	private ReservationService reservationService;

    @Autowired
    private ApplicationUserService AUserService;

    @Autowired
    private ItemService itemService;

    
    @PostMapping(value = { "/reservations/{reservationId}", "/reservations/{reservationId}/" })
    public ReservationDto createReservation(@PathVariable("reservationId") long reservationId,
        @RequestParam("user") String username,
        @RequestParam("quantity") int quantity,
        @RequestParam("item") String itemName) throws IllegalArgumentException{

        ApplicationUser aAU = null;
        if (username != null)
        aAU = AUserService.getApplicationUserByUsername(username);
        
        Item aItem = null;
        if (itemName != null)
        aItem = itemService.getItemByName(itemName);

        Reservation aReservation = reservationService.createReservation(aItem, aAU, quantity, reservationId);

        return convertToDto(aReservation);
    }

	@GetMapping(value = { "/reservations/{reservationId}", "/reservations/{reservationId}/" })
	public ReservationDto getReservationByID(@PathVariable("reservationId") long reservationId){
		return convertToDto(reservationService.getReservationByReservationId(reservationId));
	}

	@GetMapping(value = { "/reservations", "/reservations/" })
	public List<ReservationDto> getAllReservations(){
		List<ReservationDto> itemDtos = new ArrayList<>();
		for (Reservation reservation : reservationService.getAllReservations()) {
			itemDtos.add(convertToDto(reservation));
		}
		return itemDtos;
	}
    
    /* 
     * Remove an reservation from database
     */    
    @DeleteMapping(value = { "/reservations/{reservationId}", "/reservations/{reservationId}/" })
	public void deleteItem(@PathVariable("reservationId") int reservationId) throws IllegalArgumentException {
		Reservation aReservation = reservationService.getReservationByReservationId(reservationId);
        reservationService.deleteReservation(aReservation);
	}



    /*
     *  A LOT OF DTO CONVERTERS
     */
    private ApplicationUserDto convertToDto(ApplicationUser applicationUser) {
		
		if (applicationUser == null) {
            throw new IllegalArgumentException("There is no such User");
        }
		
		if (applicationUser.getCart() == null && applicationUser.getItems() == null) {
			
			ApplicationUserDto applicationUserDto = new ApplicationUserDto(applicationUser.getUsername(), applicationUser.getPassword(),
					applicationUser.getFullname(), applicationUser.getAddress(),
					null, null);
			
			return applicationUserDto;
		}
		
		
		CartDto cartDto = convertToDto(applicationUser.getCart());
		List<ItemDto> itemDto = createItemDtosForAppUser(applicationUser.getItems());
		
		ApplicationUserDto applicationUserDto = new ApplicationUserDto(applicationUser.getUsername(), applicationUser.getPassword(),
				applicationUser.getFullname(), applicationUser.getAddress(),
				cartDto, itemDto);
		
		return applicationUserDto;
	}
	
	private CartDto convertToDto(Cart cart) {
		
		if (cart == null) {
            throw new IllegalArgumentException("There is no such cart");
        }
		if (cart.getCartId()<0) {
			throw new IllegalArgumentException("This cart has an invalid id");
		}
		
		List<ItemInCartDto> itemDto = createItemInCartDtosForCart(cart.getCartItems());
		CartDto cartDto = new CartDto(itemDto, cart.getCartId());
		return cartDto;
	}

	private ItemDto convertToDto(Item item) {
		
		if (item == null) {
            throw new IllegalArgumentException("There is no such item");
        }
		
		ItemDto itemDto = new ItemDto(item.getName(), item.getPrice(), item.getAvailableSizes());
		return itemDto;
	}

    private List<ItemInCartDto> createItemInCartDtosForCart(List<ItemInCart> itemsInCart) {
		
		List<ItemInCartDto> itemsInCartForCart = new ArrayList<>();
		
			for (ItemInCart itemInCart : itemsInCart) {
				itemsInCartForCart.add(convertToDto(itemInCart));
			}
		
		return itemsInCartForCart;
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

	private List<ItemDto> createItemDtosForAppUser(List<Item> itemsFormAppUser) {
		
		List<ItemDto> itemsForAppUser = new ArrayList<>();
		
		for (Item item : itemsFormAppUser) {
			itemsForAppUser.add(convertToDto(item));
		}
		
		return itemsForAppUser;
	}


    private ReservationDto convertToDto(Reservation aReservation){
        if (aReservation == null){
            throw new IllegalArgumentException("this resevation doesn't exist");
        }

        ReservationDto aReservationDto = new ReservationDto(aReservation.getReservationId(),
        convertToDto(aReservation.getUser()),convertToDto(aReservation.getItem()),  aReservation.getQuantity());
        return aReservationDto;
    }


	//For testing the cucumber stuff
	public static Reservation createAReservation(Item item, ApplicationUser user, int quantity, long reservationId){
		Rob rob = RobApplication.getRob();

		if (item == null){
            throw new IllegalArgumentException("Please specify an item");
        }
        if (user == null){  //hypothetically this can't happen unless the user is not logged in
            throw new IllegalArgumentException("Please specify a buyer");
        }
        if (quantity < 0){
            throw new IllegalArgumentException("Please specify a positive quantity");
        }
		if (reservationId<0){	//normally impossible because of autogeneration
			throw new IllegalArgumentException("Please specify a valid reservation Id");
		}

		List<Reservation> allRes = rob.getReservations();

		for (Reservation aReservation : allRes){
			if (item.getName().equals(aReservation.getItem().getName())){
				throw new IllegalArgumentException("Item is already reserved");
			}
		}

		Reservation addedReservation = new Reservation();
		addedReservation.setItem(item);
		addedReservation.setQuantity(quantity);
		addedReservation.setReservationId(reservationId);
		addedReservation.setUser(user);

		rob.addReservation(addedReservation);

		return addedReservation;
	}
}
