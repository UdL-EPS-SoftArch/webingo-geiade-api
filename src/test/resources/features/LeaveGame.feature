Feature: Leave Game
  In order to remove a player to a game
  As a player
  I want to leave a game

  Scenario: Leave a game as a player
    Given I login as "xxx@webingo.cat" with password "password"
    When I leave a game when I'm playing with username email "xxx@webingo.cat"
    And Card 1 is removed
    Then The response code is 200
    And It has been removed the game in the player with username "xxx@webingo.cat"
    And Card 1 has been removed


  Scenario: Leave an game as a player
    Given I login as "xxx@webingo.cat" with password "password"
    When I leave a game when I'm not playing with username email "xxx@webingo.cat"
    Then The response code is 409
    And the error message is "You can't leave this game, you are not playing"
