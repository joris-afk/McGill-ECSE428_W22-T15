package ca.mcgill.ecse428.RentOrBuy.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.RentOrBuy.model.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, String>{
    Purchase findPurchaseByOrderId(String orderId);
}
