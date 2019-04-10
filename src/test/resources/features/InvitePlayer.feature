Feature: Invite Player
  In order to allow a new players to join a game

  Scenario: Invite a Player that doesn't exist as player
    Given I login as "player1" with password "password"
    When I invite a player to the game with email "jud@webingo.cat" and message "Wanna join my game?"
    Then The response code is 201
    And There is not a player with username "danicolomer" and email "dani@webingo.cat"
    And It has not been created any invitation

  Scenario: Invite a valid Player to a valid game
    Given I login as "player1" with password "password"
    And There is a player with username "danicolomer" and email "dani@webingo.cat"
    When I invite a player to the game with email "dani@webingo.cat" and message "Wanna join my game?"
    Then The response code is 201
    And It has been invited to game the player

  Scenario: Invite a player to Game without authentication
    Given I'm not logged in
    When I invite a player to the game with email "jud@webingo.cat" and message "Wanna join my game?"
    Then The response code is 401
    And It has not been created any invitation

  Scenario: Invite a player to Game that I already invited
    Given I login as "player1" with password "password"
    And I already invited the player with email "jud@webingo.cat" and message "Wanna join my game?"
    When I invite a player to the game with email "jud@webingo.cat" and message "Wanna join my game?"
    Then The response code is 406
    And It has not been created any invitation












