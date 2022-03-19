Feature: Delete a reservation
    As an application user, i want to delete a reservation when it is no longer needed so the reserved item can be bought or reserved by the others

Background:  
  Given a Rob application exists
  Given the following application users login in the system:
    | username | password | fullname | address  |
    | User1    | abcd     | Alice    | Montreal |
    | User2    | apple    | Bob      | Quebec   |

  Given the following reservation assigned to different user:
    | username | reservationId |
    | User1    | 001          |
    | User2    | 002          |
######################################################################
Scenario: Delete successfully
    When the user with username "User1" tries to delete the reservation with reservationId "001"
    Then the reservation with Id "001" should be successfully deleted

######################################################################
Scenario: Delete failed caused by deletion on other's reservation
    When the user with username "User2" tries to delete the reservation with reservationId "001"
    Then an error message "You can only delete your reservations" shall be raised

######################################################################
Scenario: Delete a non-existing reservation
    When the user with username "User1" tries to delete the reservation with reservationId "999"
    Then an error message "reservation does not exist" shall be raised