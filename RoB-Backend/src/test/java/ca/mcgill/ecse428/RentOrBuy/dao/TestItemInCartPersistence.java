package ca.mcgill.ecse428.RentOrBuy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse428.RentOrBuy.model.ItemInCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestItemInCartPersistence {
    
    @Autowired
	private ItemInCartRepository IICrepository;
	
	@AfterEach
	public void clearDataBase() {
		IICrepository.deleteAll();
	}

    @Test
	@Transactional
	public void testPersistAndLoadIIC() {

        ItemInCart aIIC = new ItemInCart();

        

    }
}
