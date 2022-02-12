package ca.mcgill.ecse428.RentOrBuy.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.RentOrBuy.model.Item;
import ca.mcgill.ecse428.RentOrBuy.model.ItemInCart;

public interface ItemInCartRepository extends CrudRepository<ItemInCart, Item>{
    
    ItemInCart findItemInCartbyItem(Item item);

}
