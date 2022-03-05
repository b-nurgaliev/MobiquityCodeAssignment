Feature: Users API

	Scenario: Comment section email validation
		When I send GET users request
		Then Status code is 200
		And Unique user "Delphine" is found
		And I collect users ID
		And I get posts of user by his ID
		And All posts have correct user id
		And I create a list of posts
		When I create a list of all comments
		And I received list of emails
		Then All comments have valid emails