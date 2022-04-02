package ca.mcgill.ecse428.RentOrBuy.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PurchaseHistory {
    private String historyOwner;
    private List<Purchase> purchases;


    public PurchaseHistory(){
        purchases = null;
    }

    public PurchaseHistory(String historyOwner){
        this.historyOwner=historyOwner;
        purchases = new ArrayList<Purchase>();
    }

    @Id
    public String getHistoryOwner(){
        return this.historyOwner;
    }

    public void setHistoryOwner(String historyOwner){
        this.historyOwner=historyOwner;
    }

    @ElementCollection
    public List<Purchase> getPurchases(){
        return purchases;
    }

    public void setPurchases( List<Purchase> purchases){
        this.purchases = purchases;
    }

    public void addPurchase(Purchase purchase){
        if (purchases == null) { this.purchases = new ArrayList<Purchase>();}
        this.purchases.add(purchase);
    }

    public void addPurchases(List<Purchase> purchases){
        if (purchases == null) { this.purchases = new ArrayList<Purchase>();}
        this.purchases.addAll(purchases);
    }

    //Not exactly a normal thing to remove but just in case
    public void removePurchase(Purchase purchase){
        this.purchases.remove(purchase);
    }

    public void removePurchases(List<Purchase> purchases){
        this.purchases.removeAll(purchases);
    }    


}
