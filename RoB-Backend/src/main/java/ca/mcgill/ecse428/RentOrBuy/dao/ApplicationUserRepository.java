package ca.mcgill.ecse428.RentOrBuy.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse428.RentOrBuy.model.ApplicationUser;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, String>{
    
    ApplicationUser findApplicationUserByUsername(String username);

    ApplicationUser findApplicationUserByUsernameAndPassword(String username,String password);
}
