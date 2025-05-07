# Java Features Demo (Java 17-25)

This project demonstrates the most important Java features introduced between Java 17 and 25. Each feature is showcased in a separate package with practical examples.

## Features Covered

1. **Pattern Matching for switch** (JEP 406, 420, 427, 433, 441)
   - Finalized in Java 21
   - Package: `pl.vm.features.patternmatching`

2. **Record Patterns** (JEP 405, 432, 440)
   - Finalized in Java 21
   - Package: `pl.vm.features.records`

3. **Sealed Classes** (JEP 360, 397, 409)
   - Finalized in Java 17
   - Package: `pl.vm.features.sealed`

4. **Sequenced Collections** (JEP 431)
   - Finalized in Java 21
   - Package: `pl.vm.features.sequenced`

5. **Stream Gatherers** (JEP 461, 473, 485)
   - Finalized in Java 24
   - Package: `pl.vm.features.gatherers`

6. **Virtual Threads** (JEP 425, 436, 444, 491)
   - Finalized in Java 21, improved in Java 24
   - Package: `pl.vm.features.virtualthreads`

7. **@snippet Javadoc** (JEP 413)
   - Standard in Java 18
   - Package: `pl.vm.features.snippets`

## Requirements

- Java 21 or later
- Maven 3.8 or later

## Building and Running

```bash
mvn clean install
```

## Project Structure

```
src/
├── main/
│   └── java/
│       └── pl/
│           └── vm/
│               └── features/
│                   ├── patternmatching/
│                   ├── records/
│                   ├── sealed/
│                   ├── sequenced/
│                   ├── gatherers/
│                   ├── virtualthreads/
│                   └── snippets/
└── test/
    └── java/
        └── pl/
            └── vm/
                └── features/
                    └── [feature packages]
```

Each feature package contains:
- Example classes demonstrating the feature
- Unit tests showing usage patterns
- Javadoc documentation with `@snippet` examples where applicable 