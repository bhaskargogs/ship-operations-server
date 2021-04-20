Feature: Find, Update and Delete Ship

  Scenario: Find a Valid Ship and Update its dimensions
    Given the user looks for ship with id 5
    When the user gets the ship
    And the user updates the ship with length 452.34 m and width 75.32 m
    Then the ship with ID 5 is updated successfully

  Scenario: Delete The ship with valid information
    Given A user having a ship with id 5
    When the user gets the valid ship
    And the user deletes the ship with id 5
    Then delete is successful