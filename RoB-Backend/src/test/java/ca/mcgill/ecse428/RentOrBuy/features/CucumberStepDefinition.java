package ca.mcgill.ecse428.RentOrBuy.features;

import java.util.*;

import org.mockito.internal.stubbing.answers.ThrowsException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import static org.junit.Assert.*;

import ca.mcgill.ecse428.RentOrBuy.*;
import ca.mcgill.ecse428.RentOrBuy.controller.ApplicationUserController;
import ca.mcgill.ecse428.RentOrBuy.model.*;

public class CucumberStepDefinition {

	private Rob rob;
	private ApplicationUser currentLoginUser;
	private List<ApplicationUser> loginUsers;
	private List<ApplicationUser> users;
	private String errorMsg;
	private int totalUsers;


// Background

	@Given("a Rob applicarion exists")
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
		 		ApplicationUser newUser = new ApplicationUser(aUser.get("username"), aUser.get("password"), aUser.get("fullname"), aUser.get("address"));
		 		rob.addCurrentExistingUser(newUser); //this should be controller.createUser !!!
		 		ApplicationUserController.logInUser(aUser.get("username"), aUser.get("password"));
		 		this.users.add(newUser);
		 		this.loginUsers.add(newUser);
		 }
		
	}

	@When("the user with username {string} tries to log out")
	public void the_user_with_username_tries_to_log_out(String string) {
		// Write code here that turns the phrase above into concrete actions

		try {
			ApplicationUser user = ApplicationUserController.logOutUser(string); //use controller login method
			System.out.println("hello");

			if (loginUsers.contains(user)) {
				System.out.println("containts");
				loginUsers.remove(user);
			}
			else System.out.println("doesnt contain..");
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
		assertEquals(users.size(), 1);
	}

	@Then("the account shall have username {string} and password {string}")
	public void the_account_shall_have_username_and_password(String string, String string2) {
		assertEquals(users.get(0).getUsername(), string);
		assertEquals(users.get(0).getPassword(), string2);
	}

	@Then("no new account shall be created")
	public void no_new_account_shall_be_created() {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(users.size(), 0);
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
		Integer price = Integer.parseInt(string3);

		Item newItem = new Item(username, name, (double) price); //use parameters to create new item

		currentLoginUser.addItem(newItem); //add new item to list of items

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

		Item newItem = new Item(username, name, (double) price);

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
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
// Final
	@After
	 public void tearDown() {
		rob.delete();
	}
}
