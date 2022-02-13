package ca.mcgill.ecse428.RentOrBuy.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.RentOrBuy.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Integer>{
    
    Cart findCartByCartId(Integer cartId);
}
