Feature: Add an item to a cart
As an application user, I would like to add items to my cart.

Background: 
    Given a Rob application exists
    Given the following application users login in the system:
      | username | password | fullname | address  |
      | User1    | abcd     | Alice    | Montreal |
      | User2    | apple    | Bob      | Quebec   |
    Given the following application items exist in the system:
      | name | price | availableSizes   |
      | hat  |  5.00 | small,medium     |
      | toy  |  8.00 | small            |
    Given the user with username "User1" has cart with ID "12482" 

  ######################################################################
  Scenario: Add Item Successfully
    When user with username "User1" adds item with id "001" to their cart
    Then the item with id "001" will appear in the cart
    

  ######################################################################
  Scenario: Add Item Failed
    When user with username "User1" add item with id "010"
    Then no item will be removed
    And an error "Item does not appear in cart" should appear