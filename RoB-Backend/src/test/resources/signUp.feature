Feature: Sign up for an application user
  As a prospective application user, I want to create an account with username and password so that I can log in later and access all the provided services.

Background: 
Given a Rob application exists
Given the following application users exist in the system:
    | username | password |
    | User1    | abcd     |

##########################################################################

Scenario Outline: Create a new account successfully
Given there is no existing username "<username>"
When the user provides a new username "<username>" and a password "<password>"
Then a new customer account shall be created
Then the account shall have username "<username>" and password "<password>"

Examples: 
    | username | password |
    | uers2    | apple    |
    | User3    | vscode   |

##########################################################################

Scenario Outline: Create a new account with incomplete form
When the user provides a new username "<username>" and a password "<password>"
Then no new account shall be created
Then an error message "<error>" shall be raised

Examples: 
    | username | password | error                         |
    |          | apple    | The username cannot be empty  |
    | User3    |          | The password cannot be empty  |

##########################################################################

Scenario: Create a new user with a username that already exists
When the user provides a new username "User1" and a password "abcd"
Then no new account shall be created
Then an error message "The username already exists" shall be raised
