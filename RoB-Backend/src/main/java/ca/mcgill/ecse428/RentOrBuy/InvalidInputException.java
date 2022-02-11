package ca.mcgill.ecse428.RentOrBuy;

public class InvalidInputException extends Exception{
    
    public InvalidInputException(String errorMessage) {
		super(errorMessage);
	}
}
