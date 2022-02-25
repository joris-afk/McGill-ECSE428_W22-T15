Feature: Add Item
As an application user, I want to add items so that I can sell them

Background:
Given a Rob application exists
Given the following application users login in the system:
    | username | password | fullname | address  |
    | User1    | abcd     | Alice    | Montreal |
    | User2    | apple    | Bob      | Quebec   |

##########################################################################

Scenario: Add Item successfully
  When the user with username "User1" tries to add "couch" with price "100.00"
  Then the "couch" will be added successfully to the database

Example:
  | username | password | name  | price  |
  | User1    | abcd     | couch | 100.00 |
  | User2    | apple    | chair | 15.00  |

##########################################################################

Scenario: Add Item when the user is not login
  When the user with username "User3" tries to add "couch" with price "100.00"
  Then an error message "You must log in first" shall be raised

Example:
  | username | password | name  | price  |
  | User3    | abcd     | apple | 100.00 |
  | User4    | apple    | peach | 15.00  |

##########################################################################

Scenario: Add duplicate Item
  When the user with username "User1" tries to add a duplicate "couch" with price "price"
  Then an error message "You've already added this item" shall be raised

Example:
  | username | password | name  | price  |
  | User1    | abcd     | couch | 100.00 |
  | User1    | abcd     | couch | 100.00 |
