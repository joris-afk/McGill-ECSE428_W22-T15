package ca.mcgill.ecse428.RentOrBuy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse428.RentOrBuy.model.ApplicationUser;
import ca.mcgill.ecse428.RentOrBuy.model.Cart;
import ca.mcgill.ecse428.RentOrBuy.model.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
public class TestApplicationUserPersistence {
    /*
    @Autowired
	private ApplicationUserRepository AUrepository;
	
	@AfterEach
	public void clearDataBase() {
		AUrepository.deleteAll();
	}

    @Test
	@Transactional
	public void testPersistAndLoadAU() {

        ApplicationUser aAppUser = new ApplicationUser();

        String username = "Terminator";
	    String password = "qwerty123";
	    String fullname = "Arnold Schwartz";
	    String address = "THE LAND OF FREEDOM";
	    Cart cart = new Cart();
	    List<Item> items = new ArrayList<Item>();

        aAppUser.setUsername(username);
        aAppUser.setPassword(password);
        aAppUser.setFullname(fullname);
        aAppUser.setAddress(address);
        aAppUser.setCart(cart);
        aAppUser.setItems(items);

        AUrepository.save(aAppUser);

        aAppUser = null;

        aAppUser = AUrepository.findApplicationUserByUsername(username);

        assertNotNull(aAppUser);
        assertEquals(username, aAppUser.getUsername());
        assertEquals(password, aAppUser.getPassword());
        assertEquals(fullname, aAppUser.getFullname());
        assertEquals(address, aAppUser.getAddress());
        assertEquals(cart, aAppUser.getCart());
        assertEquals(items, aAppUser.getItems());
    }
    */
}