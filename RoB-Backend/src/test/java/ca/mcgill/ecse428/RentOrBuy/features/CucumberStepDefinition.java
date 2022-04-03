package ca.mcgill.ecse428.RentOrBuy.features;

import java.util.*;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import static org.junit.Assert.*;

import ca.mcgill.ecse428.RentOrBuy.*;
import ca.mcgill.ecse428.RentOrBuy.controller.*;
import ca.mcgill.ecse428.RentOrBuy.model.*;

public class CucumberStepDefinition {

	private Rob rob;
	private ApplicationUser currentLoginUser;
	private List<ApplicationUser> loginUsers;
	private List<ApplicationUser> users;
	private List<Item> allItems;
	private Item currentItem;
	private String errorMsg;
	private int totalUsers;
	private List<Reservation> reservations;
	private List<Item> queriedItems;


	// Background

	@Given("a Rob application exists")
	public void a_rob_application_exists() {

		if(RobApplication.getRob() == null){
			rob = new Rob();
		}else{
			rob = RobApplication.getRob();
		}

		errorMsg = "";

	}

	@Given("the following application users exist in the system:")
	public void the_following_application_users_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) throws InvalidInputException {

		if(users == null) {
			users = new ArrayList<ApplicationUser>();
		}
		if(loginUsers == null) {
			loginUsers = new ArrayList<ApplicationUser>();
		}
		List<Map<String,String>> existingUsers = dataTable.asMaps(String.class,String.class);
		for (Map<String,String> aUser : existingUsers){
			//if(Rob.existWithUsername(aUser.get("username")) == false){ ..add this method
			// create a new user with the provided info if such an user does not already exist in the system
			ApplicationUser newUser = new ApplicationUser(aUser.get("username"), aUser.get("password"), aUser.get("fullname"), aUser.get("address"));
			users.add(newUser);
			rob.addCurrentExistingUser(newUser);
		}
	}


	// Login.feature

	@When("the user tries to log in with username {string} and password {string}")
	public void the_user_tries_to_log_in_with_username_and_password(String string, String string2) {
		// Write code here that turns the phrase above into concrete actions
		try {
			currentLoginUser = ApplicationUserController.logInUser(string, string2); //use controller login method
			loginUsers.add(currentLoginUser);
		} catch (InvalidInputException e) { //catch the error if does not work
			errorMsg += e.getMessage();
		}
	}

	@Then("the user should be successfully logged in")
	public void the_user_should_be_successfully_logged_in() {
		assertEquals(loginUsers.size(), 1);
		assertNotNull(currentLoginUser);
	}

	@Then("the user should not be logged in")
	public void the_user_should_not_be_logged_in() {
		assertNull(currentLoginUser);
	}

	@Then("an error message {string} shall be raised")
	public void an_error_message_shall_be_raised(String string) {
		assertTrue(errorMsg.length() > 0);
	}


	// logout.feature

	@Given("the following application users login in the system:")
	public void the_following_application_users_login_in_the_system(io.cucumber.datatable.DataTable dataTable) throws InvalidInputException {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.

		if (this.users == null) this.users = new ArrayList<ApplicationUser>();
		if (this.loginUsers == null) this.loginUsers = new ArrayList<ApplicationUser>();

		List<Map<String,String>> existingUsers = dataTable.asMaps(String.class,String.class);
		for (Map<String,String> aUser : existingUsers){
			//if(Rob.existWithUsername(aUser.get("username")) == false){ ..add this method
			// create a new user with the provided info if such an user does not already exist in the system
			//rob.addCurrentExistingUser(newUser); //this should be controller.createUser !!!
			ApplicationUser newUser = ApplicationUserController.createUser(aUser.get("username"), aUser.get("password"));
			this.users.add(newUser);
			ApplicationUserController.logInUser(aUser.get("username"), aUser.get("password"));
			this.loginUsers.add(newUser);
		}
	}

	@When("the user with username {string} tries to log out")
	public void the_user_with_username_tries_to_log_out(String string) {
		// Write code here that turns the phrase above into concrete actions

		try {
			ApplicationUser user = ApplicationUserController.logOutUser(string); //use controller login method
			if (loginUsers.contains(user)) {
				loginUsers.remove(user);
			}
		} catch (InvalidInputException e) { //catch the error if does not work
			errorMsg += e.getMessage();
			System.out.println(errorMsg);

		}	
	}

	@Then("the user should be successfully logged out")
	public void the_user_should_be_successfully_logged_out() {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(loginUsers.size(), 1);
	}


	// SignUp.feature

	@Given("there is no existing username {string}")
	public void there_is_no_existing_username(String string) {
		// Write code here that turns the phrase above into concrete actions
		for (ApplicationUser a : loginUsers) {
			if (a.getUsername().equals(string)) {
				loginUsers.remove(a);
			}
		}
		for (ApplicationUser a : users) {
			if (a.getUsername().equals(string)) {
				users.remove(a);
			}
		}
	}

	@When("the user provides a new username {string} and a password {string}")
	public void the_user_provides_a_new_username_and_a_password(String string, String string2) {
		try {
			ApplicationUser user = ApplicationUserController.createUser(string, string2); //use controller create method
			users.add(user);
		} catch (InvalidInputException e) { //catch the error if does not work
			errorMsg += e.getMessage();
		}	
	}

	@Then("a new customer account shall be created")
	public void a_new_customer_account_shall_be_created() {
		//1 initially, new one added -> 2
		assertEquals(users.size(), 2);
	}

	@Then("the account shall have username {string} and password {string}")
	public void the_account_shall_have_username_and_password(String string, String string2) {
		assertEquals(users.get(1).getUsername(), string);
		assertEquals(users.get(1).getPassword(), string2);
	}

	@Then("no new account shall be created")
	public void no_new_account_shall_be_created() {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(1, users.size());
	}


	// Delete Account
	@Given("the total number of users is {string}")
	public void the_total_number_of_users_is(String string) {
		totalUsers = Integer.parseInt(string);
	}

	@When("the user tries to delete with username {string} and password {string}")
	public void the_user_tries_to_delete_with_username_and_password(String string, String string2) {
		try {
			ApplicationUserController.deleteAccount(string, string2);
		} catch (InvalidInputException e) { //catch the error if does not work
			errorMsg += e.getMessage();
		}	
	}

	@Then("the user with username {string} should be successfully deleted")
	public void the_user_with_username_should_be_successfully_deleted(String string) {
		Rob rob= RobApplication.getRob();
		int i= rob.getExistingUsers().size();
		assertEquals(i, totalUsers-1);
	}

	@When("the user tries to delete with an unexisiting username {string}")
	public void the_user_tries_to_delete_with_an_unexisiting_username(String string) {
		try {
			ApplicationUserController.deleteAccount(string, "");
		} catch (InvalidInputException e) { //catch the error if does not work
			errorMsg += e.getMessage();
		}	
	}

	@Then("number of useres shall be {string}")
	public void number_of_useres_shall_be(String string) {

		assertEquals(totalUsers,Integer.parseInt(string) );
	}

	@When("the user tries to delte with username {string} and wrong password {string}")
	public void the_user_tries_to_delte_with_username_and_wrong_password(String string, String string2) {
		try {
			ApplicationUserController.deleteAccount(string, string2);
		} catch (InvalidInputException e) { //catch the error if does not work
			errorMsg += e.getMessage();
		}	
	}

	@Then("the number of users shall be {string}")
	public void the_number_of_users_shall_be(String string) {
		assertEquals(totalUsers,Integer.parseInt(string) );
	}

	// Edit Account

	@Given("the user is logged in to an account with username {string}")
	public void the_user_is_logged_in_to_an_account_with_username(String string) {

		for(ApplicationUser u : users) {
			if (u.getUsername().equals(string)) {
				rob.addCurrentLoggedInUser(u);
				loginUsers.add(u);
				currentLoginUser = u;
				break;
			}
		}
	}

	@When("the user tries to update account with a new username {string}")
	public void the_user_tries_to_update_account_with_a_new_username(String username) {

		try {

			ApplicationUserController.editAccountUsername(currentLoginUser.getUsername(), username);

		} catch (InvalidInputException e) {
			errorMsg += e.getMessage();
		}
	}

	@When("the user tries to update account with a new password {string}")
	public void the_user_tries_to_update_account_with_a_new_password(String password) {

		try {

			ApplicationUserController.editAccountPassword(currentLoginUser.getUsername(), password);

		} catch (InvalidInputException e) {
			errorMsg += e.getMessage();
		}
	}

	@When("the user tries to update account with a new address {string}")
	public void the_user_tries_to_update_account_with_a_new_address(String address) {

		try {

			ApplicationUserController.editAccountAddress(currentLoginUser.getUsername(), address);

		} catch (InvalidInputException e) {
			errorMsg += e.getMessage();
		}
	}

	@When("the user tries to update account with a new full name {string}")
	public void the_user_tries_to_update_account_with_a_new_full_name(String name) {

		try {

			ApplicationUserController.editAccountName(currentLoginUser.getUsername(), name);

		} catch (InvalidInputException e) {
			errorMsg += e.getMessage();
		}
	}

	@Then("the account shall have username {string}")
	public void the_account_shall_have_username(String string) {
		assertEquals(currentLoginUser.getUsername(), string);
	}

	@Then("the account shall have password {string}")
	public void the_account_shall_have_password(String string) {
		assertEquals(currentLoginUser.getPassword(), string);
	}

	@Then("the account shall have address {string}")
	public void the_account_shall_have_address(String string) {
		assertEquals(currentLoginUser.getAddress(), string);
	}

	@Then("the account shall have full name {string}")
	public void the_account_shall_have_full_name(String string) {
		assertEquals(currentLoginUser.getFullname(), string);
	}

	@When("the user with username {string} tries to add {string} with price {string}")
	public void the_user_with_username_tries_to_add_with_price(String string, String string2, String string3) {
		// Write code here that turns the phrase above into concrete actions
		try{
			String username = string;
			String name = string2;
			double priced = Double.parseDouble(string3);
			int price = (int) priced;

			Item newItem = ItemController.createItem(username, name, price); //use parameters to create new item

			for (ApplicationUser userinsystem: loginUsers){
				if (userinsystem.getUsername().equals(username)){
					this.currentLoginUser = userinsystem;
					break;
				}
			}

			if (currentLoginUser == null) {
				errorMsg = "You must log in first";
			}
			else if (currentLoginUser.getUsername().equals(username)) {
				currentLoginUser.addItem(newItem); //add new item to list of items
			}

		} catch (Exception e){
			errorMsg += e.getMessage();
			System.out.println(errorMsg);
		}
	}

	@Then("the {string} will be added successfully to the database")
	public void the_will_be_added_successfully_to_the_database(String string) {
		// Write code here that turns the phrase above into concrete actions
		ArrayList<String> itemNames = new ArrayList<String>();
		for(Item item: currentLoginUser.getItems()){
			itemNames.add(item.getName()); //adds each name to name list
		}
		assertTrue(itemNames.contains(string)); //checks whether itemList has new name
	}

	@When("the user with username {string} tries to add a duplicate {string} with price {string}")
	public void the_user_with_username_tries_to_add_a_duplicate_with_price(String string, String string2, String string3) {
		try{
			String username = string;
			String name = string2;
			Integer price = Integer.parseInt(string3);

			Item newItem = new Item(); //use parameters to create new item
			newItem.setName(name);
			newItem.setPrice(price);
			ApplicationUser user = null;
			for (ApplicationUser usersinsystem: loginUsers){
				if (usersinsystem.getUsername().equals(username)){
					user = usersinsystem;
					break;
				}
			}
			if (user != null){
				user.addItem(newItem);
			}

			for(Item item: currentLoginUser.getItems()){
				if(name.equals(item.getName()) && price == (item.getPrice())){ //if identical name and price are there dont add item
					throw new Exception();
				}
			}
			currentLoginUser.addItem(newItem);


		} catch (Exception e){
			errorMsg += e.getMessage();
			System.out.println(errorMsg);
		}
	}

	////////////////////////////////////////////////////////////////////
	///////////////////////////  EDIT ITEMS  ///////////////////////////
	////////////////////////////////////////////////////////////////////
	@Given("the following application items exist in the system:")
	public void the_following_application_items_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		if(allItems == null) {
			allItems = new ArrayList<Item>();
		}
		List<Map<String,String>> existingItems = dataTable.asMaps(String.class,String.class);
		for (Map<String,String> aItem : existingItems){
			// create a new user with the provided info if such an user does not already exist in the system
			String[] sizeArray = aItem.get("availableSizes").split(",");
			ArrayList<String> sizes = new ArrayList<>();
			for (String size:sizeArray) sizes.add(size);
			double price = Double.parseDouble(aItem.get("price"));

			Item newItem = null;
			try {
				newItem = ItemController.addItem(aItem.get("name"), price, sizes);
			} catch (Exception e){
				errorMsg += e.getMessage();
				System.out.println(errorMsg);
			}
			allItems.add(newItem);
		}
	}

	@Given("the user is looking at {string}")
	public void the_user_is_looking_at(String string) {
		for (Item i: allItems){
			if (string.equals(i.getName())){
				currentItem = i;
			}
		}
	}

	@When("the user tries to update price to a new price of {string}")
	public void the_user_tries_to_update_price_to_a_new_price_of(String string) {
		try{
			Item i = ItemController.updatePrice(currentItem, Double.parseDouble(string));
			currentItem = i;	//update pointer
			allItems = rob.getProducts();//update list
		}
		catch (Exception e){
			errorMsg += e.getMessage();
			System.out.println(errorMsg);
		}

	}

	@Then("the current item shall have price {string}")
	public void the_current_item_shall_have_price(String string) {
		assertEquals(Double.parseDouble(string),currentItem.getPrice(), 0.00001);
	}


	@Then("the current item shall have size {string}")
	public void the_current_item_shall_have_size(String string) {

		if (errorMsg.contains("Can't edit null item")) {
			assertNull(currentItem);
		}
		else assertTrue(currentItem.getAvailableSizes().contains(string) || string.isEmpty());
	}

	@When("the user tries to remove size of {string} from the current item")
	public void the_user_tries_to_remove_size_of_from_the_current_item(String string) {
		try{
			Item i = ItemController.removeItemSize(currentItem, string);
			currentItem = i;	//update pointer
			allItems = rob.getProducts();//update list
		}
		catch (Exception e){
			errorMsg += e.getMessage();
			System.out.println(errorMsg);
		}
	}

	@When("the user tries to add size of {string} to the current item")
	public void the_user_tries_to_add_size_of_to_the_current_item(String string) {
		try{
			System.out.println("items in sys: " + allItems);
			System.out.println("curr item: "+ currentItem);
			Item i = ItemController.addItemSize(currentItem, string);
			if (i != null) currentItem = i;	//update pointer
			System.out.println("curr item after update: "+ currentItem);
			allItems = rob.getProducts();//update list
		}
		catch (Exception e){
			errorMsg += e.getMessage();
			System.out.println(errorMsg);
		}
	}

	@Then("the current item shall not have size {string}")
	public void the_current_item_shall_not_have_size(String string) {
		if (errorMsg.contains("Can't edit null item")) {
			assertNull(currentItem);
		}
		else if (errorMsg.contains("Cannot add duplicate size")) {
			//one instance
			assertTrue(currentItem.getAvailableSizes().indexOf(string) == currentItem.getAvailableSizes().lastIndexOf(string));
		}
		else assertFalse(currentItem.getAvailableSizes().contains(string));
	}

	@When("the user with username {string} tries to delete {string} with price {string}")
	public void the_user_with_username_tries_to_delete_with_price(String string, String string2, String string3) {
		// Write code here that turns the phrase above into concrete actions
		try{
			String username = string;
			String name = string2;
			double priced = Double.parseDouble(string3);
			int price = (int) priced;

			Item newItem = ItemController.createItem(username, name, price); //use parameters to create new item

			for (ApplicationUser userinsystem: loginUsers){
				if (userinsystem.getUsername().equals(username)){
					this.currentLoginUser = userinsystem;
					break;
				}
			}

			boolean wasFound = false;

			if (currentLoginUser == null) {
				errorMsg = "You must log in first";
			}
			else if (currentLoginUser.getUsername().equals(username)) {
				for(Item items: currentLoginUser.getItems()){
					if(newItem.getName().equals(items.getName()) && newItem.getPrice() == items.getPrice()){
						wasFound = true;
						currentLoginUser.removeItem(newItem);; //remove new item to list of items
					}
				}

			}

		} catch (Exception e){
			errorMsg += e.getMessage();
			System.out.println(errorMsg);
		}
	}

	@Then("the {string} will be deleted successfully to the database")
	public void the_will_be_deleted_successfully_to_the_database(String string) {
		// Write code here that turns the phrase above into concrete actions
		ArrayList<String> itemNames = new ArrayList<String>();
		for(Item item: currentLoginUser.getItems()){
			itemNames.add(item.getName()); //adds each name to name list
		}
		assertTrue(!itemNames.contains(string)); //checks whether itemList has new name
	}

	@When("the user with username {string} tries to delete a non existent {string} with price {string}")
	public void the_user_with_username_tries_to_delete_a_non_existent_with_price(String string, String string2, String string3) {
		// Write code here that turns the phrase above into concrete actions
		try{
			String username = string;
			String name = string2;
			double priced = Double.parseDouble(string3);
			int price = (int) priced;

			Item newItem = ItemController.createItem(username, name, price); //use parameters to create new item

			for (ApplicationUser userinsystem: loginUsers){
				if (userinsystem.getUsername().equals(username)){
					this.currentLoginUser = userinsystem;
					break;
				}
			}

			boolean wasFound = false;

			if (currentLoginUser == null) {
				errorMsg = "You must log in first";
			}
			else if (currentLoginUser.getUsername().equals(username)) {
				for(Item items: currentLoginUser.getItems()){
					if(newItem.getName().equals(items.getName()) && newItem.getPrice() == items.getPrice()){
						wasFound = true;
						currentLoginUser.removeItem(newItem);; //remove new item to list of items
					}
				}
				if(!wasFound){
					errorMsg = "This Item does not exist";
				}
			}

		} catch (Exception e){
			errorMsg += e.getMessage();
			System.out.println(errorMsg);
		}
	}

	@Given("the following reservation assigned to different user:")
	public void the_following_reservation_assigned_to_different_user(io.cucumber.datatable.DataTable dataTable) {
		if (reservations == null){
			reservations= new ArrayList<Reservation>();
		}
		List<Map<String,String>> existingReservation=dataTable.asMaps(String.class,String.class);
		for (Map<String,String> aReservation : existingReservation){

			int i= Integer.parseInt(aReservation.get("reservationId"));
			Long l=new Long(i);
			ApplicationUser u=new ApplicationUser();
			u.setUsername(aReservation.get("username"));
			Reservation newReservation= new Reservation(l);
			reservations.add(newReservation);
			rob.addReservation(newReservation);
		}
	}

	@When("the user with username {string} tries to delete the reservation with reservationId {string}")
	public void the_user_with_username_tries_to_delete_the_reservation_with_reservation_id(String string, String string2) {
		try{
			ReservationController re=new ReservationController();
			re.deleteReservation(Integer.parseInt(string2), string);
			//reservations.remove(r);
		} catch (Exception e){
			errorMsg += e.getMessage();
		}
	}

	@Then("the reservation with Id {string} should be successfully deleted")
	public void the_reservation_with_id_should_be_successfully_deleted(String string) {
		ReservationController r = new ReservationController();
		assertNull(r.obtReservation(Integer.parseInt(string)));
	}


	/*
	 * Remove Item from Cart
	 */

	@Given("the user with username {string} has cart with ID {string} and with the following items:")
	public void the_user_with_username_has_cart_with_id_and_with_the_following_items(String string, String string2, io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
		Cart cart = null;
		try {
			cart = CartController.createCart(Integer.parseInt(string2));
		}
		catch (Exception e) {
			errorMsg += e.getMessage();
			System.out.println(errorMsg);
		}

		List<Map<String,String>> cartItems = dataTable.asMaps(String.class,String.class);
		for (Map<String,String> cartItem : cartItems){
			ItemInCart item = new ItemInCart();
			item.setItemInCartId(Integer.parseInt(cartItem.get("item_id")));
			item.setQuantity(Integer.parseInt(cartItem.get("quantity")));
			item.setSize(cartItem.get("size"));
			for (Item i : allItems) {
				if (i.getName().equals(cartItem.get("item_name"))) {
					item.setItem(i);
					break;
				}
			}
			cart = CartController.addItemToCart(cart, item);
		}
		for (ApplicationUser user: loginUsers) {
			if (user.getUsername().equals(string)) currentLoginUser = user;
		}
		currentLoginUser.setCart(cart);
	}

	@When("user with username {string} removes item with id {string}")
	public void user_with_username_removes_item_with_id(String string, String string2) {
		for (ApplicationUser user: loginUsers) {
			if (user.getUsername().equals(string)) currentLoginUser = user;
		}
		ItemInCart item = null;
		for (ItemInCart i: currentLoginUser.getCart().getCartItems()) {
			if (i.getItemInCartId() == Integer.parseInt(string2)) item = i;
		}
	    try {
	    	CartController.removeItemFromCart(currentLoginUser.getCart(), item);
	    } catch(Exception e) {
	    	errorMsg = e.getMessage();
	    }
	}

	@Then("the item with id {string} will not appear in the cart")
	public void the_item_with_id_will_not_appear_in_the_cart(String string) {
	    for (ItemInCart i : currentLoginUser.getCart().getCartItems()) {
	    	assertTrue(i.getItemInCartId() != Integer.parseInt(string));
	    }
	}

	@Then("the cart size will be {string}")
	public void the_cart_size_will_be(String string) {
		assertTrue(currentLoginUser.getCart().getCartItems().size() == Integer.parseInt(string));
	}

	@Then("no item will be removed")
	public void no_item_will_be_removed() {
		assertTrue(currentLoginUser.getCart().getCartItems().size() == 3);
	}

	@Then("an error {string} should appear")
	public void an_error_should_appear(String string) {
		assertTrue(errorMsg.contains(string));
	}

	/*
	 * Add Item to Cart
	 */

	@Given("the user with username {string} has cart with ID {string}")
	public void the_user_with_username_has_cart_with_id(String string, String string2) {
    	Cart cart = null;
		try {
			cart = CartController.createCart(Integer.parseInt(string2));
		}
		catch (Exception e) {
			errorMsg += e.getMessage();
			System.out.println(errorMsg);
		}

		for (ApplicationUser user: loginUsers) {
			if (user.getUsername().equals(string)) currentLoginUser = user;
		}

		currentLoginUser.setCart(cart);
	}

	@When("user with username {string} adds {string} item with name {string} of size {string} to their cart")
	public void user_with_username_adds_item_with_name_of_size_to_their_cart(String string, 
	String string2, String string3, String string4) {
    	for (ApplicationUser user: loginUsers) {
			if (user.getUsername().equals(string)) currentLoginUser = user;
		}

		Item targetItem = null;
		for (Item searchItem: allItems){
			if (searchItem.getName().equals(string3)) targetItem = searchItem;
		}
		try{
			currentLoginUser.setCart(CartController.addItemToCart(currentLoginUser.getCart(), targetItem, string4, Integer.parseInt(string2)));
		}
		catch(Exception e){
			errorMsg = e.getMessage();
		}
	}

	@Then("the item with name {string} will appear in the cart")
	public void the_item_with_name_will_appear_in_the_cart(String string) {
		boolean existsInCart = false;
    	for (ItemInCart iic: currentLoginUser.getCart().getCartItems()){
			if (iic.getItem().getName().equals(string)) existsInCart = true;
		}
		assertTrue(existsInCart);
	}

	@Then("the item with name {string} will not appear in the cart")
	public void the_item_with_name_will_not_appear_in_the_cart(String string) {
	    boolean existsInCart = false;
    	for (ItemInCart iic: currentLoginUser.getCart().getCartItems()){
			if (iic.getItem().getName().equals(string)) existsInCart = true;
		}
		assertFalse(existsInCart);
	}


	/*
	*
	Purchase Item
	*
	*/
	@When("user with username {string} purchases {string} item with name {string} of size {string} from their cart")
	public void user_with_username_purchases_item_with_name_of_size_from_their_cart(String string, 
	String string2, String string3, String string4) {
    	for (ApplicationUser user: loginUsers) {
			if (user.getUsername().equals(string)) currentLoginUser = user;
		}

		Item targetItem = null;
		for (Item searchItem: allItems){
			if (searchItem.getName().equals(string3)) targetItem = searchItem;
		}
		try{
			currentLoginUser.setCart(CartController.addItemToCart(currentLoginUser.getCart(), targetItem, string4, Integer.parseInt(string2)));
			Purchase p = new Purchase(currentLoginUser.getCart());
			currentLoginUser.addPurchase(p);
		}
		catch(Exception e){
			errorMsg = e.getMessage();
		}
	}

	@Then("the item with name {} will be added to purchase history")
	public void the_item_with_name_will_be_added_to_purchase_history(String string){
		boolean existsInPurchases = false;
    	for (Purchase p: currentLoginUser.getPurchases().getPurchases()){
			for(ItemInCart i: p.getCart().getCartItems()){
				if(i.getItem().getName().equals(string)){
					existsInPurchases = true;
				}
			}
		}
		assertTrue(existsInPurchases);
	}


	// Reserve Item

	@When("the user with username {} tries to reserve {} number of {} with reservationId {} ")
	public void the_user_with_username_tries_to_reserve_number_of_with_reservation_id(String string, String string2,String string3,String string4) {
		try{
			ReservationController re=new ReservationController();
			re.createReservation(Integer.parseInt(string4), string, Integer.parseInt(string3), string2);
			//reservations.remove(r);
		} catch (Exception e){
			errorMsg += e.getMessage();
		}
	}

	@Then("Then the reservation with Id {} should be successfully added")
	public void the_reservation_with_id_should_be_successfully_added(String string) {
		ReservationController r = new ReservationController();
		assertNotNull(r.obtReservation(Integer.parseInt(string)));
	}
	
	/*
	 * Search for item
	 */
	
	@When("the user with username {string} searches with keyword {string}")
	public void the_user_with_username_searches_with_keyword(String string, String string2) {
		List<Item> matchedItems = new ArrayList<Item>();
		try {
			matchedItems = ItemController.searchItems(string2);
		} catch (InvalidInputException e) {
			errorMsg = e.getMessage();
		}
		queriedItems = matchedItems;
	}

	@Then("the following items will be returned:")
	public void the_following_items_will_be_returned(io.cucumber.datatable.DataTable dataTable) {

		List<Map<String,String>> itemsReturned = dataTable.asMaps(String.class,String.class);
		boolean eachItemHasMatch;
		if (itemsReturned.size() == 0 && queriedItems.isEmpty()) eachItemHasMatch=true;
		else eachItemHasMatch = false;
		for (Map<String,String> itemReturned : itemsReturned){
			for (Item item : queriedItems) {
				if (item.getName().equals(itemReturned.get("name")) && //too lazy to check all attributes
						item.getPrice() == (Double.parseDouble(itemReturned.get("price")))) {
					eachItemHasMatch = true;
					break;
				}
			}
			assertTrue(eachItemHasMatch);
		}
	    assertEquals(itemsReturned.size(), queriedItems.size());
	}

	@Then("no items shall be returned")
	public void no_items_shall_be_returned() {
	    assertTrue(queriedItems.isEmpty());
	}


	// purchase item
	@Given("the user {string} has the following ItemInCart in his cart:")
	public void the_user_has_the_following_item_in_cart_in_his_cart(String string, io.cucumber.datatable.DataTable dataTable) {
		for(ApplicationUser appUser : RobApplication.getRob().getExistingUsers()){
			if(appUser.getUsername().equals(string)) currentLoginUser = appUser;
		}
		
		List<Map<String,String>> iics = dataTable.asMaps(String.class,String.class);
		for (Map<String,String> iic : iics){
			Integer id = Integer.valueOf(iic.get("itemInCartId"));
			int quantity = Integer.valueOf(iic.get("quantity"));
			Item item = null;
			String itemName = iic.get("itemName");
			for(Item i: allItems){
				if(itemName.equals(i.getName())){
					item = i;
					break;
				}
			}
			ItemInCart newIIC = new ItemInCart(id, item, quantity, iic.get("size"));
			currentLoginUser.addToCart(newIIC);
		}
	}
	
	private Purchase purchase;
	
	@Then("the purchase with order id {string} shall be made")
	public void the_purchase_with_order_id_shall_be_made(String string) {
		boolean foundPurchase = false;
		for(Purchase p : RobApplication.getRob().getPurchases()){
			if(string.equals(p.getOrderId())) foundPurchase = true;
		}
		assertTrue(foundPurchase);
	}

	@When("the user {string} tries to purchase items in {string} cart with order id {string}")
	public void the_user_tries_to_purchase_items_in_cart_with_order_id(String string, String string2, String string3) {
		ApplicationUser cartOwner = null;
		for(ApplicationUser appUser : RobApplication.getRob().getExistingUsers()){
			if(appUser.getUsername().equals(string)) currentLoginUser = appUser;
			if(appUser.getUsername().equals(string2)) cartOwner = appUser;
		}
		try {
			this.purchase = PurchaseController.purchaseItemInCarts(string3, currentLoginUser, cartOwner.getCart().getCartId());
		} catch (InvalidInputException e) {
			errorMsg += e.getMessage();
		}
	}
	

	
	@Then("the purchase with order id {string} shall not be made")
	public void the_purchase_with_order_id_shall_not_be_made(String string) {
		boolean foundPurchase = false;
		for(Purchase p : RobApplication.getRob().getPurchases()){
			if(string.equals(p.getOrderId())) foundPurchase = true;
		}
		assertTrue(!foundPurchase);
	}


	// Final
	@After
	public void tearDown() {
		errorMsg = "";
		rob.delete();
	}
}
