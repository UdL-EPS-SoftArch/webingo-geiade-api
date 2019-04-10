Feature: Invite Player
  In order to allow a new players to join a game

  Scenario: Invite a Player that doesn't exist as player
    Given I login as "player1" with password "password"
    When I create an invitation with message "Wanna join my game?"
    Then The response code is 201
    And There is not a player with username "danicolomer" and email "dani@webingo.org"
    And It has not been created any invitation

  Scenario: Invite a valid Player to a valid game
    Given I login as "player1" with password "password"
    When I create an invitation with message "Wanna join my game?"
    Then The response code is 201
    And There is a player with username "danicolomer" and email "dani@webingo.org" who I invite
    And It has been invited to game the player

  Scenario: Invite a player to Game without authentication
    Given I'm not logged in
    When I create an invitation with message "Wanna join my game?"
    Then The response code is 401
    And It has not been created any invitation

  Scenario: Invite a player to Game that I already invited
    Given I login as "player1" with password "password"
    And I already invited the player with email "dani@webingo.org" and username "danicolomer"
    When I create an invitation with message "Wanna join my game?"
    Then The response code is 406
    And It has not been created any invitation












