Feature: Accept Invitation
  In order to accept a new player to use the app
  As a player
  I want to accept a new invitation for a game

  Scenario: Accept invitation as player
    Given I login as "email" with password "password"
    And Invitation "invitation" is already created
    When I accept a new invitation "invitation" with username "username" and game id "id"
    Then The invitation is accepted

  Scenario: Accept invitation but the game is finished
    Given I login as "email" with password "password"
    When I accept a new invitation with username "username" and game id "id"
    And The game has already finished or is underway
    Then The response code is !!!
    And The player has not been added to the game

  Scenario: Reject invitation as player
    Given I login as "email" with password "password"
    When I reject a new invitation with username "username" and game id "id"
    Then The response code is <number>
    And The player has not been added to the game

  Scenario: Game is not available, invitation time has been exceeded
    Given I login as "email" with password "password"
    When I accept a new invitation with username "username" and game id "id"
    And Time "time" has been exceeded
    Then The response code is <number>
    And The player has not been added to the game