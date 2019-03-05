Feature: Join Game
  In order to add a player to a game
  As a player
  I want to being added to a game

  Scenario: Join a game as player
    Given I login as with password "password" and a username "username"
    When I join to a game with an existing id "<integer>"
    Then The response code is <201>
    And the user "player" is added to the "playingplayers"
    And a new card is added to the "playingcards"
    And the jackpot is increased by the card price
