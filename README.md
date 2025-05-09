# Java 17-24 Features Demo

This project demonstrates various features introduced in Java versions 17 through 24. Each package contains examples and tests showcasing specific Java features.

## Project Structure

The project is organized into feature-specific packages under `src/main/java/pl/vm/features/`:

### 1. Records (Java 16)
- Package: `records`
- [JEP 395: Records](https://openjdk.org/jeps/395)
- Demonstrates the use of records for creating immutable data classes
- Features compact constructor syntax and automatic implementations of equals(), hashCode(), and toString()

### 2. Pattern Matching
- Package: `patternmatching`
- [JEP 441: Pattern Matching for switch](https://openjdk.org/jeps/441) (Java 21)
- [JEP 427: Pattern Matching for switch (Preview)](https://openjdk.org/jeps/427) (Java 17)
- Shows advanced pattern matching capabilities in switch expressions and statements
- Includes type patterns, guarded patterns, and null handling

### 3. Sealed Classes
- Package: `sealed`
- [JEP 409: Sealed Classes](https://openjdk.org/jeps/409) (Java 17)
- Demonstrates how to create restricted class hierarchies
- Shows the use of `sealed`, `permits`, `non-sealed`, and `final` modifiers

### 4. Virtual Threads
- Package: `virtualthreads`
- [JEP 444: Virtual Threads](https://openjdk.org/jeps/444) (Java 21)
- Showcases lightweight threads for high-throughput concurrent applications
- Includes examples of thread-per-request server implementation

### 5. Sequenced Collections
- Package: `sequenced`
- [JEP 431: Sequenced Collections](https://openjdk.org/jeps/431) (Java 21)
- Demonstrates new sequence-oriented collection interfaces
- Shows operations like `getFirst()`, `getLast()`, `addFirst()`, `addLast()`, and `reversed()`

### 6. Stream Gatherers
- Package: `gatherers`
- [JEP 461: Stream Gatherers (Preview)](https://openjdk.org/jeps/461) (Java 24)
- Shows how to use the new Gatherer interface for stream operations
- Includes examples of custom stream transformations

## Prerequisites

- Java 24 (OpenJDK 24.0.1 or later)
- Maven 3.9.x or use of maven wrapper inside of this project

## Building and Running

```bash
# Build the project
./mvnw clean install

# Run tests
./mvnw mvn test
```

## Configuration

The project uses the following Java and Maven configuration:

- Java 24 with preview features enabled
- Maven Compiler Plugin 3.12.1
- JUnit Jupiter 5.10.1 for testing

Key configurations in `pom.xml`:
- Preview features enabled
- Native access enabled
- Required module opens for testing

## Test Coverage

The project includes comprehensive tests for each feature:
- Records: 9 tests
- Gatherers: 6 tests
- Sealed Classes: 5 tests
- Sequenced Collections: 3 tests
- Virtual Threads: 3 tests
- Pattern Matching: 8 tests

## License

This project is open source and available under the MIT License.

## Contributing

Feel free to contribute by:
1. Forking the repository
2. Creating a feature branch
3. Submitting a pull request

## Additional Resources

- [Java 24 Documentation](https://docs.oracle.com/en/java/javase/24/)
- [JDK Enhancement Proposals (JEPs)](https://openjdk.org/jeps/0)
- [Java Language Updates](https://docs.oracle.com/en/java/javase/24/language/java-language-changes.html) 