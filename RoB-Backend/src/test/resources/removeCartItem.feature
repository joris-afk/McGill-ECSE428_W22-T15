Feature: Remove Item
  As an application user, I would like to remove an item from my cart

  Background: 
    Given a Rob application exists
    Given the following application users login in the system:
      | username | password | fullname | address  |
      | User1    | abcd     | Alice    | Montreal |
      | User2    | apple    | Bob      | Quebec   |
    Given the following application items exist in the system:
      | name | price | availableSizes   |
      | hat  |  5.00 | small,medium |
      | toy  |  8.00 | small         |
    Given the user with username "User1" has cart with ID "12482" and with the following items:
      | item_name | quantity | size   | item_id |
      | hat       |        1 | small  |     001 |
      | hat       |        1 | medium |     002 |
      | toy       |        2 | small  |     003 |

  ######################################################################
  Scenario: Remove Item Successfully
    When user with username "User1" removes item with id "001"
    Then the item with id "001" will not appear in the cart
    And the cart size will be "2"

  ######################################################################
  Scenario: Remove Item Failed
    When user with username "User1" removes item with id "010"
    Then no item will be removed
    And an error "Item does not appear in cart" should appear
