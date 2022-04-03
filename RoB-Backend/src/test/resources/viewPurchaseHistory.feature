Feature: View purchase history
  As an application user, I want to view purchased items on my account to track my past purchases.

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
   Given the user with username "User1" purchases a "couch from Alice"
   When the user tries to view purchase history
   Then the number of items in the history should be "1"
   
##########################################################################

Scenario Outline: user has no purchased items
   Given the user is logged in to an account with username "User1"
   When the user tries to view purchase history
   Then an error message "No purchases have been made" shall be raised