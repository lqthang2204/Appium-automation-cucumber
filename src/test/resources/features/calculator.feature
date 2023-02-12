@feature-mc-219467
Feature: test app calculator

  @mc-calculator
  Scenario: Open App mobile
    Given I open application
    And I change the page spec to calculator
    And I wait for element divide-button to be DISPLAYED
    And I click element number-option with text "8"
    And I click element divide-button
    And I click element number-option with text "2"
#    And I click element number-option with text "5"
    And I click element equal-button
#    And I verify the text for element result-final is "4"

