package ca.mcgill.ecse428.RentOrBuy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.RentOrBuy.model.*;
import ca.mcgill.ecse428.RentOrBuy.service.*;
import ca.mcgill.ecse428.RentOrBuy.InvalidInputException;
import ca.mcgill.ecse428.RentOrBuy.RobApplication;
import ca.mcgill.ecse428.RentOrBuy.dto.*;


@CrossOrigin(origins = "*")
@RestController
public class ReservationController {
    
}
