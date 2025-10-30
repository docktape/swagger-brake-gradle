package com.docktape.swagger.brake.gradle


import org.gradle.api.GradleException
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.util.ClosureBackedAction
import spock.lang.Specification

class SwaggerBrakePluginTest extends Specification {
    def "checkBreakingChanges task is added when plugin is applied along with java plugin"() {
        given:
        def project = ProjectBuilder.builder().withName("test-project").build()
        project.pluginManager.apply('java')

        when:
        project.pluginManager.apply(SwaggerBrakePlugin)

        then:
        assert project.tasks.findByName("checkBreakingChanges") != null
    }

    def "Plugin fails when check task is missing"() {
        given:
        def project = ProjectBuilder.builder().withName("test-project").build()

        when:
        project.pluginManager.apply(SwaggerBrakePlugin)

        then:
        GradleException e = thrown()
        assert e.getCause().message.contains('applied')
        assert e.getCause().message.contains('check')
    }

    def "Plugin properties can be set with simple strings"() {
        given:
        def project = ProjectBuilder.builder().withName("test-project").build()
        project.group = 'group.test'
        project.pluginManager.apply('java')

        def expectedMavenRepoUrl = 'something'
        def expectedNewApi = 'somethingElse'
        def expectedGroupId = 'simple-group'
        def expectedArtifactId = 'artifact-id'
        def expectedOutputFilePath = 'output-path'
        def expectedOutputFormats = ['JSON']
        def expectedMavenRepoUsername = 'username'
        def expectedMavenRepoPassword = 'password'
        def expectedOldApi = 'somethingOld'

        when:
        project.pluginManager.apply(SwaggerBrakePlugin)
        project.extensions.configure(SwaggerBrakeExtension, new ClosureBackedAction<SwaggerBrakeExtension>({
            mavenRepoUrl = expectedMavenRepoUrl
            newApi = expectedNewApi
            groupId = expectedGroupId
            artifactId = expectedArtifactId
            outputFilePath = expectedOutputFilePath
            outputFormats = expectedOutputFormats
            mavenRepoUsername = expectedMavenRepoUsername
            mavenRepoPassword = expectedMavenRepoPassword
            oldApi = expectedOldApi
        }))

        then:
        def extension = project.extensions.findByName("swaggerBrake")
        assert extension != null
        assert extension.mavenRepoUrl.get() == expectedMavenRepoUrl
        assert extension.newApi.get() == expectedNewApi
        assert extension.groupId.get() == expectedGroupId
        assert extension.artifactId.get() == expectedArtifactId
        assert extension.outputFilePath.get() == expectedOutputFilePath
        assert extension.outputFormats.get() == expectedOutputFormats
        assert extension.mavenRepoUsername.get() == expectedMavenRepoUsername
        assert extension.mavenRepoPassword.get() == expectedMavenRepoPassword
        assert extension.oldApi.get() == expectedOldApi
    }


    def "Plugin properties can be set with gstrings"() {
        given:
        def project = ProjectBuilder.builder().withName("test-project").build()
        project.group = 'group.test'
        project.pluginManager.apply('java')

        def expectedMavenRepoUrl = "${project.name}/something"
        def expectedNewApi = "${project.buildDir}/swagger.json"
        def expectedGroupId = "${project.group}"
        def expectedArtifactId = "${project.name}"
        def expectedOutputFilePath = "${project.buildDir}/swagger-brake/"
        def expectedOutputFormats = ["JSON"]
        def expectedOldApi = "${project.buildDir}/swagger-old.json"

        when:
        project.pluginManager.apply(SwaggerBrakePlugin)
        project.extensions.configure(SwaggerBrakeExtension, new ClosureBackedAction<SwaggerBrakeExtension>({
            mavenRepoUrl = expectedMavenRepoUrl
            newApi = expectedNewApi
            groupId = expectedGroupId
            artifactId = expectedArtifactId
            outputFilePath = expectedOutputFilePath
            outputFormats = expectedOutputFormats
            oldApi = expectedOldApi
        }))

        then:
        def extension = project.extensions.findByName("swaggerBrake")
        assert extension != null
        assert extension.mavenRepoUrl.get() == expectedMavenRepoUrl
        assert extension.newApi.get() == expectedNewApi
        assert extension.groupId.get() == expectedGroupId
        assert extension.artifactId.get() == expectedArtifactId
        assert extension.outputFilePath.get() == expectedOutputFilePath
        assert extension.outputFormats.get() == expectedOutputFormats
        assert extension.oldApi.get() == expectedOldApi
    }
}
