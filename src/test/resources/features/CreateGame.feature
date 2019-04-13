Feature: Create Game
  In order to allow the system to create a new game
  As an admin
  I want to register a new game

  Scenario: Create new game as admin
    Given I login as "admin" with password "password"
    When I register a new game with name "something"
    Then The response code is 201
    And It has been created a game with id 1 and name "something"

  Scenario: Create new game with existing name
    Given I login as "admin" with password "password"
    And I register a new game with name "something"
    When I register another game with name "something"
    Then The response code is 409
