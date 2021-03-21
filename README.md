## Common
Build before compiling the other 2: `mvn clean install`

## Client

How to run: from /client run: `mvn clean compile javafx:run`
Or: `mvn clean compile exec:java -Dexec.mainClass="Main"`
Or: `mvn clean compile exec:java -Dexec.mainClass="Main" -Dexec.args="arg1 arg2 ..."`

## Server
How to run: from /server run: `mvn clean compile exec:java -Dexec.mainClass="Main"`


Things to introduce:
* [DONE] Socket communication 
* Database integration
* Authentication
* [PARTIAL] GUI to Client


Where to use aspects:

