Feature: Logout as an applicarion user
  As an application user, I want to log out of my account so that my session will be closed.

Background:  
  Given a Rob applicarion exists
  Given the following application users login in the system:
    | username | password | fullname | address  |
    | User1    | abcd     | Alice    | Montreal |
    | User2    | apple    | Bob      | Quebec   |

##########################################################################

Scenario: Logout successfully
  When the user with username "User1" tries to log out
  Then the user should be successfully logged out

##########################################################################

Scenario: Log out when the user is not login
  When the user with username "User3" tries to log out
  Then an error message "You must log in first" shall be raised

##########################################################################

Scenario: Log out as an non-registered user
  When the user with username "User9" tries to log out
  Then an error message "User does not exist" shall be raised
