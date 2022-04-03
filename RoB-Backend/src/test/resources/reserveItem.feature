Feature: Create a reservation
    As an application user, i want to create a reservation so I can reserve an item I want

Background:  
  Given a Rob application exists
  Given the following application users login in the system:
    | username | password | fullname | address  |
    | User1    | abcd     | Alice    | Montreal |
    | User2    | apple    | Bob      | Quebec   |

  Given the following application items exist in the system:
    | name              | price | availableSizes |
    | couch from Alice  | 10.50 | universal      |
    | couch from Bob    | 15.75 | small          |
######################################################################
Scenario: Create successfully
    When the user with username "User1" tries to reserve "couch from Alice" number of "1" with reservationId "001" 
    Then the reservation with Id "001" should be successfully added

######################################################################
Scenario: Create failed because not logged in User
    When the user with username "User4" tries to reserve "couch from Alice" number of "1" with reservationId "001"
    Then an error message "You must be logged in" shall be raised

######################################################################
Scenario: Create failed because non existent item
    When the user with username "User4" tries to reserve "couch from Joris" number of "1" with reservationId "099"
    Then an error message "item does not exist" shall be raised