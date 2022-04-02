package ca.mcgill.ecse428.RentOrBuy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse428.RentOrBuy.model.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestItemPersistence {
    
    @Autowired
	private ItemRepository itemrepository;

    @Autowired
	private ApplicationUserRepository AUrepository;

    @Autowired
	private ItemInCartRepository IICrepository;
	
	@AfterEach
	public void clearDataBase() {
        AUrepository.deleteAll();
        IICrepository.deleteAll();
        itemrepository.deleteAll();
	}

    @Test
	@Transactional
	public void testPersistAndLoadItem() {
        clearDataBase();
        Item aItem = new Item();

        String name = "Garden Hose";
        double price = 39.50;
        List<String> availableSizes = Arrays.asList("Small","Medium","Large");

        aItem.setName(name);
        aItem.setPrice(price);
        aItem.setAvailableSizes(availableSizes);

        itemrepository.save(aItem);


        aItem = null; //clear variable

        //clearDataBase();
        aItem = itemrepository.findItemByName(name);


        assertNotNull(aItem);
        assertEquals(name, aItem.getName());
        assertEquals(price, aItem.getPrice());
        assertEquals(availableSizes, aItem.getAvailableSizes());
    }

    
}