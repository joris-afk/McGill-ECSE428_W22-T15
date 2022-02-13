package ca.mcgill.ecse428.RentOrBuy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.RentOrBuy.dao.*;
import ca.mcgill.ecse428.RentOrBuy.model.*;

@Service
public class ApplicationUserService {

	@Autowired
	private ApplicationUserRepository applicationUserRepository;

	@Transactional
	public ApplicationUser createApplicationUser(String username, String password, String fullname, 
			String address, Cart cart, List<Item> items) {
		ApplicationUser newApplicationUser = null;

		if(fullname == null || fullname.equals("")) {
			throw new IllegalArgumentException("Full name can't be empty");
		}

		for (char c : fullname.toCharArray()) {
			if (Character.isDigit(c)) {
				throw new IllegalArgumentException("Full name can't have numbers.");
			}
		}

		if(username == null || username.equals("")) {
			throw new IllegalArgumentException("Username can't be empty");
		}

		if(password == null || password.equals("")) {
			throw new IllegalArgumentException("Password can't be empty");
		}

		if(address == null || address.equals("")) {
			throw new IllegalArgumentException("Address can't be empty");
		}

		newApplicationUser = new ApplicationUser();
		newApplicationUser.setUsername(username);
		newApplicationUser.setPassword(password);
		newApplicationUser.setAddress(address);
		newApplicationUser.setCart(cart);
		newApplicationUser.setFullname(fullname);
		newApplicationUser.setItems(items);

		applicationUserRepository.save(newApplicationUser);

		return newApplicationUser;
	}
	
	
	
	
	

	@Transactional
	public List<ApplicationUser> getAllApplicationUsers(){
		return (List<ApplicationUser>) applicationUserRepository.findAll();
	}
	
	@Transactional
	public ApplicationUser deleteApplicationUser(ApplicationUser aApplicationUser) {
		applicationUserRepository.delete(aApplicationUser);
		aApplicationUser  = null;
		return aApplicationUser;
	}
	
	@Transactional
	public ApplicationUser getApplicationUserByUsername(String username) {
		ApplicationUser aApplicationUser = applicationUserRepository.findApplicationUserByUsername(username);
		return aApplicationUser;
	}
	
	@Transactional
	public ApplicationUser editApplicationUserFullname(ApplicationUser aApplicationUser, String fullName) {
		if(fullName == null || fullName.equals("") || fullName.equals("undefined")) {
			throw new IllegalArgumentException("Full name can't be empty");
		}
		aApplicationUser.setFullname(fullName);
		applicationUserRepository.save(aApplicationUser);
		return aApplicationUser;
	}
	
	@Transactional
	public ApplicationUser editApplicationUserUsername(ApplicationUser aApplicationUser, String username) {
		if(username == null || username.equals("") || username.equals("undefined")) {
			throw new IllegalArgumentException("username can't be empty");
		}
		aApplicationUser.setUsername(username);
		applicationUserRepository.save(aApplicationUser);
		return aApplicationUser;
	}
	
	@Transactional
	public ApplicationUser editApplicationUserPassword(ApplicationUser aApplicationUser, String password) {
		if(password == null || password.equals("") || password.equals("undefined")) {
			throw new IllegalArgumentException("password can't be empty");
		}
		aApplicationUser.setPassword(password);
		applicationUserRepository.save(aApplicationUser);
		return aApplicationUser;
	}
	
	@Transactional
	public ApplicationUser editApplicationUserAddress(ApplicationUser aApplicationUser, String address) {
		if(address == null || address.equals("") || address.equals("undefined")) {
			throw new IllegalArgumentException("address can't be empty");
		}
		aApplicationUser.setAddress(address);
		applicationUserRepository.save(aApplicationUser);
		return aApplicationUser;
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
