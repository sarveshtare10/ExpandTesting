Feature: User Registration
  @smoke
  Scenario: Register a new user with dynamically generated data
    Given I navigate to the Expand Testing register page
    When I register a new user with random credentials
    Then I should see the successful registration message "Successfully registered, you can log in now."
    And the generated credentials should be saved to Excel