package ca.mcgill.ecse428.RentOrBuy.dto;


import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryDto {
    private String historyOwner;
    private List<PurchaseDto> purchases;


    public PurchaseHistoryDto(){
        purchases = null;
    }

    public PurchaseHistoryDto(String historyOwner, List<PurchaseDto> purchases){
        this.historyOwner=historyOwner;
        this.purchases = purchases;
    }

    public String getHistoryOwner(){
        return this.historyOwner;
    }

    public List<PurchaseDto> getPurchases(){
        return purchases;
    }
   

}
