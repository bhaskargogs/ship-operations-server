Feature: Search Ships

  Scenario: Search for ships with particular field
    Given A user has an search string of "54"
    When the user searches for ships
    Then the user gets list of ships