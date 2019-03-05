Feature: Join Game
  In order to add a player to a game
  As a player
  I want to being added to a game

  Scenario: Join a game as  a player
    Given I login as with password "password" and a username "username"
    When I join to a game with an existing "id" and "isPlaying" is false and the "wallet" is > than the Card's "price"
    Then The response code is <201>
    And the user "player" is added to the "playingplayers"
    And a new card is added to the "playingcards"
    And the "jackpot" is increased by the card "price"
    And the "wallet" is decreased by the card "price"

  Scenario: Join an unexisting game as a player
    Given I login as with password "password" and a username "username"
    When I join to a game with an unexisting "id"
    Then The response code is <404>
    And the error message is "This game does not exist"


  Scenario: Join a game as a player without money
    Given I login as with password "password" and a username "username"
    When I join to a game with an existing "id" and the "wallet" is < than the Card's "price"
    Then The response code is <406>
    And the error message is "You dont have enough money in your wallet"

  Scenario: Join a game as a player and this player is already playing in another game
    Given I login as with password "password" and a username "username"
    When I join to a game with an existing "id" and the "isPlaying" is true
    Then The response code is <406>
    And the error message is "You cannot play in more than 1 game at a time, sorry!!"

