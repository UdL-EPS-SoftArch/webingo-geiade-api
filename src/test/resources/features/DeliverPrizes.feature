Feature: Deliver Prizes
  In order to allow players to gain money with the jackpot

  Scenario: The system delivers prizes to two winners done correctly
    Given I login as "admin" with password "password"
    And The player "player1@webingo.org" won the line and the player "player2@webingo.org" won the bingo
    And There is a line prize and a bingo prize in the current game
    And There is a game named "geiade-game" that has already finished
    When I deliver prizes to the winning players
    Then The game is completely finished


  Scenario: The system delivers prizes correctly to the same player who won the lineprize and the bingorize
    Given I login as "admin" with password "password"
    And The player "player1@webingo.org" won the line and bingo at the same game
    And There is a line prize and a bingo prize in the current game
    And There is a game named "geiade-game" that has already finished
    When I deliver prizes to the only winner
    Then The game is completely finished


  Scenario: The system fails to deliver cause there is a bad lineprize or bingoprize in the game
    Given I login as "admin" with password "password"
    And The player "player1@webingo.org" won the line and the player "player2@webingo.org" won the bingo
    And There is a bad line prize and a bingo prize in the current game
    And There is a game named "geiade-game" that has already finished
    When I deliver prizes to the winning players
    Then The response code is 404



  Scenario: The system fails to deliver cause the game didn't finish yet
    Given I login as "admin" with password "password"
    And The player "player1@webingo.org" won the line and the player "player2@webingo.org" won the bingo
    And There is a line prize and a bingo prize in the current game
    And There is a game with named "geiade-game" that didn't finish yet
    When I deliver prizes to the winning players
    Then The response code is 400
















