Feature: Deliver Prizes
  In order to allow players to gain money with the jackpot

  Scenario: The system delivers prizes to two winners done correctly
    Given There is a game with named "geiade-game" that has already finished
    And The player "player1@webingo.cat" won the line
    And The player "player2@webingo.cat" won the bingo
    And There is a line prize and a bingo prize in the current game
    When I deliver prizes to the players with email "player1@webingo.cat" and "player2@webingo.cat"
    Then The response code is 201
    And I delete the bingo and line prizes
    Then The response code is 201
    And I update the wallet of the players who won

  Scenario: The system delivers prizes correctly to the same player who won the lineprize and the bingorize
    Given There is a game with name "geiade-game" that has finished


  Scenario: The system fails to deliver cause there is a bad lineprize or bingoprize in the game
    Given

  Scenario: The system fails to deliver cause the game didn't finish yet
    Given













