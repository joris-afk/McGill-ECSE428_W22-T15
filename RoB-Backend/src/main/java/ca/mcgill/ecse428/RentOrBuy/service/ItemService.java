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
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Transactional
	public Item createItem(String name, double price, List<String> availableSizes) {
		
		Item item = null;
		
		if(name == null || name.equals("")) {
			throw new IllegalArgumentException("Item name can't be empty");
		}
		
		if (price<0) {
			throw new IllegalArgumentException("Item price can't be negative");
		}
		
		if (availableSizes == null || availableSizes.isEmpty()) {
			throw new IllegalArgumentException("Must specify one or more available sizes");
		}
		
		item = new Item();
		item.setAvailableSizes(availableSizes);
		item.setName(name);
		item.setPrice(price);
		
		itemRepository.save(item);
		return item;
	}
	
	@Transactional
	public Item editItemSizes(Item item, List<String> availableSizes) {
		
		if (item == null) {
			throw new IllegalArgumentException("Can't edit null item");
		}
		if (availableSizes == null || availableSizes.isEmpty()) {
			throw new IllegalArgumentException("Must specify one or more available sizes");
		}
		
		item.setAvailableSizes(availableSizes);
		itemRepository.save(item);
		return item;
	}
	
	
	@Transactional
	public Item addItemSizes(Item item, List<String> availableSizes) {
		
		if (item == null) {
			throw new IllegalArgumentException("Can't edit null item");
		}
		if (availableSizes == null || availableSizes.isEmpty()) {
			throw new IllegalArgumentException("Must specify one or more available sizes");
		}
		
		item.addAvailableSizes(availableSizes);
		itemRepository.save(item);
		return item;
	}
	
	@Transactional
	public Item removeItemSizes(Item item, List<String> unavailableSizes) {
		
		if (item == null) {
			throw new IllegalArgumentException("Can't edit null item");
		}
		if (unavailableSizes == null || unavailableSizes.isEmpty()) {
			throw new IllegalArgumentException("Must specify one or more unavailable sizes");
		}
		
		item.removeAvailableSizes(unavailableSizes);
		itemRepository.save(item);
		return item;
	}
	
	@Transactional
	public Item editItemName(Item item, String name) {
		
		if (item == null) {
			throw new IllegalArgumentException("Can't edit null item");
		}
		if(name == null || name.equals("")) {
			throw new IllegalArgumentException("Item name can't be empty");
		}
		
		
		item.setName(name);
		itemRepository.save(item);
		return item;
	}
	
	@Transactional
	public Item editItemPrice(Item item, double price) {
		
		if (item == null) {
			throw new IllegalArgumentException("Can't edit null item");
		}
		if (price<0) {
			throw new IllegalArgumentException("Item price can't be negative");
		}
		
		
		item.setPrice(price);
		itemRepository.save(item);
		return item;
	}
	
}