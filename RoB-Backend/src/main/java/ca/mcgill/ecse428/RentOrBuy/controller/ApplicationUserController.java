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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.RentOrBuy.model.*;
import ca.mcgill.ecse428.RentOrBuy.service.*;
import ca.mcgill.ecse428.RentOrBuy.InvalidInputException;
import ca.mcgill.ecse428.RentOrBuy.RobApplication;
import ca.mcgill.ecse428.RentOrBuy.dto.*;

@CrossOrigin(origins = "*")
@RestController
public class ApplicationUserController {
	
	@Autowired
	private ApplicationUserService applicationUserService;
	
	/*
	 * Calling RESTful service endpoints
	 *
	 * http://localhost:8080/applicationUsers/{username}?password={String}&fullname={String}
	 * &address={String}
	 *
	 * @return application user dto
	 */

	@PostMapping(value = { "/applicationUsers/{username}", "/applicationUsers/{username}/" })
	public ApplicationUserDto createApplicationUser(@PathVariable("username") String username,
			@RequestParam(name = "password") String password, 
			@RequestParam(name = "fullname") String fullname, 
			@RequestParam(name = "address") String address)
		throws IllegalArgumentException {
		
		ApplicationUser applicationUser = applicationUserService.createApplicationUser(username, password, fullname, address, null, null);
		return convertToDto(applicationUser);
	}
	
	/*
	 * Calling RESTful service endpoints
	 *
	 * http://localhost:8080/applicationUsers
	 *
	 * Using get query method, we can get a all application users existing in RoB
	 *
	 * @return list of application user Dtos
	 */

	@GetMapping(value = { "/applicationUsers", "/applicationUsers/" })
	public List<ApplicationUserDto> getAllApplicationUsers() {
		List<ApplicationUserDto> applicationUserDtos = new ArrayList<>();
		for (ApplicationUser applicationUser : applicationUserService.getAllApplicationUsers()) {
			applicationUserDtos.add(convertToDto(applicationUser));
		}
		return applicationUserDtos;
	}
	
	/*
	 * http://localhost:8080/applicationUsers/{username}
	 *
	 * get application user by their username
	 *
	 * @return application user dto
	 */

	@GetMapping(value = { "/applicationUsers/{username}", "/applicationUsers/{username}" })
	public ApplicationUserDto getApplicationUserByUsername(@PathVariable("username") String username)
			throws IllegalArgumentException {
			return convertToDto(applicationUserService.getApplicationUserByUsername(username));
	}


	@DeleteMapping(value = { "/applicationUsers/{username}", "/applicationUsers/{username}" })
	public void deleteApplicationUser(@PathVariable("username") String username) throws IllegalArgumentException {
		ApplicationUser aApplicationUser = applicationUserService.getApplicationUserByUsername(username);
		applicationUserService.deleteApplicationUser(aApplicationUser);
	}

	/* This is the method to call to delete account */
	public static void deleteAccount(String username, String password) throws InvalidInputException{
		ApplicationUserController appUserController = new ApplicationUserController();
		ApplicationUserDto aApplicationUser = appUserController.getApplicationUserByUsername(username);
		if(aApplicationUser == null){
			throw new InvalidInputException("Cannot delete unexisting account.");
		}
		if(password==null || password.length()==0 || !password.equals(aApplicationUser.getPassword())){
			throw new InvalidInputException("Correct password must be entered to delete account.");
		}else{
			appUserController.deleteApplicationUser(username);
		}
	}
	
			
	public static ApplicationUser createUser(String username, String password) throws InvalidInputException{
		
		Rob rob = RobApplication.getRob(); 
		ApplicationUser u = null;
		
		for (ApplicationUser user : rob.getExistingUsers()) {
			if (user.getUsername().equals(username)) {
				throw new InvalidInputException("Username is taken");
			}
		}
		
		u = new ApplicationUser(username, password);
		
		rob.addCurrentExistingUser(u);
		
		return u;

	}
	
	
	public static ApplicationUser logInUser(String username, String password) throws InvalidInputException{

		Rob rob = RobApplication.getRob(); 
		ApplicationUser u = null;

		
		for (ApplicationUser user : rob.getExistingUsers()) {
			if (user.getPassword().equals(password) && user.getUsername().equals(username)) {
				u = user;
				rob.addCurrentLoggedInUser(user);
			}
		}
		
		if (u == null) {
			throw new InvalidInputException("Username and password do not match");
		}
		return u;

	}
	

	public static ApplicationUser logOutUser(String username) throws InvalidInputException{

		Rob rob = RobApplication.getRob(); 
		ApplicationUser u = null;

		
		for (ApplicationUser user : rob.getCurrentLoggedInUsers()) {
			if (user.getUsername().equals(username)) {
				u = user;
				rob.removeCurrentLoggedInUser(user);
			}
		}
		
		if (u == null) {
			throw new InvalidInputException("This user is not logged in");
		}
		return u;
	}







	
	
	/*
	 * convert to dtos
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
	
	/*
	 * convert to dto lists
	 */
	
	private List<ItemInCartDto> createItemInCartDtosForCart(List<ItemInCart> itemsInCart) {
		
		List<ItemInCartDto> itemsInCartForCart = new ArrayList<>();
		
			for (ItemInCart itemInCart : itemsInCart) {
				itemsInCartForCart.add(convertToDto(itemInCart));
			}
		
		return itemsInCartForCart;
	}

	private List<ItemDto> createItemDtosForAppUser(List<Item> itemsFormAppUser) {
		
		List<ItemDto> itemsForAppUser = new ArrayList<>();
		
		for (Item item : itemsFormAppUser) {
			itemsForAppUser.add(convertToDto(item));
		}
		
		return itemsForAppUser;
	}

    
}
