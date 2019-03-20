Feature: Invite Player
  In order to allow a new players to join a game

  Scenario: Invite a Player that doesn't exist as player
    Given I login as "player" with password "password"
    When I invite a new player to the game with email "jud@webingo.cat" and message "Wanna join my game?"
    Then The response code is 201
    And It has not been created any invitation

  Scenario: Invite a valid Player to a valid game
    Given I login as "player" with password "password"
    When I invite a new player to the game with email "dani@webingo.cat" and message "Wanna join my game?"
    Then The response code is 201
    And There is a player with username "danicolomer" and email "dani@webingo.cat"
    And It has been invited to game the player with email "dani@webingo.cat" and message "Wanna join my game?"

  Scenario: Invite a player to Game without authentification
    Given I'm not logged in
    When I invite a new player to the game with email "jud@webingo.cat" and message "Wanna join my game?"
    Then The response code is 401
    And It has not been created any invitation














