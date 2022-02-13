package ca.mcgill.ecse428.RentOrBuy.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.RentOrBuy.model.Item;

public interface ItemRepository extends CrudRepository<Item, String> {
    
    Item findItemByName(String name);
}
