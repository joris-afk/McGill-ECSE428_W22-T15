Feature: Edit item details as an applicarion user
  As an application user, I want to purchase items in my cart.

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
  Given the user "User1" has the following ItemInCart in his cart:
    | itemInCartId  | quantity  | size      | itemName        | 
    | 2000          | 2         | universal | couch from Alice|
    | 3001          | 1         | small     | couch from Bob  |

##########################################################################

Scenario: purchase items in cart successfully
   Given the user is logged in to an account with username "User1"
   When the user "User1" tries to purchase items in "User1" cart with order id "s001"
   Then the purchase with order id "s001" shall be made
   
##########################################################################

Scenario: purchase empty cart
   Given the user is logged in to an account with username "User2"
   When the user "User2" tries to purchase items in "User2" cart with order id "s002"
   Then an error message "cannot purchase empty cart" shall be raised
   Then the purchase with order id "s002" shall not be made
   
##########################################################################

Scenario: purchase items from other users' cart
   Given the user is logged in to an account with username "User1"
   When the user "User2" tries to purchase items in "User1" cart with order id "s003"
   Then an error message "cannot purchase other user's cart" shall be raised
   Then the purchase with order id "s003" shall not be made

   