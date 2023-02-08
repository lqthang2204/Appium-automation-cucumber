@feature-mc-21946 @regression
Feature: Login function

  @mc-test
    Scenario: Open App mobile
    Given I open application
    And I change the page spec to LoginPage
    And I click element option-language with text "Vietnam"
    And I change the page spec to HomePage
    And I wait for element confirm-option to be ENABLED
    And I click element confirm-option
    And I change the page spec to IndexPage
    And I wait for element search-field to be DISPLAYED
    And I click element search-field
#    And I click into element have text "KHÔNG, CẢM ƠN" to be DISPLAYED
    And I wait for element search-input to be DISPLAYED
    And I type "áo gió" into element search-input
    And I wait for element search-button to be ENABLED
    And I click element search-button

