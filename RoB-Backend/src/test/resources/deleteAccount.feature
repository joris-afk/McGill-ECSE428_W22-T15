Feature: Login as an applicarion user
  As an application user, I want to delete my account so that I can erase my personal information when it is no longer needed.

Background:  
  Given a Rob applicarion exists
  Given the following application users exist in the system:
    | username | password | fullname | address  |
    | User1    | abcd     | Alice    | Montreal |
    | User2    | apple    | Bob      | Quebec   |
    | User3    | vscode   | Cathy    | Toronto  |

##########################################################################

Scenario Outline: Delete successfully
  When the user tries to delete with username "<username>" and password "<password>"
  Then the user should be successfully deleted

  Examples: 
  | username | password |
  | User1    | abcd     |
  | User2    | apple    |
  | User3    | vscode   |

##########################################################################

Scenario Outline: Delete with unexisting account
  When the user tries to delete with an exisiting username "<username>"
  Then none of the users should be deleted
  Then an error message "Cannot delete unexisting account." shall be raised

  Examples: 
  | username |
  | User4    | 
  | User5    |
  | User6    |

##########################################################################

Scenario Outline: Delete with wrong password
  When the user tries to delte with username "<username>" and wrong password "<password>"
  Then the user should still exist
  Then an error message "Correct password must be entered to delete account." shall be raised

  Examples: 
  | username | password |
  | User1    |          |
  | User2    | b        |
  | User3    | c        |