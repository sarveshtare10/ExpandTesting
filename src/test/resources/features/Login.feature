Feature: User Login

  Background:
    Given I navigate to the Expand Testing login page

  @smoke
  Scenario Outline: Successfully login with valid credentials from Excel
    When I login with credentials from row "<RowNumber>" of the registered users Excel file
    Then I should be redirected to the secure page
    And I should see a "You logged into a secure area!" message
    And I should see a welcome message containing my username from row "<RowNumber>" of the registered users Excel file

    Examples:
      | RowNumber |
      | 1         |

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