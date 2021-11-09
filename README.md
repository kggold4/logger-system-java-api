# <i>Logger System Built With Java and MySQL</i>

This Logger System can operate a several manipulations on MySQL database with MySQL-Java Connector Like:
1. ```connect(id, password)``` - connect a user to the system
2. ```disconnect()``` - disconnect current the logged in user from the system
3. ```check()``` - checking if there is a user that logged into the system
4. ```create( firstName, lastName, email, password)``` - creating a user in the database
5. ```delete(id, password)``` - deleting a user from the database
6. ```printAllUsers(id, password)``` - printing all the users in the database (given admin id and password)
7. ```getUserName()``` - get the name of the connected user
8. ```getUserId()``` - get the id of the connected user