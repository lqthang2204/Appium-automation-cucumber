@feature-mc-21946 @lazada
Feature: Login function

  @mc-test-login-lazada
  Scenario: Open App lazada
    Given I open application
    And I change the page spec to LoginPage
    And I wait for element option-language with text "Vietnam" to be DISPLAYED
    And I scroll to element option-language with text "Vietnam"
    And I click element option-language with text "Vietnam"
    And I change the page spec to HomePage
#    And I wait for element confirm-option to be ENABLED
    And I perform confirm-option-vietnmese action
#    And I wait for element confirm-option to be ENABLED
#    And I click element confirm-option
    And I change the page spec to index
    And I perform click-voucher-if-existing action
    And I click element user-icon with text "Tài khoản"
    And I click element text-ui with text "Đăng nhập / Đăng ký"
