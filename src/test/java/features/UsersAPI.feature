Feature: Users API
	Scenario: Happy flow
		When I send GET users request
		Then Status code is 200
		And Unique user "Delphine" is found
