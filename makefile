JCC = javac

JFLAGS = -g

default: Iperfer.class

Iperfer.class: Iperfer.java
		$(JCC) $(JFLAGS) Iperfer.java

clean: 
		$(RM) *.class