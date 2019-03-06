Feature: Invite Player
  In order to allow a new players to join a game

  Scenario: Invite a Player that doesn't exist as player
    Given I login as "player" with password "password_player"
    When I invite a new player to the game with username "groover"
    Then The response code is 404
    And It has not been invited any player to the game

  Scenario: Invite X Players to a game
    Given I login as "player" with password "password_player"
    When I invite a X numner os players to the game with username "judvillanueva", username "miremoix", username "davidcalavia",
    Then The response code is 201
    And It has been created the Invitation to the current game

  Scenario: Invite a valid Player to a valid game
    Given I login as "player" with password "password_player"
    When I invite a new player to the game with username "danicolomer"
    Then The response code is 201
    And It has been created the Invitation to the current game

  Scenario: Invite a valid Player who is already playing
    Given I login as "player" with password "password_player"
    When I invite a new player with username "vikton96" who is playing in another game
    Then The response code is 400
    And It has not been invited the player with username "vikton96"



