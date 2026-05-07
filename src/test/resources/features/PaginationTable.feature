Feature: Dynamic Pagination Table

  Background:
    Given I navigate to the Expand Testing dynamic pagination table page

  Scenario: Navigate to the next page
    When I record the first row data of the current page
    And I click on the "Next" pagination button
    Then the first row data should be different from the previous page

  Scenario: Navigate to a specific page number
    When I click on pagination page "2"
    Then the active pagination button should be "2"