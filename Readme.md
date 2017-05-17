Run app
=====================

To start app you need build project with command:

    mvn clean install

Program work in two mode: client and server.
When run program in argument pass mode and port

***First need start server:***

    java -jar target/message-game.jar server 8090

***Then it should be launched in client mode, run two client in separate terminal window:***

    java -jar target/message-game.jar client 8090
