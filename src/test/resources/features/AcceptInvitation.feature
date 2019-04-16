Feature: Accept Invitation
  In order to accept a new invitation to use the app
  As a player
  I want to accept a new invitation for a game

  Scenario: Accept invitation as player
    Given I login as "player1" with password "password"
    And Invitation "invitation" is already created and was sent from email "user@webingo.org" to email "player1@webingo.org"
    And The game is the desired one
    When I accept the invitation "invitation"
    Then The response code is 200

  Scenario: Accept invitation but the game is finished or underway
    Given I login as "player1" with password "password"
    And Invitation "invitation" is already created and was sent from email "user@webingo.org" to email "player1@webingo.org"
    And The game has already finished or is underway
    When I accept the invitation "invitation"
    Then The response code is 406

  Scenario: Game is not available, invitation time has been exceeded
    Given I login as "player1" with password "password"
    And Invitation "invitation" is already created and was sent from email "user@webingo.org" to email "player1@webingo.org"
    And Timeout has been exceeded
    When I accept the invitation "invitation"
    Then The response code is 406

  Scenario: Reject invitation as player
    Given I login as "player1" with password "password"
    And Invitation "invitation" is already created and was sent from email "user@webingo.org" to email "player1@webingo.org"
    And The game is not the desired one
    When I reject the invitation "invitation"
    Then The response code is 406
