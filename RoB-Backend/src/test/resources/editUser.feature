Feature: Edit accout as an applicarion user
  As an application user, I want to edit to my account so that I can update my personal information.

Background:  
  Given a Rob applicarion exists
  Given the following application users exist in the system:
    | username | password | fullname | address  |
    | User1    | abcd     | Alice    | Montreal |
    | User2    | apple    | Bob      | Quebec   |

##########################################################################

Scenario: Update username successfully
   Given the user is logged in to an account with username "User1"
   When the user tries to update account with a new username "User55"
   Then the account shall have username "User55"
   
##########################################################################
   
Scenario: Update password successfully
   Given the user is logged in to an account with username "User1"
   When the user tries to update account with a new password "123abcd"
   Then the account shall have password "123abcd"

##########################################################################

Scenario: Update full name successfully
   Given the user is logged in to an account with username "User1"
   When the user tries to update account with a new full name "Aidan"
   Then the account shall have full name "Aidan"
   
##########################################################################
   
Scenario: Update address successfully
   Given the user is logged in to an account with username "User1"
   When the user tries to update account with a new address "Vancouver"
   Then the account shall have address "Vancouver"

##########################################################################

Scenario Outline: Update username failed
 	 Given the user is logged in to an account with username "User1"
   When the user tries to update account with a new username "<newUser>"
   Then the account shall have username "User1"
   Then an error message "<error>" shall be raised

Examples: 
   | newUser | error                                          |
   | User2   | Username not available                         |
   |         | Username cant be empty										      |
   
##########################################################################

Scenario: Update password failed
 	 Given the user is logged in to an account with username "User1"
   When the user tries to update account with a new password ""
   Then the account shall have password "abcd"
   Then an error message "password cant be empty" shall be raised
   
##########################################################################

Scenario: Update address failed
 	 Given the user is logged in to an account with username "User1"
   When the user tries to update account with a new address ""
   Then the account shall have address "Montreal"
   Then an error message "address cant be empty" shall be raised
   
##########################################################################

Scenario: Update full name failed
 	 Given the user is logged in to an account with username "User1"
   When the user tries to update account with a new full name ""
   Then the account shall have full name "Alice"
   Then an error message "Full name cant be empty" shall be raised
