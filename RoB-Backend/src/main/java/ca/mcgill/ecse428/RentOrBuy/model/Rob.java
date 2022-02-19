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
    
    public Rob getRob() {
    	return this;
    }

	public List<ApplicationUser> getCurrentLoggedInUsers() {
		return currentLoggedInUsers;
	}
	
	public void addCurrentLoggedInUser(ApplicationUser applicationUser) {
		this.currentLoggedInUsers.add(applicationUser);
	}
	
	public void removeCurrentLoggedInUser(ApplicationUser applicationUser) {
		this.currentLoggedInUsers.remove(applicationUser);
	}
	
	public void setCurrentLoggedInUsers(List<ApplicationUser> currentLoggedInUsers) {
		this.currentLoggedInUsers = currentLoggedInUsers;
	}

	public List<ApplicationUser> getExistingUsers() {
		if (existingUsers==null){
			existingUsers=new ArrayList<ApplicationUser>();
		}
		return existingUsers;
	}

	public void setExistingUsers(List<ApplicationUser> existingUsers) {
		this.existingUsers = existingUsers;
	}
	
	public void addCurrentExistingUser(ApplicationUser applicationUser) {
		if (existingUsers==null){
			existingUsers=new ArrayList<ApplicationUser>();
		}
		this.existingUsers.add(applicationUser);
	}
	
	public ApplicationUser logInUser(String user, String pass) {
		for (ApplicationUser u : existingUsers) {
			if (u.getPassword().equals(pass) && u.getUsername().equals(user)) {
				currentLoggedInUsers.add(u);
				return u;
			}
		}
		return null;
	}
	
	
	public void delete() {
		for (ApplicationUser a : existingUsers) {
			a = null;
		}
		existingUsers = null;
		currentLoggedInUsers = null;
	}
	
}
