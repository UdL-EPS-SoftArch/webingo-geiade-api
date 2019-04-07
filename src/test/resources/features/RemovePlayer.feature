Feature: Remove Player
  In order to remove a player to use the app
  As an admin
  I want to remove a player account

  Scenario: Remove a player as admin
    Given I login as "admin" with password "password"
    And There is player with username "user", email "user@webingo.cat" and password "password"
    When I remove a player with username "user"
    Then The response code is 204
    And The player with username "user" has been removed

  Scenario: Remove a player without without authenticating
    Given I'm not logged in
    And There is player with username "user", email "user@webingo.cat" and password "password"
    When I remove a player with username "user" without authenticating
    Then The response code is 401
    And It has not been removed the player with username "user"

  Scenario: Remove a player being a User
    Given I login as "player1" with password "password"
    And There is player with username "user", email "user@webingo.cat" and password "password"
    When I remove a player with username "user" being a User
    Then The response code is 403
    And It has not been removed the player with username "user"

  Scenario: Remove an unexisting player as admin
    Given I login as "admin" with password "password"
    When I remove an unexisting player with username "user"
    Then The response code is 204
