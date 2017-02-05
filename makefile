JCC = javac

JFLAGS = -g

default: Iperfer.java


Iperfer.class: Iperfer.java
        $(JCC) $(JFLAGS) Iperfer.java

clean: 
        $(RM) *.class