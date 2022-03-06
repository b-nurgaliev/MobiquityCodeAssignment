Feature: Users API

	Scenario: Comment section email validation
		When I send GET users request
		Then Status code is 200
		And Unique user "Delphine" is found
		And I send GET posts request
		And Status code is 200
		And All posts have correct user id
		And All comments have valid emails

	Scenario: Get user by username
		When I request user by username "Delphine"
		Then Status code is 200
		And Unique user "Delphine" is found

	Scenario: Requesting non-existing user by username
		When I request user by username "QwertyAsdfg"
		Then Status code is 404

	Scenario: Requesting non-existing user by id
		When I request user by id "99999999999999999999999"
		Then Status code is 404