package ca.mcgill.ecse428.RentOrBuy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.RentOrBuy.dao.*;
import ca.mcgill.ecse428.RentOrBuy.model.*;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ApplicationUserRepository applicationuserRepository;

    @Transactional
    public Reservation createReservation(Item item, ApplicationUser user, int quantity, long reservationId){
        if (item == null){
            throw new IllegalArgumentException("Please specify an item");
        }
        if (user == null){  //hypothetically this can't happen unless the user is not logged in
            throw new IllegalArgumentException("Please specify a buyer");
        }
        if (quantity < 0){
            throw new IllegalArgumentException("Please specify a positive quantity");
        }

        //confirm that it is unique to the item
        List<Reservation> allReservations = getAllReservations();
        for (Reservation aReservation : allReservations){
			if (item.getName().equals(aReservation.getItem().getName())){
				throw new IllegalArgumentException("Item is already reserved");
			}
		}
        
        Reservation aReservation = new Reservation();
        aReservation.setItem(item);
        aReservation.setQuantity(quantity);
        //aReservation.setUser(user);
        aReservation.setReservationId(reservationId);
        user.getReservations().add(aReservation);
        reservationRepository.save(aReservation);
        applicationuserRepository.save(user);
        return aReservation;
    }

    @Transactional
	public List<Reservation> getAllReservations(){
		return (List<Reservation>) reservationRepository.findAll();
	}

    @Transactional
    public Reservation getReservationByReservationId(long reservationId){
        Reservation aRerservation = reservationRepository.findReservationByReservationId(reservationId);
        return aRerservation;
    }

    @Transactional
    public void deleteReservation(Reservation reservation,ApplicationUser user){
        
        user.getReservations().remove(reservation);
        applicationuserRepository.save(user);
        reservationRepository.delete(reservation);
    }

    //NO UPDATE FOR NOW, JUST DELETE AND ADD AGAIN
}
