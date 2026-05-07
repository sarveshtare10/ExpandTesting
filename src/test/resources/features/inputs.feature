Feature: Inputs Page Automation

  Scenario Outline: Successfully fill out and display inputs from Excel
    Given I navigate to the Expand Testing inputs page
    When I fill out the form using data from row "<RowNumber>" of the Excel file
    And I click the Display Inputs button
    Then I should see the entered values from row "<RowNumber>" displayed correctly on the screen

    Examples:
      | RowNumber |
      | 1         |

  Scenario: Clear input fields
    Given I navigate to the Expand Testing inputs page
    When I enter "123" in the number field
    And I enter "test" in the text field
    And I click the Clear Inputs button
    Then all input fields should be empty