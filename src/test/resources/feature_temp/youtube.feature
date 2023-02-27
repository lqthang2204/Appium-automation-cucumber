@feature-mc-219467 @mc-youtube
Feature: test app youtube

  @mc-youtube
  Scenario: Open youtube application
    Given I open application
    And I change the page spec to youtube
    And I wait for element search-icon to be DISPLAYED
    And I click element search-icon
#    And I wait for element search-field to be DISPLAYED
#    And I click element search-field
    And I wait for element search-field to be DISPLAYED
    And I type "QUẢ PHỤ TƯỚNG" into element search-field
#    And I click element search-icon

  @mc-youtube-2
  Scenario: Open youtube application
    Given I open application
    And I change the page spec to youtube
    And I wait for element search-icon to be DISPLAYED
    And I click element search-icon
#    And I wait for element search-field to be DISPLAYED
#    And I click element search-field
    And I wait for element search-field to be DISPLAYED
    And I type "QUẢ PHỤ TƯỚNG 2" into element search-field
#    And I click element search-icon

