Feature: Verify API endpoints are valid

  @ReceiveProductDetails
  Scenario Outline: Send a valid Request to get product details
    Given I send a request to the URL "/api/product" to get product details
    Then the response will return status 200 and id <id> and names "<name>"

    Examples:
      |id    |name|
      |1        |A         |
      |2        |A         |
