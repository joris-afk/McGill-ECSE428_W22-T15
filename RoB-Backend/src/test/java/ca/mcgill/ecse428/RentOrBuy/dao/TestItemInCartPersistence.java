package ca.mcgill.ecse428.RentOrBuy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse428.RentOrBuy.model.Item;
import ca.mcgill.ecse428.RentOrBuy.model.ItemInCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestItemInCartPersistence {
    
    @Autowired
	private ItemInCartRepository IICrepository;
	
    @Autowired
    private ItemRepository itemRepository;

	@AfterEach
	public void clearDataBase() {
		IICrepository.deleteAll();
        itemRepository.deleteAll();
	}

    @Test
	@Transactional
	public void testPersistAndLoadIIC() {

        ItemInCart aIIC = new ItemInCart();

        Item item = new Item();
        item.setName("American flag");
        itemRepository.save(item);
        
	    int quantity = 420;
	    String size = "Small";
	    Integer itemInCartId = 12345;

        aIIC.setItem(item);
        aIIC.setQuantity(quantity);
        aIIC.setSize(size);
        aIIC.setItemInCartId(itemInCartId);

        IICrepository.save(aIIC);

        aIIC = null;

        aIIC = IICrepository.findItemInCartByItemInCartId(itemInCartId);

        assertNotNull(aIIC);
        assertEquals(item.getName(), aIIC.getItem().getName());
        assertEquals(quantity, aIIC.getQuantity());
        assertEquals(size, aIIC.getSize());
        assertEquals(itemInCartId, aIIC.getItemInCartId());
    }

}
