Feature: Delete Item
As an application user, I want to delete items so that I can take them off the marketplace

Background:
Given a Rob application exists
Given the following application users login in the system:
    | username | password | fullname | address  |
    | User1    | abcd     | Alice    | Montreal |
    | User2    | apple    | Bob      | Quebec   |

##########################################################################

Scenario: Delete Item successfully
  When the user with username "User1" tries to delete "couch" with price "100.00"
  Then the "couch" will be deleted successfully to the database

Example:
  | username | password | name  | price  |
  | User1    | abcd     | couch | 100.00 |
  | User2    | apple    | chair | 15.00  |

##########################################################################

Scenario: Delete Item when the user is not login
  When the user with username "User3" tries to delete "couch" with price "100.00"
  Then an error message "You must log in first" shall be raised

Example:
  | username | password | name  | price  |
  | User3    | abcd     | apple | 100.00 |
  | User4    | apple    | peach | 15.00  |

##########################################################################

Scenario: Delete non existent item
  When the user with username "User1" tries to delete a non existent "chihuahua" with price "price"
  Then an error message "This Item does not exist" shall be raised

Example:
  | username | password | name      | price  |
  | User1    | abcd     | chihuahua | 100.00 |
  | User1    | abcd     | couch     | 100.00 |
