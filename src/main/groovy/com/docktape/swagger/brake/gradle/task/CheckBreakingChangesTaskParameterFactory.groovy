package com.docktape.swagger.brake.gradle.task

import com.docktape.swagger.brake.runner.ArtifactPackaging
import com.docktape.swagger.brake.runner.OutputFormat
import org.apache.commons.collections4.CollectionUtils
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

import static java.util.Collections.emptyList
import static org.apache.commons.lang3.StringUtils.isBlank

class CheckBreakingChangesTaskParameterFactory {
    private static final Logger logger = Logging.getLogger(CheckBreakingChangesTaskParameterFactory)

    static CheckBreakingChangesTaskParameter create(
            Project project,
            Property<String> newApi,
            Property<String> oldApi,
            Property<String> mavenRepoUrl,
            Property<String> mavenSnapshotRepoUrl,
            Property<String> artifactId,
            Property<String> groupId,
            Property<String> currentVersion,
            Property<String> artifactPackaging,
            Property<String> outputFilePath,
            ListProperty<String> outputFormats,
            Property<String> mavenRepoUsername,
            Property<String> mavenRepoPassword,
            Property<Boolean> deprecatedApiDeletionAllowed,
            Property<String> betaApiExtensionName,
            Property<String> apiFilename,
            ListProperty<String> excludedPaths,
            ListProperty<String> ignoredBreakingChangeRules,
            Property<Boolean> strictValidation,
            Property<Integer> maxLogSerializationDepth
    ) {
        def parameter = new CheckBreakingChangesTaskParameter()
        parameter.newApi = newApi.get()
        parameter.oldApi = oldApi.getOrElse(null)
        parameter.mavenRepoUrl = mavenRepoUrl.getOrElse(null)
        parameter.mavenSnapshotRepoUrl = mavenSnapshotRepoUrl.getOrElse(null)
        parameter.artifactId = getArtifactIdParam(project, artifactId)
        parameter.groupId = getGroupIdParam(project, groupId)
        parameter.currentVersion = getCurrentVersionParam(project, currentVersion)
        parameter.artifactPackaging = getArtifactPackaging(project, artifactPackaging);
        parameter.outputFilePath = getOutputFilePathParam(project, outputFilePath)
        parameter.outputFormats = getOutputFormatParam(outputFormats)
        parameter.mavenRepoUsername = mavenRepoUsername.getOrElse(null)
        parameter.mavenRepoPassword = mavenRepoPassword.getOrElse(null)
        parameter.deprecatedApiDeletionAllowed = deprecatedApiDeletionAllowed.getOrElse(false)
        parameter.betaApiExtensionName = betaApiExtensionName.getOrElse(null)
        parameter.apiFilename = apiFilename.getOrElse(null)
        parameter.excludedPaths = excludedPaths.getOrElse(emptyList())
        parameter.ignoredBreakingChangeRules = ignoredBreakingChangeRules.getOrElse(emptyList())
        parameter.strictValidation = strictValidation.getOrElse(null)
        parameter.maxLogSerializationDepth = maxLogSerializationDepth.getOrElse(null)
        return parameter
    }

    static String getArtifactPackaging(Project project, Property<String> artifactPackagingProp) {
        def artifactPackaging = artifactPackagingProp.getOrElse(null);
        if (isBlank(artifactPackaging)) {
            def hasWarPlugin = project.getPlugins().hasPlugin("war")
            if (hasWarPlugin) {
                artifactPackaging = ArtifactPackaging.WAR.getPackaging()
            } else {
                artifactPackaging = ArtifactPackaging.JAR.getPackaging()
            }
            return artifactPackaging;
        } else {
            return artifactPackaging
        }
    }

    static String getGroupIdParam(Project project, Property<String> groupIdProp) {
        def groupId = groupIdProp.getOrElse(null)
        if (isBlank(groupId)) {
            def pGroupId = project.getGroup().toString()
            if (isBlank(pGroupId)) {
                logger.warn("Default groupId based on the project group cannot be used for Swagger Brake. Consider setting it manually.")
                return null
            } else {
                return pGroupId
            }
        } else {
            return groupId
        }
    }

    static String getArtifactIdParam(Project project, Property<String> artifactIdProp) {
        def artifactId = artifactIdProp.getOrElse(null)
        if (isBlank(artifactId)) {
            def pArtifactId = project.getName().toString()
            if (isBlank(pArtifactId)) {
                logger.warn("Default artifactId based on the project name cannot be used for Swagger Brake. Consider setting it manually.")
                return null
            } else {
                return pArtifactId
            }
        } else {
            return artifactId
        }
    }

    static String getCurrentVersionParam(Project project, Property<String> currentVersionProp) {
        def currentVersion = currentVersionProp.getOrElse(null)
        if (isBlank(currentVersion)) {
            def pVersion = project.getVersion().toString()
            if (isBlank(pVersion) || pVersion == "unspecified") {
                logger.warn("Default currentVersion based on the project version cannot be used for Swagger Brake. Consider setting it manually.")
                return null
            } else {
                return pVersion
            }
        } else {
            return currentVersion
        }
    }

    static String getOutputFilePathParam(Project project, Property<String> outputFilePathProp) {
        def outputFilePath = outputFilePathProp.getOrElse(null)
        if (isBlank(outputFilePath)) {
            def pBuildDir = project.getBuildDir().toString()
            if (isBlank(pBuildDir)) {
                logger.warn("Default outputFilePath based on the project buildDir cannot be used for Swagger Brake. Consider setting it manually.")
                return null
            } else {
                def pOutputFilePath = "${pBuildDir}/swagger-brake/".toString()
                return pOutputFilePath
            }
        } else {
            return outputFilePath
        }
    }

    static List<String> getOutputFormatParam(ListProperty<String> outputFormatsProp) {
        def outputFormats = outputFormatsProp.getOrElse([])
        if (CollectionUtils.isEmpty(outputFormats)) {
            return [OutputFormat.HTML.name()]
        } else {
            return outputFormats
        }
    }
}
