package ca.mcgill.ecse428.RentOrBuy.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.RentOrBuy.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer>{
    Reservation findReservationByReservationId(Integer reservationId);
}
