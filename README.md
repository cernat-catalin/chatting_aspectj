## Common
Build before compiling the other 2: `mvn clean install`

## Client

How to run: from /client run: `mvn clean compile javafx:run`
Or: `mvn clean compile exec:java -Dexec.mainClass="org.chatting.client.Main"`
Or: `mvn clean compile exec:java -Dexec.mainClass="org.chatting.client.Main" -Dexec.args="arg1 arg2 ..."`

## Server
How to run: from /server run: `mvn clean compile exec:java -Dexec.mainClass="org.chatting.server.org.chatting.server.Main"`


Things to introduce:
* [DONE] Socket communication 
* [DONE] Database integration
* [ ] Authentication
* [DONE] GUI to Client
* [ ] Perfect GUI


Where to use aspects:
* [DONE] Transaction management
* [ ] Security aspect
* [ ] Event Logging aspect
* [ ] General logging aspect
* [ ] Performance aspect
* [ ] Error Handling aspect
