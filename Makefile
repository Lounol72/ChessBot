
JAVAC = javac
JAVA = java

# cherche tous les .java sous src (récursif)
SRC := $(shell find src -type f -name "*.java")
CLASS_FILES := $(SRC:.java=.class)

.PHONY: all compile run clean

all: compile run

# compile tous les fichiers trouvés
compile:
	$(JAVAC) $(SRC)

# les .class sont générés dans l'arborescence src/ selon les packages
run: compile
	$(JAVA) -cp src main.Main

clean:
	$(RM) $(CLASS_FILES)
