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

    @Transactional
    public Reservation getReservationByReservationId(int reservationId){
        Reservation aRerservation = reservationRepository.findReservationByReservationId(reservationId);
        return aRerservation;
    }
    @Transactional
    public void deleteReservation(Reservation reservation){
        reservationRepository.delete(reservation);
    }
}
