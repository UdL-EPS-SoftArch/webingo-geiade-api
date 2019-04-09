Feature: Withdraw money
  In order to allow a player to withdraw money from their wallet
  #As an admin
  #I want to register a new player account

  Scenario: Withdraw money as a player
    Given I login as "email" with password "password"
    When I want to get back some of my money 5 from the wallet of "player1@webingo.org"
    Then The response code is 200
    And Wallet has the correct amount of money left
    #It has been sent the money to the player with username from his wallet


  Scenario: Withdraw money as an another player (Ilegal)
    Given I login as "email" with password "password"
    When I want to get back some money 5 from some wallet of "player1@webingo.org" with username "user@webingo.org"
    #Then The response code is 401
    Then The system recognize it is ilegal
    And The money has not been withdrawed from the wallet