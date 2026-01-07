package com.docktape.swagger.brake.gradle.task

import com.docktape.swagger.brake.gradle.task.starter.StarterFactory
import com.docktape.swagger.brake.runner.OptionsValidator
import org.gradle.api.DefaultTask
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

class CheckBreakingChangesTask extends DefaultTask {
    @Input
    final Property<String> newApi = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final Property<String> oldApi = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final Property<String> mavenRepoUrl = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final Property<String> mavenSnapshotRepoUrl = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final Property<String> artifactId = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final Property<String> groupId = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final Property<String> currentVersion = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final Property<String> artifactPackaging = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final Property<String> outputFilePath = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final ListProperty<String> outputFormats = getProject().getObjects().listProperty(String.class)
    @Input
    @Optional
    final Property<String> mavenRepoUsername = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final Property<String> mavenRepoPassword = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final Property<Boolean> deprecatedApiDeletionAllowed = getProject().getObjects().property(Boolean.class)
    @Input
    @Optional
    final Property<String> betaApiExtensionName = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final Property<String> apiFilename = getProject().getObjects().property(String.class)
    @Input
    @Optional
    final ListProperty<String> excludedPaths = getProject().getObjects().listProperty(String.class)
    @Input
    @Optional
    final ListProperty<String> ignoredBreakingChangeRules = getProject().getObjects().listProperty(String.class)
    @Input
    @Optional
    final Property<Boolean> strictValidation = getProject().getObjects().property(Boolean.class)
    @Input
    @Optional
    final Property<Integer> maxLogSerializationDepth = getProject().getObjects().property(Integer.class)

    @Input
    @Optional
    final Property<Boolean> testModeEnabled = getProject().getObjects().property(Boolean.class)

    private final OptionsValidator optionsValidator = new OptionsValidator()

    @TaskAction
    void performCheck() {
        def parameter = CheckBreakingChangesTaskParameterFactory.create(
                getProject(),
                this.newApi,
                this.oldApi,
                this.mavenRepoUrl,
                this.mavenSnapshotRepoUrl,
                this.artifactId,
                this.groupId,
                this.currentVersion,
                this.artifactPackaging,
                this.outputFilePath,
                this.outputFormats,
                this.mavenRepoUsername,
                this.mavenRepoPassword,
                this.deprecatedApiDeletionAllowed,
                this.betaApiExtensionName,
                this.apiFilename,
                this.excludedPaths,
                this.ignoredBreakingChangeRules,
                this.strictValidation,
                this.maxLogSerializationDepth
        )
        logger.info("The following parameters are set for the task {}", parameter)
        def options = OptionsFactory.create(parameter)
        optionsValidator.validate(options)
        createExecutor().execute(options)
    }

    CheckBreakingChangesTaskExecutor createExecutor() {
        def factory = new StarterFactory(testModeEnabled.getOrElse(false))
        return new CheckBreakingChangesTaskExecutor(factory)
    }
}
