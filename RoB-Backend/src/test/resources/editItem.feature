Feature: Edit item details as an applicarion user
  As an application user, I want to edit items on my account to adjust my offers.

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

Scenario: Update item price successfully
   Given the user is logged in to an account with username "User1"
   Given the user is looking at "couch from Alice"
   When the user tries to update price to a new price of "10.50"
   Then the current item shall have price "10.50"
   
##########################################################################
   
Scenario: Add item size successfully
   Given the user is logged in to an account with username "User1"
   Given the user is looking at "couch from Alice"
   When the user tries to add size of "small" to the current item
   Then the current item shall have size "small"

##########################################################################

Scenario: Remove item size successfully
   Given the user is logged in to an account with username "User1"
   Given the user is looking at "couch from Alice"
   When the user tries to remove size of "universal" from the current item
   Then the current item shall not have size "universal"
   
##########################################################################

Scenario Outline: Update item price failed
   Given the user is logged in to an account with username "User1"
   Given the user is looking at "<Item>"
   When the user tries to update price to a new price of "<NewPrice>"
   Then an error message "<error>" shall be raised

Examples: 
   | Item             | NewPrice | error                                  |
   | couch from Alice | -10.50   | Item price can't be negative           |
   |                  |  10.00   | Can't edit null item		  	           |
   
##########################################################################

Scenario: Add item size failed
   Given the user is logged in to an account with username "User1"
   Given the user is looking at "<Item>"
   When the user tries to add size of "<NewSize>" to the current item
   Then the current item shall not have size "<NewSize>"
   Then an error message "<error>" shall be raised

Examples: 
   | Item             |     NewSize      | error                                                |
   | couch from Alice |                  | Must specify one available size                      |
   |                  |      small       | Can't edit null item			                        |
   | couch from Alice |     universal    | Cannot add duplicate size                            |
   
##########################################################################

Scenario: Remove item size failed
   Given the user is logged in to an account with username "User1"
   Given the user is looking at "<Item>"
   When the user tries to remove size of "<OldSize>" from the current item
   Then the current item shall have size "<OldSize>"
   Then an error message "<error>" shall be raised

Examples: 
   | Item             | OldSize | error                                                |
   | couch from Alice |         | Must specify one unavailable sizes                   |
   |                  |  small  | Can't edit null item			                        |
   