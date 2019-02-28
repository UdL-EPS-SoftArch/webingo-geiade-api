Feature: Join Game
  In order to add a player to a game
  As a player
  I want to being added to a game

  Scenario: Join a game as player
    Given I join  username as a "username" and password "password"
    When
    Then The respons code is "201"
