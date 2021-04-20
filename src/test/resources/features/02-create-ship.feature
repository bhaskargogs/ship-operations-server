Feature: Create Ship

  Scenario: Create Ship with valid parameters
    Given A list of Ships are available
    When User create a ship of name "Madonna" having code "AUYT-2495-L9" with dimensions 456.25 metres in length and 45 metres in width
    Then the ship is Created successfully

