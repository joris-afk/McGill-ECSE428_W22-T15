Feature: Search for an Item
As an application user, I want to be able to search for an item

Background:
	Given a Rob application exists
	Given the following application users login in the system:
	    | username | password | fullname | address  |
	    | User1    | abcd     | Alice    | Montreal |
	    | User2    | apple    | Bob      | Quebec   |
  Given the following application items exist in the system:
      | name          | price | availableSizes   |
      | winter hat    | 7.00  | large            |
      | baseball hat  | 5.00  | small,medium     |
      | hat           | 5.00  | small,medium     |
      | toy           | 2.00  | small            |
      
      
##########################################################################

Scenario: Search for single item successfully
  When the user with username "User1" searches with keyword "winter hat"
  Then the following items will be returned:
 			| name          | price | availableSizes   |
      | winter hat    | 7.00  | large            |
      
##########################################################################

Scenario: Search for multiple items successfully
  When the user with username "User1" searches with keyword "hat"
  Then the following items will be returned:
 			| name          | price | availableSizes   |
      | winter hat    | 7.00  | large            |
      | baseball hat  | 5.00  | small,medium     |
      | hat           | 5.00  | small,medium     |

##########################################################################

Scenario: Search for multiple items successfully via size
  When the user with username "User1" searches with keyword "small"
  Then the following items will be returned:
 			| name          | price | availableSizes   |
      | baseball hat  | 5.00  | small,medium     |
      | hat           | 5.00  | small,medium     |
      | toy           | 2.00  | small            |

##########################################################################

Scenario: Search for Item that does not exist
  When the user with username "User1" searches with keyword "shirt"
  Then no items shall be returned
  Then an error message "No items match your search" shall be raised
  
##########################################################################

Scenario: Search for Item without a keyword
  When the user with username "User1" searches with keyword ""
  Then no items shall be returned
  Then an error message "Please enter a keyword" shall be raised
  
##########################################################################