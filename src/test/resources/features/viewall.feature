Feature: View all Students
  Scenario Outline: Valid Student is visible
    Given I am logged in with valid credentials
    When I register a student with valid credentials:
      | name     | mobile       | email   | cgpa | department | backlog |
      | <name>   | <mobile>     | <email> | <cgpa> | <department> | <backlog> |
    And I click on View All Students
    Then I see that the student entered is present in the table

    Examples:
      | name         | mobile     | email            | cgpa | department | backlog |
      | John Doe     | 9876543210 | john@abc.com     | 8.2  | CSE        | 1       |
  Scenario: Verify view student list after page reload
    Given I am logged in with valid credentials
    When I click on student menu
    And I click on View All Students
    And I reload the page
    Then I should still see the student list table

  Scenario: Verify long names and values do not overflow
    Given I am logged in with valid credentials
    When I register a student with valid credentials:
      | name                 | mobile       | email                  | cgpa | department               | backlog |
      | Alexander Maximilian | 9999999999   | longname@example.com   | 9.5  | Department of Philosophy | 1       |
    And I click on View All Students
    Then I should see that the student details are displayed without overflow

  Scenario: Verify responsive view on mobile
    Given I am logged in with valid credentials on a mobile viewport
    When I click on student menu
    And I click on View All Students
    Then The student table should be responsive

  Scenario: Verify table headers are correct
    Given I am logged in with valid credentials
    When I click on student menu
    And I click on View All Students
    Then I should see correct table headers
