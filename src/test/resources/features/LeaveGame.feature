Feature: Leave Game
  In order to remove a player to a game
  As a player
  I want to leave a game

  Scenario: Leave a game as a player
    Given I login as with password "password" and a username "username"
    When I leave a game when I'm playing
    Then The response code is 200
    And "isPaying_game" becomes null
    And isPaying_card" becomes null


  Scenario: Leave an game as a player
    Given I login as with password "password" and a username "username"
    When I leave a game when I'm not playing
    Then The response code is 409
    And the error message is "You can't leave this game, you are not playing"
