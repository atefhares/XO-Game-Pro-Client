# XO-Game-Pro
![](https://forthebadge.com/images/badges/made-with-java.svg)
![](http://ForTheBadge.com/images/badges/built-with-love.svg)

A network based Tic tac toe game, this implementation was done as the java project @ ITI intake 40 by students of the open source cloud platform development track.

[Client repo](https://github.com/atefhares/XO-Game-Pro-Client)

[Server repo](https://github.com/atefhares/XO-Game-Pro-Server)

![hg](https://github.com/atefhares/XO-Game-Pro-Client/blob/master/out.gif)

# Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Database Schema](#database-schema)
- [Features](#features)

## Getting Started

how to run the server:

- open project in netbeans or intellij idea
- open File -> Project Properties -> Libraries and make sure that all libraries from libs folder are included
- open ClientController.java and configure the attribute SERVER_ADDRESS to the server ip address
- clean, build and run

how to run the client:
- open project in netbeans or intellij idea
- open File -> Project Properties -> Libraries and make sure that all libraries from libs folder are included
- clean, build and run
- before starting server make sure there is a mysql server at the "localhost" on port 3306
   also add a user named "test" with password "!Pass12345678" plus please allow this user's access on database names "tictactoe"

### Prerequisites

- java 8 or higher recommended
- firewall configured for socket communications.


## Features

Client Side Features:</br>
- login
- SignUp
- play with pc
- play with online friends
- chat while playing
- see who has the highest score in the game
- see who is online offline or busy playing with someone else

Server side Features:</br>
- see a list of all users</br>
- see players status and score</br>
- close and reopen the server</br>


## Built With

* [MySQL](https://dev.mysql.com/downloads/connector/j/) - JDBC Type 4 driver for MySQL
* [JSON-Simple](https://code.google.com/archive/p/json-simple/) -  A simple Java toolkit for JSON


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
