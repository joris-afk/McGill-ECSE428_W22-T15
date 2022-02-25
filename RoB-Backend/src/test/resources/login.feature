Feature: Login as an applicarion user
  As an application user, I want to log in to my account so that I can access my personal information, sell or buy items.

Background:  
  Given a Rob application exists
  Given the following application users exist in the system:
    | username | password | fullname | address  |
    | User1    | abcd     | Alice    | Montreal |
    | User2    | apple    | Bob      | Quebec   |
    | User3    | vscode   | Cathy    | Toronto  |

##########################################################################

Scenario Outline: Log in successfully
  When the user tries to log in with username "<username>" and password "<password>"
  Then the user should be successfully logged in

Examples: 
  | username | password |
  | User1    | abcd     |
  | User2    | apple    |
  | User3    | vscode   |

##########################################################################

Scenario Outline: Log in with a username that does not exist
  When the user tries to log in with username "<username>" and password "<password>"
  Then the user should not be logged in
  Then an error message "Username does not exist" shall be raised

Examples: 
  | username | password |
  | User4    | html     |
  | User9    | grape    |

##########################################################################

Scenario Outline: Log in with incorrect password
  When the user tries to log in with username "<username>" and password "<password>"
  Then the user should not be logged in
  Then an error message "Username/password not found" shall be raised

Examples: 
  | username | password |
  | User1    | abc      |
  | User2    | grape    |
  | User3    | eclipse  |