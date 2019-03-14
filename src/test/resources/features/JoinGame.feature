Feature: Join Game
  In order to add a player to a game
  As a player
  I want to being added to a game

  Scenario: Join a game as  a player
    Given I login with password "password" and a username "username" with id 1 and the player has more wallet 2 than price 3 and im "notplaying"
    When I join to a game
    Then The response code is 201
    And a new "card" is added to the player
    And the "Game" is added to the player
    And the "jackpot" is increased by the card "price"
    And the "wallet" is decreased by the card "price"

  Scenario: Join an unexisting game as a player
    Given I login with password "password" and a username "username" and the game does not exist
    When I join to a game
    Then The response code is 404
    And the error message is "This game does not exist"


  Scenario: Join a game as a player without money
    Given I login with password "password" and a username "username" existing game id 1 but without money 2 for the gameprice 3
    When I join to a game
    Then The response code is 406
    And the error message is "You dont have enough money in your wallet"

  Scenario: Join a game as a player and this player is already playing in another game
    Given I login with password "password" and a username "username"  id 1 while "implaying"
    When I join to a game
    Then The response code is 406
    And the error message is "You cannot play in more than 1 game at a time, sorry!!"

