Feature: Join Game
  In order to add a player to a game
  As a player
  I want to being added to a game

  Scenario: Join a game as  a player
    Given I login as "player" with password "password"
    And existing game with name "game" and price 2
    And the player "player" has more wallet than price
    When I join to a game with name "x"
    Then The response code is 201
    And a new "card" is added to the player
    And the "Game" is added to the player
    And the "jackpot" is increased by the card "price"
    And the "wallet" is decreased by the card "price"

  Scenario: Join an unexisting game as a player
    Given I login as "player" with password "password"
    And the game does not exist 1
    When I join to a game with name "x"
    Then The response code is 404
    And the error message is "This game does not exist"


  Scenario: Join a game as a player without money
    Given I login as "player" with password "password"
    And without enough money 2 for the gameprice 3
    When I join to a game with name "x"
    Then The response code is 406
    And the error message is "You dont have enough money in your wallet"

  Scenario: Join a game as a player and this player is already playing in another game
    Given I login as "player" with password "password"
    And im playing "playing"
    When I join to a game with name "x"
    Then The response code is 406
    And the error message is "You cannot play in more than 1 game at a time, sorry!!"

