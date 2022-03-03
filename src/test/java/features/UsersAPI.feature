Feature: Users API
	Scenario: Happy flow
		When I send GET users request
		Then Status code is 200
		And Unique user "Delphine" is found
		And I collect users ID
		And I get posts of user by his ID
		And I get response body for all posts
		When I create a list of all comments
#		And I received list of emails
#		Then I validate emails