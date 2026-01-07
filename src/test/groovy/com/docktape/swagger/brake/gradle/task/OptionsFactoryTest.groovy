package com.docktape.swagger.brake.gradle.task

import com.google.common.collect.Lists
import com.docktape.swagger.brake.runner.ArtifactPackaging
import spock.lang.Specification

class OptionsFactoryTest extends Specification {
    private OptionsFactory underTest = new OptionsFactory()

    def "Attributes are correctly copied from the parameter object"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.mavenRepoUrl = "repo"
        parameter.newApi = "newApi"
        parameter.artifactId = "artifactId"
        parameter.groupId = "groupId"
        parameter.outputFilePath = "outputFilePath"
        parameter.outputFormats = ["HTML"]
        parameter.mavenRepoUsername = "username"
        parameter.mavenRepoPassword = "password"
        parameter.deprecatedApiDeletionAllowed = true
        parameter.excludedPaths = Lists.newArrayList("/path", "/test")
        parameter.ignoredBreakingChangeRules = Lists.newArrayList("R002", "R001")

        when:
        def result = underTest.create(parameter)

        then:
        assert result.mavenRepoUrl == parameter.mavenRepoUrl
        assert result.newApiPath == parameter.newApi
        assert result.artifactId == parameter.artifactId
        assert result.groupId == parameter.groupId
        assert result.outputFilePath == parameter.outputFilePath
        assert result.mavenRepoUsername == parameter.mavenRepoUsername
        assert result.mavenRepoPassword == parameter.mavenRepoPassword
        assert result.outputFormats[0].name() == 'HTML'
        assert result.deprecatedApiDeletionAllowed == parameter.deprecatedApiDeletionAllowed
        assert result.excludedPaths.sort() == parameter.excludedPaths.sort()
        assert result.ignoredBreakingChangeRules.sort() == parameter.ignoredBreakingChangeRules.sort()
        assert result.strictValidation == null
        assert result.maxLogSerializationDepth == null
    }

    def "OutputFormat is accepted when it's a lowercase value"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.mavenRepoUrl = "repo"
        parameter.newApi = "newApi"
        parameter.artifactId = "artifactId"
        parameter.groupId = "groupId"
        parameter.outputFilePath = "outputFilePath"
        parameter.outputFormats = ["html"]

        when:
        def result = underTest.create(parameter)

        then:
        assert result.mavenRepoUrl == parameter.mavenRepoUrl
        assert result.newApiPath == parameter.newApi
        assert result.artifactId == parameter.artifactId
        assert result.groupId == parameter.groupId
        assert result.outputFilePath == parameter.outputFilePath
        assert result.outputFormats[0].name() == 'HTML'
    }

    def "ArtifactPackaging is accepted when it's a lowercase JAR value"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.mavenRepoUrl = "repo"
        parameter.newApi = "newApi"
        parameter.artifactId = "artifactId"
        parameter.groupId = "groupId"
        parameter.artifactPackaging = "jar"

        when:
        def result = underTest.create(parameter)

        then:
        assert result.mavenRepoUrl == parameter.mavenRepoUrl
        assert result.newApiPath == parameter.newApi
        assert result.artifactId == parameter.artifactId
        assert result.groupId == parameter.groupId
        assert result.artifactPackaging == ArtifactPackaging.JAR
    }

    def "ArtifactPackaging is accepted when it's a uppercase JAR value"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.mavenRepoUrl = "repo"
        parameter.newApi = "newApi"
        parameter.artifactId = "artifactId"
        parameter.groupId = "groupId"
        parameter.artifactPackaging = "JAR"

        when:
        def result = underTest.create(parameter)

        then:
        assert result.mavenRepoUrl == parameter.mavenRepoUrl
        assert result.newApiPath == parameter.newApi
        assert result.artifactId == parameter.artifactId
        assert result.groupId == parameter.groupId
        assert result.artifactPackaging == ArtifactPackaging.JAR
    }

    def "ArtifactPackaging is accepted when it's a uppercase non-trimmed JAR value"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.mavenRepoUrl = "repo"
        parameter.newApi = "newApi"
        parameter.artifactId = "artifactId"
        parameter.groupId = "groupId"
        parameter.artifactPackaging = "     JAR   "

        when:
        def result = underTest.create(parameter)

        then:
        assert result.mavenRepoUrl == parameter.mavenRepoUrl
        assert result.newApiPath == parameter.newApi
        assert result.artifactId == parameter.artifactId
        assert result.groupId == parameter.groupId
        assert result.artifactPackaging == ArtifactPackaging.JAR
    }

    def "ArtifactPackaging is set to JAR when none provided"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.mavenRepoUrl = "repo"
        parameter.newApi = "newApi"
        parameter.artifactId = "artifactId"
        parameter.groupId = "groupId"

        when:
        def result = underTest.create(parameter)

        then:
        assert result.mavenRepoUrl == parameter.mavenRepoUrl
        assert result.newApiPath == parameter.newApi
        assert result.artifactId == parameter.artifactId
        assert result.groupId == parameter.groupId
        assert result.artifactPackaging == ArtifactPackaging.JAR
    }

    def "ArtifactPackaging is accepted when it's a lowercase WAR value"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.mavenRepoUrl = "repo"
        parameter.newApi = "newApi"
        parameter.artifactId = "artifactId"
        parameter.groupId = "groupId"
        parameter.artifactPackaging = "war"

        when:
        def result = underTest.create(parameter)

        then:
        assert result.mavenRepoUrl == parameter.mavenRepoUrl
        assert result.newApiPath == parameter.newApi
        assert result.artifactId == parameter.artifactId
        assert result.groupId == parameter.groupId
        assert result.artifactPackaging == ArtifactPackaging.WAR
    }

    def "ArtifactPackaging is accepted when it's a uppercase WAR value"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.mavenRepoUrl = "repo"
        parameter.newApi = "newApi"
        parameter.artifactId = "artifactId"
        parameter.groupId = "groupId"
        parameter.artifactPackaging = "WAR"

        when:
        def result = underTest.create(parameter)

        then:
        assert result.mavenRepoUrl == parameter.mavenRepoUrl
        assert result.newApiPath == parameter.newApi
        assert result.artifactId == parameter.artifactId
        assert result.groupId == parameter.groupId
        assert result.artifactPackaging == ArtifactPackaging.WAR
    }

    def "create throws exception when unavailable output format"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.mavenRepoUrl = "repo"
        parameter.newApi = "newApi"
        parameter.artifactId = "artifactId"
        parameter.groupId = "groupId"
        parameter.outputFilePath = "outputFilePath"
        parameter.outputFormats = ["svg"]

        when:
        underTest.create(parameter)

        then:
        thrown(IllegalArgumentException)
    }

    def "create copies apiFilename over properly when value is set"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.apiFilename = "something"

        when:
        def result = underTest.create(parameter)

        then:
        assert result.apiFilename == parameter.apiFilename
    }

    def "create sets null apiFilename when value is blank"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.apiFilename = ""

        when:
        def result = underTest.create(parameter)

        then:
        assert result.apiFilename == null
    }

    def "create copies betaApiExtensionName over properly when value is set"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.betaApiExtensionName = "something"

        when:
        def result = underTest.create(parameter)

        then:
        assert result.betaApiExtensionName == parameter.betaApiExtensionName
    }

    def "create sets null betaApiExtensionName when value is blank"() {
        given:
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.betaApiExtensionName = ""

        when:
        def result = underTest.create(parameter)

        then:
        assert result.betaApiExtensionName == null
    }
}
