package com.docktape.swagger.brake.gradle.task

import com.docktape.swagger.brake.gradle.task.starter.StarterFactory
import com.docktape.swagger.brake.runner.OptionsValidator
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

@CacheableTask
class CheckBreakingChangesTask extends DefaultTask {
    @Internal
    final Property<String> newApi = project.objects.property(String)
    @Internal
    final Property<String> oldApi = project.objects.property(String)
    @Input
    @Optional
    final Property<String> mavenRepoUrl = project.objects.property(String)
    @Input
    @Optional
    final Property<String> mavenSnapshotRepoUrl = project.objects.property(String)
    @Input
    @Optional
    final Property<String> artifactId = project.objects.property(String)
    @Input
    @Optional
    final Property<String> groupId = project.objects.property(String)
    @Input
    @Optional
    final Property<String> currentVersion = project.objects.property(String)
    @Input
    @Optional
    final Property<String> artifactPackaging = project.objects.property(String)
    @Internal
    final Property<String> outputFilePath = project.objects.property(String)
    @Input
    @Optional
    final ListProperty<String> outputFormats = project.objects.listProperty(String)
    @Input
    @Optional
    final Property<String> mavenRepoUsername = project.objects.property(String)
    @Input
    @Optional
    final Property<String> mavenRepoPassword = project.objects.property(String)
    @Input
    @Optional
    final Property<Boolean> deprecatedApiDeletionAllowed = project.objects.property(Boolean)
    @Input
    @Optional
    final Property<String> betaApiExtensionName = project.objects.property(String)
    @Input
    @Optional
    final Property<String> apiFilename = project.objects.property(String)
    @Input
    @Optional
    final ListProperty<String> excludedPaths = project.objects.listProperty(String)
    @Input
    @Optional
    final ListProperty<String> ignoredBreakingChangeRules = project.objects.listProperty(String)
    @Input
    @Optional
    final Property<Boolean> strictValidation = project.objects.property(Boolean)
    @Input
    @Optional
    final Property<Integer> maxLogSerializationDepth = project.objects.property(Integer)

    @Internal
    final Property<Boolean> testModeEnabled = project.objects.property(Boolean)

    @OutputDirectory
    final DirectoryProperty outputDir = project.objects.directoryProperty()
        .convention(
            project.layout.dir(
                outputFilePath.map { path -> project.file(path) }
            ).orElse(project.layout.buildDirectory.dir("swagger-brake"))
        )

    @InputFile
    @PathSensitive(PathSensitivity.NONE)
    Provider<RegularFile> getNewApiFile() {
        return project.layout.projectDirectory.file(newApi)
    }

    @InputFile
    @PathSensitive(PathSensitivity.NONE)
    @Optional
    Provider<RegularFile> getOldApiFile() {
        return project.layout.projectDirectory.file(oldApi)
    }

    private final OptionsValidator optionsValidator = new OptionsValidator()

    @TaskAction
    void performCheck() {
        def resolvedOutputPath = project.objects.property(String)
        resolvedOutputPath.set(outputDir.get().asFile.absolutePath)
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
                resolvedOutputPath,
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
