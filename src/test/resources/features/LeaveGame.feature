Feature: Leave Game
  In order to remove a player to a game
  As a player
  I want to leave a game

  Scenario: Leave a game as a player
    Given I login as "player1" with password "password"
    And Player "player1@webingo.org" is in game "game" and playing with card 1
    When I leave a game when I'm playing with Card 1
    Then The response code is 404
    And It has been removed the game in the player with card 1



  Scenario: Leave an game as a player
    Given I login as "xxx@webingo.cat" with password "password"
    When I leave a game when I'm not playing with username email "xxx@webingo.cat"
    Then The response code is 409