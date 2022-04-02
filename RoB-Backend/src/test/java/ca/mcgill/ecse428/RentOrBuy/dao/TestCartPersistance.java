package ca.mcgill.ecse428.RentOrBuy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse428.RentOrBuy.model.Cart;
import ca.mcgill.ecse428.RentOrBuy.model.ItemInCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestCartPersistance {
    
    @Autowired
	private CartRepository cartrepository;
	
	@AfterEach
	public void clearDataBase() {
		cartrepository.deleteAll();
	}

    @Test
	@Transactional
	public void testPersistAndLoadCart() {
        
        Cart aCart = new Cart();

        List<ItemInCart> items = new ArrayList<>();
        Integer cartID = 123; 

        aCart.setCartId(cartID);
        aCart.setCartItems(items);

        cartrepository.save(aCart);

        aCart = null;

        //clearDataBase();
        aCart = cartrepository.findCartByCartId(cartID);

        assertNotNull(aCart);
        assertEquals(cartID, aCart.getCartId());
        assertEquals(items, aCart.getCartItems());
    }

  
}
