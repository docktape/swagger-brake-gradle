package com.docktape.swagger.brake.gradle.task

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import spock.lang.Specification

import static java.util.Collections.emptyList
import static java.util.Collections.emptySet

class CheckBreakingChangesTaskParameterFactoryTest extends Specification {
    private Project project = Mock(Project)
    private PluginContainer pluginContainer = Mock(PluginContainer)
    private Property<String> newApi = Mock(Property)
    private Property<String> oldApi = Mock(Property)
    private Property<String> mavenRepoUrl = Mock(Property)
    private Property<String> mavenSnapshotRepoUrl = Mock(Property)
    private Property<String> artifactId = Mock(Property)
    private Property<String> groupId = Mock(Property)
    private Property<String> currentVersion = Mock(Property)
    private Property<String> artifactPackaging = Mock(Property)
    private Property<String> outputFilePath = Mock(Property)
    private ListProperty<String> outputFormats = Mock(ListProperty)
    private Property<String> mavenRepoUsername = Mock(Property)
    private Property<String> mavenRepoPassword = Mock(Property)
    private Property<Boolean> deprecatedApiDeletionAllowed = Mock(Property)
    private Property<String> betaApiExtensionName = Mock(Property)
    private Property<String> apiFilename = Mock(Property)
    private ListProperty<String> excludedPaths = Mock(ListProperty)
    private ListProperty<String> ignoredBreakingChangeRules = Mock(ListProperty)
    private Property<Boolean> strictValidation = Mock(Property)
    private Property<Integer> maxLogSerializationDepth = Mock(Property)

    private CheckBreakingChangesTaskParameterFactory underTest = new CheckBreakingChangesTaskParameterFactory()

    def "create works when everything is set in properties"() {
        given:
        def newApiVal = "newApi"
        def oldApiVal = "oldApi"
        def mavenRepoUrlVal = "mavenRepoUrl"
        def mavenSnapshotRepoUrlVal = "mavenSnapshotRepoUrl"
        def mavenRepoUsernameVal = "username"
        def mavenRepoPasswordVal = "password"
        def artifactIdVal = "artifactId"
        def groupIdVal = "groupId"
        def currentVersionVal = "1.0.0"
        def artifactPackagingVal = "jar"
        def outputFilePathVal = "outputFilePath"
        def outputFormatsVal = ["HTML", "JSON"]
        def deprecatedApiDeletionAllowedVal = true
        def betaApiExtensionNameVal = "betaApiExtensionName"
        def apiFilenameVal = "apiFilename"
        def excludedPathsVal = Lists.newArrayList("/path")
        def ignoredBreakingChangeRulesVal = Lists.newArrayList("R002")
        def strictValidationVal = true
        def maxLogSerializationDepthVal = 3

        and:
        newApi.get() >> newApiVal
        oldApi.getOrElse(null) >> oldApiVal
        mavenRepoUrl.getOrElse(null) >> mavenRepoUrlVal
        mavenSnapshotRepoUrl.getOrElse(null) >> mavenSnapshotRepoUrlVal
        artifactId.getOrElse(null) >> artifactIdVal
        groupId.getOrElse(null) >> groupIdVal
        currentVersion.getOrElse(null) >> currentVersionVal
        artifactPackaging.getOrElse(null) >> artifactPackagingVal
        outputFilePath.getOrElse(null) >> outputFilePathVal
        outputFormats.getOrElse([]) >> outputFormatsVal
        mavenRepoUsername.getOrElse(null) >> mavenRepoUsernameVal
        mavenRepoPassword.getOrElse(null) >> mavenRepoPasswordVal
        deprecatedApiDeletionAllowed.getOrElse(false) >> deprecatedApiDeletionAllowedVal
        betaApiExtensionName.getOrElse(null) >> betaApiExtensionNameVal
        apiFilename.getOrElse(null) >> apiFilenameVal
        excludedPaths.getOrElse(emptyList()) >> excludedPathsVal
        ignoredBreakingChangeRules.getOrElse(emptyList()) >> ignoredBreakingChangeRulesVal
        strictValidation.getOrElse(null) >> strictValidationVal
        maxLogSerializationDepth.getOrElse(null) >> maxLogSerializationDepthVal
        project.getPlugins() >> pluginContainer
        pluginContainer.hasPlugin("war") >> false


        when:
        def result = CheckBreakingChangesTaskParameterFactory.create(
                project,
                newApi,
                oldApi,
                mavenRepoUrl,
                mavenSnapshotRepoUrl,
                artifactId,
                groupId,
                currentVersion,
                artifactPackaging,
                outputFilePath,
                outputFormats,
                mavenRepoUsername,
                mavenRepoPassword,
                deprecatedApiDeletionAllowed,
                betaApiExtensionName,
                apiFilename,
                excludedPaths,
                ignoredBreakingChangeRules,
                strictValidation,
                maxLogSerializationDepth
        )
        
        then:
        assert result.newApi == newApiVal
        assert result.oldApi == oldApiVal
        assert result.mavenRepoUrl == mavenRepoUrlVal
        assert result.mavenSnapshotRepoUrl == mavenSnapshotRepoUrlVal
        assert result.artifactId == artifactIdVal
        assert result.groupId == groupIdVal
        assert result.currentVersion == currentVersionVal
        assert result.artifactPackaging == artifactPackagingVal
        assert result.outputFilePath == outputFilePathVal
        assert result.outputFormats == outputFormatsVal
        assert result.mavenRepoUsername == mavenRepoUsernameVal
        assert result.mavenRepoPassword == mavenRepoPasswordVal
        assert result.deprecatedApiDeletionAllowed == deprecatedApiDeletionAllowedVal
        assert result.betaApiExtensionName == betaApiExtensionNameVal
        assert result.apiFilename == apiFilenameVal
        assert result.excludedPaths == excludedPathsVal
        assert result.ignoredBreakingChangeRules == ignoredBreakingChangeRulesVal
        assert result.strictValidation == strictValidationVal
        assert result.maxLogSerializationDepth == maxLogSerializationDepthVal
    }


    def "create works when the project is used for properties and defaults are applied"() {
        given:
        def newApiVal = "newApi"
        def mavenRepoUrlVal = "mavenRepoUrl"
        def mavenSnapshotRepoUrlVal = "mavenSnapshotRepoUrl"
        def artifactIdVal = "artifactId"
        def groupIdVal = "groupId"
        def currentVersionVal = "1.0.0"
        def outputFilePathVal = "outputFilePath"
        def buildDir = new File(outputFilePathVal)
        def deprecatedApiDeletionAllowedVal = false

        and:
        newApi.get() >> newApiVal
        mavenRepoUrl.getOrElse(null) >> mavenRepoUrlVal
        mavenSnapshotRepoUrl.getOrElse(null) >> mavenSnapshotRepoUrlVal
        artifactId.getOrElse(null) >> null
        groupId.getOrElse(null) >> null
        currentVersion.getOrElse(null) >> null
        outputFilePath.getOrElse(null) >> null
        deprecatedApiDeletionAllowed.getOrElse(false) >> deprecatedApiDeletionAllowedVal
        excludedPaths.getOrElse(emptyList()) >> emptyList()
        ignoredBreakingChangeRules.getOrElse(emptyList()) >> emptyList()
        strictValidation.getOrElse(null) >> null
        maxLogSerializationDepth.getOrElse(null) >> null
        project.getBuildDir() >> buildDir
        project.getGroup() >> groupIdVal
        project.getName() >> artifactIdVal
        project.getVersion() >> currentVersionVal
        project.getPlugins() >> pluginContainer
        pluginContainer.hasPlugin("war") >> false


        when:
        def result = CheckBreakingChangesTaskParameterFactory.create(
                project,
                newApi,
                oldApi,
                mavenRepoUrl,
                mavenSnapshotRepoUrl,
                artifactId,
                groupId,
                currentVersion,
                artifactPackaging,
                outputFilePath,
                outputFormats,
                mavenRepoUsername,
                mavenRepoPassword,
                deprecatedApiDeletionAllowed,
                betaApiExtensionName,
                apiFilename,
                excludedPaths,
                ignoredBreakingChangeRules,
                strictValidation,
                maxLogSerializationDepth
        )

        then:
        assert result.newApi == newApiVal
        assert result.mavenRepoUrl == mavenRepoUrlVal
        assert result.mavenSnapshotRepoUrl == mavenSnapshotRepoUrlVal
        assert result.artifactId == artifactIdVal
        assert result.groupId == groupIdVal
        assert result.currentVersion == currentVersionVal
        assert result.artifactPackaging == "jar"
        assert result.outputFilePath == "${outputFilePathVal}/swagger-brake/"
        assert result.outputFormats == ["HTML"]
        assert result.deprecatedApiDeletionAllowed == deprecatedApiDeletionAllowedVal
        assert result.excludedPaths == emptyList()
        assert result.ignoredBreakingChangeRules == emptyList()
    }
}
