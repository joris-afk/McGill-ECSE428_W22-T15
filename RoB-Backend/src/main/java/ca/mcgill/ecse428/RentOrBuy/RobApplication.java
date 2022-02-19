package ca.mcgill.ecse428.RentOrBuy;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.mcgill.ecse428.RentOrBuy.model.ApplicationUser;
import ca.mcgill.ecse428.RentOrBuy.model.Rob;

@RestController
@SpringBootApplication
public class RobApplication {

	private static Rob thisRob;

	public static void main(String[] args) {
		SpringApplication.run(RobApplication.class, args);
	}

	@RequestMapping("/")
	public String greeting(){
		return "Hello world!";
	}

	public static Rob getRob() {
		if (thisRob == null || (thisRob.getCurrentLoggedInUsers() == null &&thisRob.getExistingUsers() == null)) {
			thisRob = new Rob();
		}
		return thisRob;
	}
	
}