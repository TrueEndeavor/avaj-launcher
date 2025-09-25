NAME = avaj-launcher
SRC_DIR = src
BIN_DIR = bin

SRC_FILES = $(SRC_DIR)/sim/Simulator.java \
			$(SRC_DIR)/sim/Flyable.java \
			$(SRC_DIR)/sim/Coordinates.java \
			$(SRC_DIR)/sim/Aircraft.java \
			$(SRC_DIR)/sim/Helicopter.java \
			$(SRC_DIR)/sim/JetPlane.java \
			$(SRC_DIR)/sim/Baloon.java \
			$(SRC_DIR)/sim/Tower.java \
			$(SRC_DIR)/sim/WeatherTower.java \
			$(SRC_DIR)/sim/WeatherProvider.java \
			$(SRC_DIR)/sim/AircraftFactory.java \
			$(SRC_DIR)/sim/TestTowerFlyable.java \
			$(SRC_DIR)/sim/TestWeatherTypes.java \

JAVAC = javac
JAVA  = java
JAVAP = javap
JFLAGS = -source 7 -target 7 -Xlint:-options -d $(BIN_DIR) -cp $(BIN_DIR)

all:
	rm -rf bin
	@mkdir -p $(BIN_DIR)
	$(JAVAC) $(JFLAGS) $(SRC_FILES)

run: all
	$(JAVA) -cp $(BIN_DIR) sim.Simulator

clean:
	rm -rf $(BIN_DIR)

.PHONY: all run clean