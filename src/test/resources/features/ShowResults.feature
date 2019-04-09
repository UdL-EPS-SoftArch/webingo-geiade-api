Feature: ShowResults
  In order to show the results
  As a user
  I want to sing line or sing bingo

  Scenario: Show the results of a finished game
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And user "player1@webingo.org" join to a game "game"
    And "player1@webingo.org" has won the bingo for the game "game"
    And "player1@webingo.org" has won the line for the game "game"
    When admin display results of game "game"
    Then The response code is 201
    And "player1@webingo.org" has been added at the bingo winner variable of the game "game"
    And "player1@webingo.org" has been added at the line winner variable of the game "game"

  Scenario: show results of a game which is not finished
    Given I login as "admin" with password "password"
    And existing game with name "game"
    And user "player1@webingo.org" join to a game "game"
    When admin display results of game "game"
    Then The response code is 404


  Scenario: show results of a non existing game
    Given I login as "admin" with password "password"
    When admin display results of game "game"
    Then The response code is 401


