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
	
	/*
	 * Calling RESTful service endpoints
	 *
	 * http://localhost:8080/applicationUsers/{username}?new_username={String}&new_password={String}
	 * http://localhost:8080/applicationUsers/{username}?new_address={String}
	 * http://localhost:8080/applicationUsers/{username}?new_name={String}&new_username={String}&new_adress={String}
	 * etc...
	 * 
	 * Using put methods, we can edit an application user's account with a new username, or password, 
	 * or full name, or address.
	 * We can find the application user by their current username and edit from there.
	 *
	 * @param String username, to find the given application user object
	 * @param String new_username, new username for the user
	 * @param String new_name, new name for the user
	 * @param Strign new_password, new password for the user
	 * @param Strign new_address, new address for the user
	 */

	@PutMapping(value = { "/applicationUsers/{username}", "/applicationUsers/{username}/" })
	public void editApplicationUsers(@PathVariable("username") String username,
			@RequestParam(required = false, name = "new_username") String new_username,
			@RequestParam(required = false, name = "new_name") String new_name,
			@RequestParam(required = false, name = "new_password") String new_password,
			@RequestParam(required = false, name = "new_address") String new_address)
	
		throws IllegalArgumentException {
		
		ApplicationUser aApplicationUser = applicationUserService.getApplicationUserByUsername(username);

		if (new_username != null) {

			applicationUserService.editApplicationUserUsername(aApplicationUser, new_username);
		}

		if (new_name != null) {

			applicationUserService.editApplicationUserUsername(aApplicationUser, new_name);
		}
		
		if (new_password != null) {

			applicationUserService.editApplicationUserUsername(aApplicationUser, new_password);
		}
		
		if (new_address != null) {

			applicationUserService.editApplicationUserUsername(aApplicationUser, new_address);
		}

	}

	/* This is the method to call to delete account */
	public static void deleteAccount(String username, String password) throws InvalidInputException{
		ApplicationUser aApplicationUser = null;
		Rob rob = RobApplication.getRob(); 
		for(ApplicationUser aUser: rob.getExistingUsers()){
			if(aUser.getUsername().equals(username)){
				aApplicationUser = aUser;
				break;
			}
		}
		if(aApplicationUser == null){
			throw new InvalidInputException("Cannot delete unexisting account.");
		}
		if(password==null || password.length()==0 || !password.equals(aApplicationUser.getPassword())){
			throw new InvalidInputException("Correct password must be entered to delete account.");
		}else{
			rob.getExistingUsers().remove(aApplicationUser);
		}
	}
	
	/* This is the method to call to edit account username */
	public static void editAccountUsername(String username, String newUsername) throws InvalidInputException{
		
		ApplicationUser aApplicationUser = null;
		Rob rob = RobApplication.getRob(); 
		
		for(ApplicationUser aUser: rob.getExistingUsers()){
			if(aUser.getUsername().equals(username)){
				aApplicationUser = aUser;
				break;
			}
		}
		
		if(newUsername.length() == 0){
			throw new InvalidInputException("username cant be empty");
		}
		
		for(ApplicationUser aUser: rob.getExistingUsers()){
			if(aUser.getUsername().equals(newUsername)){
				throw new InvalidInputException("Username not available");
			}
		}
		
		aApplicationUser.setUsername(newUsername);
	}
	
	/* This is the method to call to edit account password */
	public static void editAccountPassword(String username, String newPassword) throws InvalidInputException{
		
		ApplicationUser aApplicationUser = null;
		Rob rob = RobApplication.getRob(); 
		
		for(ApplicationUser aUser: rob.getExistingUsers()){
			if(aUser.getUsername().equals(username)){
				aApplicationUser = aUser;
				break;
			}
		}
		
		if(newPassword.length() == 0){
			throw new InvalidInputException("password cant be empty");
		}
		
		aApplicationUser.setPassword(newPassword);
	}
	
	/* This is the method to call to edit account address */
	public static void editAccountAddress(String username, String newAddress) throws InvalidInputException{
		
		ApplicationUser aApplicationUser = null;
		Rob rob = RobApplication.getRob(); 
		
		for(ApplicationUser aUser: rob.getExistingUsers()){
			if(aUser.getUsername().equals(username)){
				aApplicationUser = aUser;
				break;
			}
		}
		
		if(newAddress.length() == 0){
			throw new InvalidInputException("address cant be empty");
		}
		
		aApplicationUser.setAddress(newAddress);
	}
	
	/* This is the method to call to edit account */
	public static void editAccountName(String username, String newName) throws InvalidInputException{
		
		ApplicationUser aApplicationUser = null;
		Rob rob = RobApplication.getRob(); 
		
		for(ApplicationUser aUser: rob.getExistingUsers()){
			if(aUser.getUsername().equals(username)){
				aApplicationUser = aUser;
				break;
			}
		}
		
		if(newName.length() == 0){
			throw new InvalidInputException("Full name cant be empty");
		}
		
		aApplicationUser.setFullname(newName);
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

		if (rob.getCurrentLoggedInUsers().isEmpty()) {
			throw new InvalidInputException("There are no users logged in");
		}
		
		for (ApplicationUser user : rob.getCurrentLoggedInUsers()) {
			if (user.getUsername().equals(username)) {
				u = user;
				rob.removeCurrentLoggedInUser(user);
				return user;
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
