Feature: retrieve wish list
  by different criteria

  Scenario: query and retrieve wish list which not exist
    Given client had not add any wishlist before
    When client call /v1/wishes/lists?id=1
    Then client receive null wishlist
