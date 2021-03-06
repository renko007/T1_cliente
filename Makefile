JFLAGS = -g
JC = javac
JVM = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Cliente.java \

MAIN=Cliente

default: classes

classes: $(CLASSES:.java=.class)

%.class: %.java
	$(JC) $(JFLAGS) *.java

run: classes
	$(JVM) $(MAIN)

clean:
	$(RM) *.class
