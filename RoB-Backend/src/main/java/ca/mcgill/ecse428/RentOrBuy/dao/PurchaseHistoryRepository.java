package ca.mcgill.ecse428.RentOrBuy.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.RentOrBuy.model.PurchaseHistory;


public interface PurchaseHistoryRepository extends CrudRepository<PurchaseHistory, String>{
    PurchaseHistory findPurchaseHistoryByHistoryOwner(String historyOwner);
}
