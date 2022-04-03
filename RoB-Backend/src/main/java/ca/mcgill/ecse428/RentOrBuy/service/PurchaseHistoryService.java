package ca.mcgill.ecse428.RentOrBuy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse428.RentOrBuy.dao.*;
import ca.mcgill.ecse428.RentOrBuy.model.*;

@Service
public class PurchaseHistoryService {
    
    @Autowired
	private PurchaseRepository purchaseRepository;

	@Autowired
	private PurchaseHistoryRepository PHRepository;

    @Transactional
    public PurchaseHistory createPurchaseHistory(String historyOwner, List<Purchase> purchases){
        PurchaseHistory PH = null;
        PH = new PurchaseHistory();

        PH.setHistoryOwner(historyOwner);
        if (purchases == null) {
			purchases = new ArrayList<Purchase>();
		}
        PH.setPurchases(purchases);

        PHRepository.save(PH);

        return PH;
    }

    @Transactional
    public PurchaseHistory addNewPurchase(PurchaseHistory PH, Purchase purchase){
        if (purchase == null){
            throw new IllegalArgumentException("invalid purchase");
        }
        PH.addPurchase(purchase);
        PHRepository.save(PH);
        return PH;
    }

    //Not exactly a normal thing to remove but just in case
    @Transactional
    public PurchaseHistory removeItemFromHistory(PurchaseHistory PH, Purchase purchase) {
		PH.removePurchase(purchase);
        purchaseRepository.delete(purchase);
		PHRepository.save(PH);
		return PH;
	}

    @Transactional
    public PurchaseHistory deleteHistory(PurchaseHistory aPH){
        PHRepository.delete(aPH);
        for(Purchase p: aPH.getPurchases()){
            purchaseRepository.delete(p);
        }
        return null;
    }

    @Transactional
	public PurchaseHistory getPurchaseHistoryByHistoryOwner(String historyOwner){
		return PHRepository.findPurchaseHistoryByHistoryOwner(historyOwner);
	}
}
