package ca.mcgill.ecse428.RentOrBuy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse428.RentOrBuy.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestApplicationUserPersistence {
    
    @Autowired
	private ApplicationUserRepository AUrepository;
	
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PurchaseHistoryRepository PHRepository;

	@AfterEach
	public void clearDataBase() {
		AUrepository.deleteAll();
        cartRepository.deleteAll();
        itemRepository.deleteAll();
        PHRepository.deleteAll();
	}

    @Test
	@Transactional
	public void testPersistAndLoadAU() {
        clearDataBase();
        ApplicationUser aAppUser = new ApplicationUser();

        String username = "Terminator";
	    String password = "qwerty123";
	    String fullname = "Arnold Schwartz";
	    String address = "THE LAND OF FREEDOM";

	    Cart cart = new Cart();
        cart.setCartId(12345);

        cartRepository.save(cart);

	    List<Item> items = new ArrayList<Item>();
        Item i1 = new Item("flippers",39.50,Arrays.asList("small","medium"), false, false);
        Item i2 = new Item("gun",34.50,Arrays.asList("large"), false, false);
        Item i3 = new Item("girbil",28.50,Arrays.asList("universal"), false, false);
        items.add(i1);
        items.add(i2);
        items.add(i3);
        itemRepository.saveAll(items);

        PurchaseHistory ph = new PurchaseHistory(username+"'s history");
        PHRepository.save(ph);


        aAppUser.setUsername(username);
        aAppUser.setPassword(password);
        aAppUser.setFullname(fullname);
        aAppUser.setAddress(address);
        aAppUser.setCart(cart);
        aAppUser.setItems(items);
        aAppUser.setPurchases(ph);

        AUrepository.save(aAppUser);

        aAppUser = null;

        //clearDataBase();
        aAppUser = AUrepository.findApplicationUserByUsername(username);

        assertNotNull(aAppUser);
        assertEquals(username, aAppUser.getUsername());
        assertEquals(password, aAppUser.getPassword());
        assertEquals(fullname, aAppUser.getFullname());
        assertEquals(address, aAppUser.getAddress());
        assertEquals(cart.getCartId(), aAppUser.getCart().getCartId());
        
        for (int i=0;i<items.size();i++){
            assertEquals(items.get(i).getName(), aAppUser.getItems().get(i).getName());
        }
    }
    
}