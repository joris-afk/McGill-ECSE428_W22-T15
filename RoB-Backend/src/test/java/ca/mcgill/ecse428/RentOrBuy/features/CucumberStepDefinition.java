package ca.mcgill.ecse428.RentOrBuy.features;

import java.util.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import static org.junit.Assert.*;

import ca.mcgill.ecse428.RentOrBuy.*;
import ca.mcgill.ecse428.RentOrBuy.model.*;

public class CucumberStepDefinition {

	private Rob rob;
	private ApplicationUser currentLoginUaser;
	private ApplicationUser[] loginUsers;
	private String errorMsg;
	private int errorCount;


// Background

	@Given("a Rob applicarion exists")
	public void a_rob_application_exists() {
		// TODO
		// rob = RobApplication.getRob();
		// rob.delete();
		errorMsg = "";
		errorCount = 0;
	}

	@Given("the following application users exist in the system:")
	public void the_following_application_users_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
		// List<Map<String,String>> existingUsers = dataTable.asMaps(String.class,String.class);
		// for (Map<String,String> aUser : existingUsers){
		// 	if(ApplicationUser.existWithUsername(aUser.get("username")) == false){
		// 		// create a new user with the provided info if such an user does not already exist in the system
		// 		// TODO
		// 		// implement ApplicationUser.java in model
		// 		// whose constructor takes username, password, fullname, address as input (all are String)
		// 		ApplicationUser newUser = new ApplicationUser(aUser.get("username"), aUser.get("password"), aUser.get("fullname"), aUser.get("address"));
		// 	}
		// }
	}


// Login.feature

	@When("the user tries to log in with username {string} and password {string}")
	public void the_user_tries_to_log_in_with_username_and_password(String string, String string2) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("the user should be successfully logged in")
	public void the_user_should_be_successfully_logged_in() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("the user should not be logged in")
	public void the_user_should_not_be_logged_in() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("an error message {string} shall be raised")
	public void an_error_message_shall_be_raised(String string) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
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
		throw new io.cucumber.java.PendingException();
	}

	@When("the user with username {string} tries to log out")
	public void the_user_with_username_tries_to_log_out(String string) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("the user should be successfully logged out")
	public void the_user_should_be_successfully_logged_out() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}


// SignUp.feature

	@Given("there is no existing username {string}")
	public void there_is_no_existing_username(String string) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@When("the user provides a new username {string} and a password {string}")
	public void the_user_provides_a_new_username_and_a_password(String string, String string2) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("a new customer account shall be created")
	public void a_new_customer_account_shall_be_created() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("the account shall have username {string} and password {string}")
	public void the_account_shall_have_username_and_password(String string, String string2) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("no new account shall be created")
	public void no_new_account_shall_be_created() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}


// Final
	@After
	 public void tearDown() {
		// TODO : implement delete() for Rob.java
		//rob.delete();
	}
}
