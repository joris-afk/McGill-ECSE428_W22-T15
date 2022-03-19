package ca.mcgill.ecse428.RentOrBuy.dto;

public class ReservationDto {
    private ItemDto item;
    private ApplicationUserDto user;
    private int reservationId;

    public ReservationDto(){}
    public ReservationDto(int reservationId, ApplicationUserDto user,ItemDto item){
        this.reservationId=reservationId;
        this.user=user;
        this.item=item;
    }
}
