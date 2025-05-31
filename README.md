# A51597_TP4 - Java Colony Management Game

## Overview

This project is a Java-based simulation game developed as part of the MOP (Modelação e Otimização de Processos) course at Instituto Politécnico de Lisboa. The main objective is to manage a colony, balancing resources, population, and structures, while responding to random events. The project is designed to demonstrate proficiency in Java programming, object-oriented design, and software engineering best practices.

## Features

- **Colony Simulation:** Manage resources (wood, food, stone, metal), settlers, and structures.
- **Event System:** Random events affect the colony, requiring player choices and impacting outcomes.
- **Resource Management:** Build structures, assign settlers, and optimize production.
- **Persistence:** Save and load game state using Java serialization and custom XML parsing.
- **Console UI:** Interact with the game via a text-based console interface.
- **Custom Exceptions:** Robust error handling for game logic and file operations.
- **Settings Management:** Load and override game settings from XML files.

## Java Techniques and Concepts Demonstrated

- **Object-Oriented Programming:** Extensive use of classes, inheritance, interfaces, and encapsulation.
- **Enums:** Used for types such as structure types, event types, and settler names.
- **Interfaces:** For defining impactable behaviors and UI abstraction.
- **Exception Handling:** Custom exceptions for game-specific error cases.
- **Serialization:** Save/load game state using Java's Serializable interface.
- **File I/O:** Read/write operations for both binary and XML files.
- **XML Parsing:** Custom XML parser for settings and colony import/export.
- **Design Patterns:** Factory pattern for structure and event creation.
- **Collections Framework:** Use of lists for managing settlers and structures.
- **Logging:** Integration with Log4j for debug and error logging.
- **Javadoc:** Comprehensive documentation for all classes and methods.

## Project Structure

- `src/main/java/tps/tp4/` - Core game logic and main classes.
- `src/main/java/tps/tp4/structures/` - Structure classes and factory.
- `src/main/java/tps/tp4/settlers/` - Settler class and related logic.
- `src/main/java/tps/tp4/Events/` - Event system and event types.
- `src/main/java/tps/tp4/Interfaces/` - Interfaces for impactable behaviors and UI.
- `src/main/java/tps/tp4/errors/` - Custom exception classes.
- `src/main/java/tps/tp4/ui/` - User interface abstraction and console implementation.
- `src/main/java/tps/tp4/settings/` - XML settings files.
- `docs/` - Documentation, UML diagrams, and Javadoc output.

## How to Run

1. **Build the project** using Maven or your preferred Java IDE.
2. **Run the main class:** `tps.tp4.App`
3. **Follow the console prompts** to start a new game, load a save, or manage your colony.

## Evaluation Focus

This project is intended to showcase:

- Mastery of Java language fundamentals and advanced features.
- Ability to design and implement a modular, extensible application.
- Proper use of object-oriented principles and design patterns.
- Robust error handling and user feedback.
- Clear, maintainable code with thorough documentation.

---

*Developed for academic evaluation. All code is original and demonstrates individual understanding of Java programming concepts.*
