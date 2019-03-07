Feature: Create Game
  In order to allow the system to create a new game
  As the system
  I want to register a new game

  Scenario: Create new game as system
    Given I login as "system" with password "password"
    When I register a new game
    Then The response code is <202>
    And It has been created a game
