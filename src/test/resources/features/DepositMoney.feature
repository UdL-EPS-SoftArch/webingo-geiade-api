Feature: Deposit Money
  In order to allow the player to deposit money
  As a player
  I want to increase my wallet

  Scenario: Deposit money as a player
    Given I login as "player1" with password "password"
    And "player1@webingo.org" wallet is 0
    When As "player1@webingo.org" I deposit money 10 euros in my wallet
    Then The response code is 200
    And  "player1@webingo.org" wallet is 10

  Scenario: Deposit money to another player
    Given I login as "player1" with password "password"
    When As "player1@webingo.org" I deposit 10 euros in "user@webingo.org"
    Then The response code is 403

  Scenario: Diposit less quantity than allowed
    Given I login as "player1" with password "password"
    When As "player1@webingo.org" I deposit money 1 euros in my wallet
    Then The response code is 406

  Scenario: Diposit less quantity than allowed
    Given I login as "player1" with password "password"
    When As "player1@webingo.org" I deposit money - 10 euros in my wallet
    Then The response code is 406