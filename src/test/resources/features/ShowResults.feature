Feature: ShowResults
  In order to show the results
  As a user
  I want to sing line or sing bingo

  Scenario: player sings line and it is accepted
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And the player "player1@webingo.org" has more wallet than price 3
    And the boolean of beingplaying was not activated for user "player1@webingo.org"
    And user "player1@webingo.org" join to a game "game"
    And "player1@webingo.org" has won the line for the game "game"
    When player "email" sing line "game"
    Then The response code is 201
    And "player1@webingo.org" has been added at the line winner variable of the game "game"

  Scenario: player sings line and it is not accepted
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And user "player1@webingo.org" join to a game "game"
    When player "email" sing line "game"
    Then The response code is 500


  Scenario: player sings bingo and it is accepted
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And user "player1@webingo.org" join to a game "game"
    And "player1@webingo.org" has won the bingo for the game "game"
    When player "email" sing bingo "game"
    Then The response code is 201
    And "player1@webingo.org" has been added at the bingo winner variable of the game "game"


  Scenario: player sings bingo and it is not accepted
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And user "player1@webingo.org" join to a game "game"
    When player "email" sing bingo "game"
    Then The response code is 500