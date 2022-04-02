package ca.mcgill.ecse428.RentOrBuy.dto;


import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryDto {
    private String historyOwner;
    private List<PurchaseDto> purchases;


    public PurchaseHistoryDto(){
        purchases = null;
    }

    public PurchaseHistoryDto(String historyOwner){
        this.historyOwner=historyOwner;
        purchases = new ArrayList<PurchaseDto>();
    }

    public String getHistoryOwner(){
        return this.historyOwner;
    }

    public List<PurchaseDto> getPurchases(){
        return purchases;
    }
   

}
