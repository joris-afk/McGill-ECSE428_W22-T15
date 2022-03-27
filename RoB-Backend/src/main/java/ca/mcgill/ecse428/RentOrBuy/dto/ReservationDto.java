package ca.mcgill.ecse428.RentOrBuy.dto;

public class ReservationDto {
    private ItemDto item;
    private int quantity;
    //private ApplicationUserDto user;
    private long reservationId;

    public ReservationDto(){}
    public ReservationDto(long reservationId,ItemDto item){
        this.reservationId=reservationId;
        //this.user=user;
        this.item=item;
        this.quantity=1;
    }

    public ReservationDto(long reservationId,ItemDto item, int quantity){
        this.reservationId=reservationId;
        //this.user=user;
        this.item=item;
        this.quantity=quantity;
    }

    public ItemDto getItem(){
        return item;
    }

    // public ApplicationUserDto getUser(){
    //     return user;
    // }

    public long getReservationId(){
        return reservationId;
    }

    public int getQuantity(){
        return quantity;
    }
}
