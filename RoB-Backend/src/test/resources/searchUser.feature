Feature: Search for a User
As an application user, I want to be able to search for a user

Background:
	Given a Rob application exists
	Given the following application users login in the system:
	    | username | password | fullname | address  |
	    | User1    | abcd     | Alice    | Montreal |
	    | User2    | apple    | Bob      | Quebec   |


##########################################################################

Scenario: Search for single user successfully
  When the user with username "User1" searches for username "User2"
  Then the following user will be returned:
      | username |
	    | User2    |


##########################################################################

Scenario: Search for User that does not exist
  When the user with username "User1" searches for username "User9"
  Then no users shall be returned
  Then an error message "No items match your search" shall be raised

##########################################################################

Scenario: Search for Item without a keyword
  When the user with username "User1" searches for username ""
  Then no users shall be returned
  Then an error message "Please enter a keyword" shall be raised

##########################################################################