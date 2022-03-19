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
    private ApplicationUser user;
    private int reservationId;

    public Reservation(Item item, ApplicationUser user){
        this.item=item;
        this.user=user;
    }

    @Id
    public int getReservationId(){
        return this.reservationId;
    }


}
