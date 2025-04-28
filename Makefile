# Makefile for P213 Integration

startServer:
	javac -cp ../junit5.jar:. *.java
	java WebApp 8080

runAllTests:
	javac -cp ../junit5.jar:. *.java
	java -jar ../junit5.jar --class-path=. --select-class=FrontendTests

clean:
	rm -f *.class

