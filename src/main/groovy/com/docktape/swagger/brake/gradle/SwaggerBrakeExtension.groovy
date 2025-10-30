package com.docktape.swagger.brake.gradle


import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

class SwaggerBrakeExtension {
    private static final Logger logger = Logging.getLogger(SwaggerBrakeExtension)

    final Property<String> newApi
    final Property<String> oldApi
    final Property<String> mavenRepoUrl
    final Property<String> mavenSnapshotRepoUrl
    final Property<String> groupId
    final Property<String> artifactId
    final Property<String> currentVersion
    final Property<String> artifactPackaging
    final Property<String> outputFilePath
    final ListProperty<String> outputFormats
    final Property<String> mavenRepoUsername
    final Property<String> mavenRepoPassword
    final Property<Boolean> deprecatedApiDeletionAllowed
    final Property<String> betaApiExtensionName
    final Property<String> apiFilename
    final ListProperty<String> excludedPaths
    final ListProperty<String> ignoredBreakingChangeRules

    final Property<Boolean> testModeEnabled

    SwaggerBrakeExtension(Project project) {
        this.newApi = project.getObjects().property(String.class)
        this.oldApi = project.getObjects().property(String.class)
        this.mavenRepoUrl = project.getObjects().property(String.class)
        this.mavenSnapshotRepoUrl = project.getObjects().property(String.class)
        this.groupId = project.getObjects().property(String.class)
        this.artifactId = project.getObjects().property(String.class)
        this.currentVersion = project.getObjects().property(String.class)
        this.artifactPackaging = project.getObjects().property(String.class)
        this.outputFilePath = project.getObjects().property(String.class)
        this.outputFormats = project.getObjects().listProperty(String.class)
        this.mavenRepoUsername = project.getObjects().property(String.class)
        this.mavenRepoPassword = project.getObjects().property(String.class)
        this.deprecatedApiDeletionAllowed = project.getObjects().property(Boolean.class)
        this.betaApiExtensionName = project.getObjects().property(String.class)
        this.apiFilename = project.getObjects().property(String.class)
        this.excludedPaths = project.getObjects().listProperty(String.class)
        this.ignoredBreakingChangeRules = project.getObjects().listProperty(String.class)
        this.testModeEnabled = project.getObjects().property(Boolean.class)
    }
}
