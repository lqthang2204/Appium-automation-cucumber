@tiki
Feature: Login function

  @mc-test-tiki
  Scenario: Open App Tiki
    Given I open application
    And I change the page spec to HomeTiki
    And I wait for element icon-at-page-home with text "Trang chủ" to be DISPLAYED
    And I wait for element icon-at-page-home with text "Danh mục" to be DISPLAYED
    And I wait for element icon-at-page-home with text "Tin mới" to be DISPLAYED
    And I wait for element icon-at-page-home with text "navigation_navigate_profile" to be DISPLAYED
    And I wait for element icon-at-page-home with text "Astra" to be DISPLAYED
    And I click element icon-at-page-home with text "navigation_navigate_profile"
    And I change the page spec to profile_user
    And I wait for element quan-ly-don-hang to be DISPLAYED
    And I click element quan-ly-don-hang
    And I wait for element phone-number-field to be DISPLAYED
