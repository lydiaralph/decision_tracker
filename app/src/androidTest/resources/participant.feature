User Story: End user as potential research participant

# Basic application use

Scenario: End user is able to select options to make a decision
    As an end user of the mobile application
    I am able to select options to make a decision
    So that researchers can see what decision I make

Scenario: End user is able to track moods alongside decisions
    As an end user of the mobile application
    I am able to track my moods alongside my decisions
    So that researchers can see how moods affect my decision-making

Scenario: End user is able to track other factors alongside decisions
    As a user of the mobile application
    I am able to track other factors alongside my decisions
    So that I can see how other factors affect my decision-making

Scenario: End user is able to track configure decisions to track
    As a user of the mobile application
    I am able to configure which decisions and options to track
    So that I can track decisions over a period of time

Scenario: End user is able to view results of a decision
    As a user of the mobile application
    I am able to view the results of a decision when the tracking period has expired
    So that I know what I decided for that decision

Scenario: End user is able to terminate decision tracking early
    As a user of the mobile application
    I am able to terminate the tracking of a decision early
    So that I can view results for the decision
    And no more records for that decision can be entered


# User protection

Scenario: End user is able to delete a decision from the database
    As an end user of the mobile application
    I am able to delete a decision from the database
    And no associated information is left in the database.

Scenario: End user is able to opt in to taking part in research
    As an end user of the mobile application
    I am able to opt in to taking part in research
    And data from my mobile device will be collected

Scenario: End user is opted out of research by default
    As an end user of the mobile application
    When I do not opt in to taking part in research
    Then no data from my mobile device will be collected

Scenario: End user is able to opt out from research after opting in
    As an end user of the mobile application
    I am able to opt in to taking part in research
    And when I opt out of taking part in research
    Then no data from my mobile device will be collected
    And all data on my device will be deleted
    So that no data remains on my device
    And no data remains in the remote database

Scenario: End user is able to delete all data
    As an end user of the mobile application
    I am able to delete all my data collected by the app
    And all data on my device will be deleted
    So that no data remains on my device
    And no data remains in the remote database