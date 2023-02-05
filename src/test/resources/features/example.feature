@feature-mc-21946 @regression
Feature: Login function

  @mc-test
    Scenario: Open App mobile
    Given I open application
    And I change the page spec to LoginPage
    And I click element option-vietnam with text "Vietnam"
    And I wait for element option-vietnam to be DISPLAYED
    And I click element option-language with text "Vietnam"
    And I change the page spec to HomePage
    And I wait for element confirm-option to be ENABLED
    And I click element confirm-option

