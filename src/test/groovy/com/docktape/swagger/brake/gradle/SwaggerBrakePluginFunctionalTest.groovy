package com.docktape.swagger.brake.gradle

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class SwaggerBrakePluginFunctionalTest extends Specification {
    public static final String GRADLE_VERSION = "8.3"
    public static final String TASK_NAME = "checkBreakingChanges"

    @TempDir
    File testProjectDir

    File buildFile
    File settingsFile

    def setup() {
        buildFile = new File(testProjectDir, 'build.gradle')
        settingsFile = new File(testProjectDir, 'settings.gradle')
    }

    def "checkBreakingChanges task works"() {
        given:
        settingsFile << """
            rootProject.name = 'swagger-brake-gradle-func-test'
        """

        buildFile << """
            plugins {
                id 'java'
                id 'com.docktape.swagger-brake'
            }

            group = 'com.docktape'
            version = '2.0.0-SNAPSHOT' 

            swaggerBrake {
                mavenRepoUrl = "http://localhost:8081/artifactory/libs-release-local"
                mavenSnapshotRepoUrl = "http://localhost:8081/artifactory/libs-snapshot-local"
                newApi = "${testProjectDir.getAbsolutePath().replace('\\', '/')}/resources/main/swagger.yaml"
                testModeEnabled = true
            }
        """

        when:
        def result = createGradleRunner().build()

        then:
        result.task(":checkBreakingChanges").outcome == SUCCESS
    }

    def "checkBreakingChanges task works with single output format"() {
        given:
        settingsFile << """
            rootProject.name = 'swagger-brake-gradle-func-test'
        """

        buildFile << """
            plugins {
                id 'java'
                id 'com.docktape.swagger-brake'
            }

            group = 'com.docktape'
            version = '2.0.0-SNAPSHOT' 

            swaggerBrake {
                mavenRepoUrl = "http://localhost:8081/artifactory/libs-release-local"
                mavenSnapshotRepoUrl = "http://localhost:8081/artifactory/libs-snapshot-local"
                newApi = "${testProjectDir.getAbsolutePath().replace('\\', '/')}/resources/main/swagger.yaml"
                outputFormats = ["HTML"]
                testModeEnabled = true
            }
        """

        when:
        def result = createGradleRunner().build()

        then:
        result.task(":checkBreakingChanges").outcome == SUCCESS
    }

    def "checkBreakingChanges task works with multi output format"() {
        given:
        settingsFile << """
            rootProject.name = 'swagger-brake-gradle-func-test'
        """

        buildFile << """
            plugins {
                id 'java'
                id 'com.docktape.swagger-brake'
            }

            group = 'com.docktape'
            version = '2.0.0-SNAPSHOT' 

            swaggerBrake {
                mavenRepoUrl = "http://localhost:8081/artifactory/libs-release-local"
                mavenSnapshotRepoUrl = "http://localhost:8081/artifactory/libs-snapshot-local"
                newApi = "${testProjectDir.getAbsolutePath().replace('\\', '/')}/resources/main/swagger.yaml"
                outputFormats = ["HTML", "JSON"]
                testModeEnabled = true
            }
        """

        when:
        def result = createGradleRunner().build()

        then:
        result.task(":checkBreakingChanges").outcome == SUCCESS
    }

    def "checkBreakingChanges task works with multiple exclude paths"() {
        given:
        settingsFile << """
            rootProject.name = 'swagger-brake-gradle-func-test'
        """

        buildFile << """
            plugins {
                id 'java'
                id 'com.docktape.swagger-brake'
            }

            group = 'com.docktape'
            version = '2.0.0-SNAPSHOT' 

            swaggerBrake {
                mavenRepoUrl = "http://localhost:8081/artifactory/libs-release-local"
                mavenSnapshotRepoUrl = "http://localhost:8081/artifactory/libs-snapshot-local"
                newApi = "${testProjectDir.getAbsolutePath().replace('\\', '/')}/resources/main/swagger.yaml"
                excludedPaths = ["/path", "/test"]
                testModeEnabled = true
            }
        """

        when:
        def result = createGradleRunner().build()

        then:
        result.task(":checkBreakingChanges").outcome == SUCCESS
    }

    private GradleRunner createGradleRunner() {
        GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withDebug(true)
                .withPluginClasspath()
                .withArguments(TASK_NAME)
                .withGradleVersion(GRADLE_VERSION)
    }
}
