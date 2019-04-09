Feature: Join Game
  In order to add a player to a game
  As a player
  I want to being added to a game

  Scenario: Join a game as  a player
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And the player "player1@webingo.org" has more wallet than price 3
    And the boolean of beingplaying was not activated for user "player1@webingo.org"
    When user "player1@webingo.org" join to a game
    Then The response code is 201
    And a new card has been created "player1@webingo.org"
    And the boolean of beingplaying has been activated "player1@webingo.org"
    And the jackpot has increased by 3
    And the wallet has decreased by 3 for "player1@webingo.org"


  Scenario: Join an unexisting game as a player
    Given I login as "player1" with password "password"
    When user "player1@webingo.org" join to a game
    Then The response code is 500


  Scenario: Join a game as a player without money
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And the player "player1@webingo.org" has less money 3
    When user "player1@webingo.org" join to a game
    Then The response code is 500

  Scenario: Join a game as a player and this player is already playing in another game
    Given I login as "player1" with password "password"
    And existing game with name "game"
    And the boolean of beingplaying of player "player1@webingo.org" was activated
    When user "player1@webingo.org" join to a game
    Then The response code is 500

