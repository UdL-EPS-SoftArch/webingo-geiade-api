Feature: ShowResults
  In order to show the results
  As a user
  I want to sing line or sing bingo

  Scenario: player sings line and it is accepted
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And user "player1@webingo.org" join to a game "game"
    And "player1@webingo.org" has won the line for the game "game"
    When player sing line "game"
    Then The response code is 201
    And "player1@webingo.org" has been added at the line winner variable of the game "game"

  Scenario: player sings line and it is not accepted
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And user "player1@webingo.org" join to a game "game"
    And user "player1@webingo.org" join to a game "game"
    When player sing line "game"
    Then The response code is 500


  Scenario: player sings bingo and it is accepted
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And user "player1@webingo.org" join to a game "game"
    And "player1@webingo.org" has won the bingo for the game "game"
    When player sing bingo "game"
    Then The response code is 201
    And "player1@webingo.org" has been added at the bingo winner variable of the game "game"


  Scenario: player sings bingo and it is not accepted
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And user "player1@webingo.org" join to a game "game"
    When player sing line "game"
    Then The response code is 500