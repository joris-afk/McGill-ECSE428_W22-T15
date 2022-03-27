Feature: Edit item details as an applicarion user
  As an application user, I want to purchase items on my account to receive them at home.

Background:  
  Given a Rob application exists
  Given the following application users exist in the system:
    | username | password | fullname | address  |
    | User1    | abcd     | Alice    | Montreal |
    | User2    | apple    | Bob      | Quebec   |
  Given the following application items exist in the system:
    | name              | price | availableSizes |
    | couch from Alice  | 10.50 | universal      |
    | couch from Bob    | 15.75 | small          |

##########################################################################

Scenario: purchase item successfully
   Given the user is logged in to an account with username "User1"
   Given the user is looking at "couch from Alice"
   When the user tries to purchase a "couch from Alice"
   Then the current item shall be purchased
   
##########################################################################

Scenario Outline: purchase item failed
   Given the user is logged in to an account with username "User1"
   Given the user is looking at "<Item>"
   When the user tries to purchase a "<NewPrice>" with insufficient funds
   Then an error message "<error>" shall be raised
   
##########################################################################

Scenario: purchase a non existing item
   Given the user is logged in to an account with username "User1"
   Given the user is looking at "<Item>"
   When the user tries to purchase a "<Item>" that does not exist
   Then an error message "<error>" shall be raised

   