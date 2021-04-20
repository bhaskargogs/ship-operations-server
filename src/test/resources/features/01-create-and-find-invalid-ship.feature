Feature: Create and Find Invalid Ship

  Scenario: Create a Ship with Invalid data
    Given A user having Ship of name "DemaKila" with length 543.25 m, width 65.24m and code "AY29-HASD-22"
    When the user creates ship
    Then an exception is thrown

  Scenario: Find a Ship with Non-existant ID
    Given A user tries to find a ship with ID 253
    When the user searches for the ship
    Then it fails