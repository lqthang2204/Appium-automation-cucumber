@tiki
Feature: Login function

  @mc-test-tiki
  Scenario: Open App Tiki
    And I become a random user
    And I am user1 created by the file
    Given I open application
    And I change the page spec to HomeTiki
    And I wait for element icon-at-page-home with text "Trang chủ" to be DISPLAYED
    And I wait for element icon-at-page-home with text "Danh mục" to be DISPLAYED
    And I wait for element icon-at-page-home with text "Tin mới" to be DISPLAYED
    And I wait for element icon-at-page-home with text "navigation_navigate_profile" to be DISPLAYED
    And I wait for element icon-at-page-home with text "Astra" to be DISPLAYED
    And I verify attribute element icon-at-page-home with text "navigation_navigate_profile" has css property focusable with value "false"
    And I verify attribute element icon-at-page-home with text "navigation_navigate_profile" has css property package with value "vn.tiki.app.tikiandroid"
    And I verify attribute element icon-at-page-home with text "navigation_navigate_profile" has css property class with value "android.widget.ImageView"
    And I verify attribute element icon-at-page-home with text "navigation_navigate_profile" has css property checked with value "false"
    And I verify attribute element icon-at-page-home with text "navigation_navigate_profile" has css property bounds with value "[708,1218][732,1242]"
    And I wait for element icon-at-page-home with text "navigation_navigate_profile" to be ENABLED
    And I click element icon-at-page-home with text "navigation_navigate_profile"
    And I change the page spec to profile_user
    And I perform click-quan-ly-don-hang-if-exist action
    And I wait for element phone-number-field to be DISPLAYED
    And I verify the text for element phone-number-field is ""
#    And I wait for element back-button to be ENABLED
#    And I click element back-button
    And I click Back button in keyboard
    And I change the page spec to HomeTiki
    And I wait for element icon-at-page-home with text "Trang chủ" to be ENABLED
    And I click element icon-at-page-home with text "Trang chủ"
    And I wait for element search-field to be ENABLED
    And I become a random user
    And I perform action-search-product action with override values
      | search-product | USER.1.dob |
#    And I click element search-field
#    And I wait for element search-product to be DISPLAYED
#    And I type "giay adidas" into element search-product
    And I click Search button in keyboard
    And I change the page spec to product
    And I perform click-deny-if-exist action
    And I wait for element product-adidas to be DISPLAYED
    And I save text for element product-adidas with key "product-one"
    And I change the page spec to product
    And I click element search-field
#    And I change the page spec to HomeTiki
#    And I type "KEY.product-one" into element search-product
    And I perform search-product action with override values
      |search-product | KEY.product-one    |
    And I click Search button in keyboard
    And I change the page spec to product
    And I perform click-deny-if-exist action
    And I wait for element product-adidas to be DISPLAYED
    And I verify the text for element product-adidas is "KEY.product-one"

#    And I click keyboard ENTER button on element search-product
#    And I save text for element title-suggestion with key "title"
#    And I verify the text for element title-suggestion is "KEY.title"

#  @rerun-tiki
# Scenario Outline: Open App Tiki
#    Given I open application
#    And I change the page spec to HomeTiki
#    And I wait for element icon-at-page-home with text "Trang chủ" to be DISPLAYED
#    And I wait for element icon-at-page-home with text "Danh mục" to be DISPLAYED
#    And I wait for element icon-at-page-home with text "Tin mới" to be DISPLAYED
#    And I wait for element icon-at-page-home with text "navigation_navigate_profile" to be DISPLAYED
#    And I wait for element icon-at-page-home with text "Astra" to be DISPLAYED
#    And I click element icon-at-page-home with text "navigation_navigate_profile"
#    And I change the page spec to profile_user
#    And I perform click-quan-ly-don-hang-if-exist action
#    And I wait for element phone-number-field to be DISPLAYED
#    And I verify the text for element phone-number-field is ""
#    And I click Back button in keyboard
#    And I change the page spec to HomeTiki
#    And I click element icon-at-page-home with text "Trang chủ"
#    And I wait for element search-field to be DISPLAYED
##    And I perform action-search-product action with override values
##      |search-product | giay adidas              |
#    And I click element search-field
#    And I wait for element search-product to be DISPLAYED
#    And I type <product> into element search-product
#
#    Examples:
#      | product        |
#      | "mobile"  |
#      | "tivi"  |




