Feature: Register Player
  In order to allow a new player to use the app
  As an admin
  I want to register a new player account

  Scenario: Register new player as admin
    Given I login as "admin" with password "password"
    When I register a new player with username "player", email "player@webingo.org" and password "password"
    Then The response code is 201
    And It has been created a player with username "player" and email "player@webingo.org", the password is not returned

  Scenario: Try to register new player without authenticating
    Given I'm not logged in
    When I register a new player with username "player", email "player@webingo.org" and password "password"
    Then The response code is 401
    And It has not been created a player with username "player"

  Scenario: Register new player with empty password
    Given I login as "admin" with password "password"
    When I register a new player with username "player", email "player@webingo.org" and password ""
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a player with username "player"

  Scenario: Register new player with empty email
    Given I login as "admin" with password "password"
    When I register a new player with username "player", email "" and password "password"
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a player with username "player"

  Scenario: Register new player with invalid email
    Given I login as "admin" with password "password"
    When I register a new player with username "player", email "playerawebingo.org" and password "password"
    Then The response code is 400
    And The error message is "must be a well-formed email address"
    And It has not been created a player with username "player"

  Scenario: Register new player with password shorter than 8 characters
    Given I login as "admin" with password "password"
    When I register a new player with username "player", email "player@webingo.org" and password "pass"
    Then The response code is 400
    And The error message is "length must be between 8 and 256"
    And It has not been created a player with username "player"

  Scenario: Change Player value and still can access player
    Given I login as "player1" with password "password"
    When I change "player1@webingo.org" isPlaying to true
    Then "player1@webingo.org" isPlaying value is set to true

  Scenario: Change the password of an existing player
    Given I login as "player1" with password "password"
    When I change "player1@webingo.org" password to "password2"
    Then The response code is 200
    And I login as "player1" with password "password2"
    And It has been created a player with username "player1" and email "player1@webingo.org", the password is not returned

