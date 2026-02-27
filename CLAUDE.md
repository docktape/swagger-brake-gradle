# swagger-brake-gradle

A Gradle plugin that integrates swagger-brake API breaking change detection into Gradle builds, exposing a `checkBreakingChanges` task.

## Tech Stack

- Language: Java 21
- Build: Gradle
- Testing: Groovy, Spock Framework
- Distribution: Gradle Plugin Portal
- Release: JReleaser

## Commands

```bash
# Build and run tests
./gradlew clean build

# Publish plugin and release
./gradlew clean publishPlugins jreleaserFullRelease

# Run tests only
./gradlew test
```

## Project Structure

- `src/main/java/` - Plugin source (task and plugin implementations)
- `src/test/groovy/` - Spock specification tests
- `build.gradle` - Build configuration

## Key Notes

- Tests are written in Groovy using the Spock framework
- The plugin registers a `checkBreakingChanges` task of type `SwaggerBrakeTask`
- Published to the Gradle Plugin Portal; plugin ID is `io.github.swagger-brake`
