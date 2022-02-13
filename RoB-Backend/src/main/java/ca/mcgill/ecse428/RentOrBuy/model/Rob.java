package ca.mcgill.ecse428.RentOrBuy.model;

import java.util.List;
import java.util.ArrayList;

public class Rob {
	
	private List<ApplicationUser> currentLoggedInUsers;
	private List<ApplicationUser> existingUsers;
	
    public Rob() {
    	
    	if (this.currentLoggedInUsers == null) {
    		this.currentLoggedInUsers = new ArrayList<ApplicationUser>();
    	}
    	
    	if (this.existingUsers == null) {
    		this.existingUsers = new ArrayList<ApplicationUser>();
    	}
    }

//	public List<ApplicationUser> getCurrentLoggedInUsers() {
//		return currentLoggedInUsers;
//	}
//	
//	public void addCurrentLoggedInUser(ApplicationUser applicationUser) {
//		this.currentLoggedInUsers.add(applicationUser);
//	}
//	
//	public void removeCurrentLoggedInUser(ApplicationUser applicationUser) {
//		this.currentLoggedInUsers.remove(applicationUser);
//	}
//	
//	public void setCurrentLoggedInUsers(List<ApplicationUser> currentLoggedInUsers) {
//		this.currentLoggedInUsers = currentLoggedInUsers;
//	}
//
//	public List<ApplicationUser> getExistingUsers() {
//		return existingUsers;
//	}
//
//	public void setExistingUsers(List<ApplicationUser> existingUsers) {
//		this.existingUsers = existingUsers;
//	}
//	
//	public void addCurrentExistingUser(ApplicationUser applicationUser) {
//		this.existingUsers.add(applicationUser);
//	}
}
