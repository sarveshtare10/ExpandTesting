Feature: User Login

  Background:
    Given I navigate to the Expand Testing login page

  @smoke
  Scenario: Successfully login with valid credentials
    When I enter "practice" as username
    And I enter "SuperSecretPassword!" as password
    And I click the login button
    Then I should be redirected to the secure page
    And I should see a "You logged into a secure area!" message


  Scenario: Fail to login with invalid username
    When I enter "invalid_user" as username
    And I enter "Practice123!" as password
    And I click the login button
    Then I should see an "Your username is invalid!" error message

  Scenario: Fail to login with invalid password
    When I enter "practice" as username
    And I enter "invalid_password" as password
    And I click the login button
    Then I should see a "Your password is invalid!" error message

  Scenario: Logout from the application
    Given I am logged in
    When I click the logout button
    Then I should be redirected to the login page
    And I should see a "You logged out of the secure area!" message