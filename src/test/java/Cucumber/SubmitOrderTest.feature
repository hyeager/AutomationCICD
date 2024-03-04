
@tag
Feature: Purchase the order from Ecommerce WebSite.
  I want to use this template for my feature file

  Background:
  Given I landed on Ecommerce page

  @Regression
  Scenario Outline: Positive test of purchasing order
    Given Logged in with username <name> and password <password>
    When I add product <productName> to cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage 

    Examples: 
      | name  									| password		| productName |
      | newuserone@yopmail.com 	| Password@1 	| ZARA COAT 3 |
