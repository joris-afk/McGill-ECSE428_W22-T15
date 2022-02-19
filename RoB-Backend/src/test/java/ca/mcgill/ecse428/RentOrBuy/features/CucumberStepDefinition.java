package ca.mcgill.ecse428.RentOrBuy.features;

import java.util.*;

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
	public void the_following_application_users_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		
		if(users == null) {
			users = new ArrayList<ApplicationUser>();
		}
		List<Map<String,String>> existingUsers = dataTable.asMaps(String.class,String.class);
		for (Map<String,String> aUser : existingUsers){
		 	//if(Rob.existWithUsername(aUser.get("username")) == false){ ..add this method
		 		// create a new user with the provided info if such an user does not already exist in the system
		 		ApplicationUser newUser = new ApplicationUser(aUser.get("username"), aUser.get("password"), aUser.get("fullname"), aUser.get("address"));
		 		users.add(newUser);
		 }
	}


// Login.feature

	@When("the user tries to log in with username {string} and password {string}")
	public void the_user_tries_to_log_in_with_username_and_password(String string, String string2) {
		// Write code here that turns the phrase above into concrete actions
		try {
			ApplicationUser currentLoginUser = ApplicationUserController.logInUser(string, string2); //use controller login method
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
	public void the_following_application_users_login_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		

		List<Map<String,String>> existingUsers = dataTable.asMaps(String.class,String.class);
		for (Map<String,String> aUser : existingUsers){
		 	//if(Rob.existWithUsername(aUser.get("username")) == false){ ..add this method
		 		// create a new user with the provided info if such an user does not already exist in the system
		 		ApplicationUser newUser = new ApplicationUser(aUser.get("username"), aUser.get("password"), aUser.get("fullname"), aUser.get("address"));
		 		users.add(newUser);
		 		loginUsers.add(newUser);
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
		}	
	}

	@Then("the user should be successfully logged out")
	public void the_user_should_be_successfully_logged_out() {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(loginUsers.size(), 0);
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
    ApplicationUserController appc=new ApplicationUserController();
	int i=appc.getAllApplicationUsers().size();
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

// Final
	@After
	 public void tearDown() {
		rob.delete();
	}
}
