package com.docktape.swagger.brake.gradle


import com.docktape.swagger.brake.gradle.task.CheckBreakingChangesTask
import org.gradle.api.Action
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class SwaggerBrakePlugin implements Plugin<Project>  {
    @Override
    void apply(Project project) {
        def extension = project.getExtensions().create("swaggerBrake", SwaggerBrakeExtension.class, project)

        def checkBreakingChangesTask = project.getTasks().register("checkBreakingChanges", CheckBreakingChangesTask.class, new Action<CheckBreakingChangesTask>() {
            @Override
            void execute(CheckBreakingChangesTask checkBreakingChangesTask) {
                checkBreakingChangesTask.getMavenRepoUrl().set(extension.mavenRepoUrl)
                checkBreakingChangesTask.getMavenSnapshotRepoUrl().set(extension.mavenSnapshotRepoUrl)
                checkBreakingChangesTask.getNewApi().set(extension.newApi)
                checkBreakingChangesTask.getOldApi().set(extension.oldApi)
                checkBreakingChangesTask.getArtifactId().set(extension.artifactId)
                checkBreakingChangesTask.getGroupId().set(extension.groupId)
                checkBreakingChangesTask.getCurrentVersion().set(extension.currentVersion)
                checkBreakingChangesTask.getArtifactPackaging().set(extension.artifactPackaging)
                checkBreakingChangesTask.getOutputFilePath().set(extension.outputFilePath)
                checkBreakingChangesTask.getOutputFormats().set(extension.outputFormats)
                checkBreakingChangesTask.getMavenRepoUsername().set(extension.mavenRepoUsername)
                checkBreakingChangesTask.getMavenRepoPassword().set(extension.mavenRepoPassword)
                checkBreakingChangesTask.getDeprecatedApiDeletionAllowed().set(extension.deprecatedApiDeletionAllowed)
                checkBreakingChangesTask.getBetaApiExtensionName().set(extension.betaApiExtensionName)
                checkBreakingChangesTask.getApiFilename().set(extension.apiFilename)
                checkBreakingChangesTask.getExcludedPaths().set(extension.excludedPaths)
                checkBreakingChangesTask.getIgnoredBreakingChangeRules().set(extension.ignoredBreakingChangeRules)
                checkBreakingChangesTask.getStrictValidation().set(extension.strictValidation)
                checkBreakingChangesTask.getMaxLogSerializationDepth().set(extension.maxLogSerializationDepth)
                checkBreakingChangesTask.getTestModeEnabled().set(extension.testModeEnabled)
            }
        })

        def checkTask = project.tasks.findByName("check")
        if (checkTask != null) {
            checkTask.dependsOn(checkBreakingChangesTask)
        } else {
            throw new GradleException("Plugin cannot be applied when no check task is present")
        }
    }
}
