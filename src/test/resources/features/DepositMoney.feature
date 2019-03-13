Feature: Deposit Money
  In order to allow the player to deposit money
  As a player
  I want to increase my wallet

  Scenario: Deposit money as a player
    Given I login as "player" with password "password"
    And "player" wallet is 0
    When As "player" I deposit money 10 euros in my wallet
    Then The response code is 201
    And  "player" wallet has increased in 10 euros