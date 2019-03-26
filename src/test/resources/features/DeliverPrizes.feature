Feature: Deliver Prizes
  In order to allow players to gain money with the jackpot

  Scenario: Deliver prizes to two winners
    Given I login as "player1" with password "password"
    And there is a player with email "jud@webingo.cat" who won the line
    And there is a player with email "david@webingo.cat" who won the bingo game
    When I deliver prizes to a player with email "jud@webingo.cat" and "david@webingo.cat"
    Then The response code is 201
    And The wallet of both players is updated














