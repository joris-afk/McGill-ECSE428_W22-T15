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
    When user with username "User1" adds "6" item with name "hat" of size "small" to their cart
    Then the item with name "hat" will appear in the cart
    

  ######################################################################
  Scenario: Add Item Failed -- Item does not exist
    When user with username "User1" adds "6" item with name "trash" of size "small" to their cart
    Then the item with name "trash" will not appear in the cart
    And an error "This item does not exist." should appear

  Scenario: Add Item Failed -- Size does not exist
    When user with username "User1" adds "7" item with name "toy" of size "medium" to their cart
    Then the item with name "toy" will not appear in the cart
    And an error "Unavailable size for this product" should appear

  Scenario: Add Item Failed -- Non-Positive quantity
    When user with username "User1" adds "7" item with name "toy" of size "medium" to their cart
    Then the item with name "toy" will not appear in the cart
    And an error "You must order at least one of the product" should appear