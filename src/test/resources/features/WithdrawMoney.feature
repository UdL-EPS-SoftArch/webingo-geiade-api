Feature: Withdraw money
  In order to allow a player to withdraw money from their wallet
  As a player
  I want to decrease my wallet

  Scenario: Withdraw money as a player
    Given I login as "player1" with password "password"
    And "player1@webingo.org" wallet has 200 euros
    When As "player1@webingo.org" I withdraw 30 euros from my wallet
    Then The response code is 200

  Scenario: Withdraw money from another player
    Given I login as "player1" with password "password"
    When I withdraw 30 euros in "user@webingo.org"
    Then The response code is 401

  Scenario: Withdraw less quantity than allowed
    Given I login as "player1" with password "password"
    When As "player1@webingo.org" I withdraw 1 euros from my wallet
    Then The response code is 406