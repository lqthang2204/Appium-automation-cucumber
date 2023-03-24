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
    And I wait for element icon-at-page-home with text "Astra" to be NOT_DISPLAYED
#    And I click element icon-at-page-home with text "navigation_navigate_profile"
#    And I change the page spec to profile_user
#    And I perform click-quan-ly-don-hang-if-exist action
#    And I wait for element phone-number-field to be DISPLAYED
#    And I verify the text for element phone-number-field is ""
#    And I wait for element back-button to be ENABLED
#    And I click element back-button
#    And I change the page spec to HomeTiki
#    And I click element icon-at-page-home with text "Trang chủ"
#    And I wait for element search-field to be ENABLED
#    And I click element search-field
#    And I wait for element title-suggestion to be DISPLAYED
#    And I type "giay adidas" into element search-product
#    And I click search button in keyboard
#    And I change the page spec to product
#    And I perform click-deny-if-exist action
    #    And I click keyboard ENTER button on element search-product
#    And I save text for element title-suggestion with key "title"
#    And I verify the text for element title-suggestion is "KEY.title"

#  @rerun-tiki
#  Scenario: Open App Tiki
#    Given I open application
#    And I change the page spec to HomeTiki
#    And I wait for element icon-at-page-home with text "Trang chủ" to be DISPLAYED
#    And I wait for element icon-at-page-home with text "Danh mục" to be DISPLAYED
#    And I wait for element icon-at-page-home with text "Tin mới" to be DISPLAYED
#    And I wait for element icon-at-page-home with text "navigation_navigate_profile" to be DISPLAYED
#    And I wait for element icon-at-page-home with text "Astra" to be DISPLAYED



