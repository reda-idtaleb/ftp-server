# FTP server
- Authors :
    - LAGHMICH Manal 
    - ID-TALEB Réda
- Date : 02/02/2022

# Introduction
The FTPServer project aims to create a remote FTP server in which the user can deposit and/or find these files as well as other functionalities. The client interacts with the server by sending it commands in order to perform an action. Depending on the command requested, the server performs the right action and returns the result to the client.

# How to execute
On a shell consol, type the following commands:
1. To clean the project
```
mvn clean
``` 
2. To compile and build the .jar file of the project
```
mvn package
``` 
3. To execute the .jar file
```
java -jar target/sr1-tp2-reda-laghmich-0.0.1-SNAPSHOT.jar
``` 

# Notes
All the files are stored in the [server_data](server_data/) directory.