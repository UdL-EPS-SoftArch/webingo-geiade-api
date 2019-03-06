Feature: Create Game
  In order to allow the system to create a new game
  As an admin
  I want to register a new game

  Scenario: Create new game as admin
    Given I login as "admin" with password "password"
    When I register a new game
    Then The response code is 200
    And It has been created a game

  Scenario: Create new game with existing "id"
    Given A game is currently existing
    When I create a new game
    Then The response code is 500
    And It has not been created a new game with same "id"


