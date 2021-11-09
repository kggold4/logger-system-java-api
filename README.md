# <i>Logger System Build With Java and MySQL</i>

This Logger System can operate a several manipulations on MySQL database with MySQL-Java Connector Like:
1. ```connect``` - connect a user to the system
2. ```disconnect``` - disconnect a user to the system
3. ```check``` - checking if there is an user that logged in
4. ```create( firstName, lastName, email, password)``` - creating a user in the database
5. ```delete(id, password)``` - deleting a user from the database
6. ```printAllUsers(id, password)``` - printing all the users in the database (given admin id and password)
7. ```getUserName``` - get the name of the connected user
8. ```getUserId()``` - get the id of the connected user