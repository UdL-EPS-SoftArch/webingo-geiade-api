Feature: Join Game
  In order to add a player to a game
  As a player
  I want to being added to a game

  Scenario: Join a game as  a player
    Given I login as "admin" with password "password"
    And existing game with name "game" and price 2
    And the player "player@webingo.org" has more wallet than price
    When I join to a game with name "game"
    Then The response code is 201
    And a new "card" is added to the player
    And the "Game" is added to the player
    And the "jackpot" is increased by the card "price"
    And the "wallet" is decreased by the card "price"

  Scenario: Join an unexisting game as a player
    Given I login as "player" with password "password"
    When I join to a game with name "game"
    Then The response code is 404


  Scenario: Join a game as a player without money
    Given I login as "player" with password "password"
    And existing game with name "game" and price 3
    And the player "player@webingo.org" has less money
    When I join to a game with name "game"
    Then The response code is 406

  Scenario: Join a game as a player and this player is already playing in another game
    Given I login as "player" with password "password"
    And im playing "playing"
    When I join to a game with name "game"
    Then The response code is 406

