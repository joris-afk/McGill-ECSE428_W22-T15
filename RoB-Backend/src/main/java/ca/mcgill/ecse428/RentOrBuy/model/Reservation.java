package ca.mcgill.ecse428.RentOrBuy.model;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;


@Entity
public class Reservation {
    private Item item;
    private int quantity;
    //private ApplicationUser user;
    private long reservationId;

    public Reservation(){}

    public Reservation(Item item){
        this.item=item;
        //this.user=user;
        this.quantity=1;
    }

    public Reservation(Item item, int quantity){
        this.item=item;
        //this.user=user;
        this.quantity=quantity;
    }

    public Reservation(Long reservationId){
        this.reservationId=reservationId;
        //this.user=user;
    }

    @Id
    public long getReservationId(){
        return this.reservationId;
    }

    public void setReservationId(long reservationId){
        this.reservationId=reservationId;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    public Item getItem(){
        return this.item;
    }

    public void setItem(Item item){
        this.item=item;
    }

    // @OneToOne(cascade = {CascadeType.ALL})
    // public ApplicationUser getUser(){
    //     return this.user;
    // }

    // public void setUser(ApplicationUser user){
    //     this.user=user;
    // }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

}
