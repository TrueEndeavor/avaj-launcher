NAME = avaj-launcher
SRC_DIR = src
BIN_DIR = bin

SRC_FILES = $(SRC_DIR)/sim/Simulator.java \

JAVAC = javac
JAVA  = java
JAVAP = javap
JFLAGS = -source 7 -target 7 -Xlint:-options -d $(BIN_DIR) -cp $(BIN_DIR)

all:
	@mkdir -p $(BIN_DIR)
	$(JAVAC) $(JFLAGS) $(SRC_FILES)

run: all
	$(JAVA) -cp $(BIN_DIR) sim.Simulator

clean:
	rm -rf $(BIN_DIR)

.PHONY: all run clean