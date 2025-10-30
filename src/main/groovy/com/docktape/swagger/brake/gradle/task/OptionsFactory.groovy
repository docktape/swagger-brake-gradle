package com.docktape.swagger.brake.gradle.task

import com.google.common.collect.ImmutableSet
import com.google.common.collect.Sets
import com.docktape.swagger.brake.runner.ArtifactPackaging
import com.docktape.swagger.brake.runner.Options
import com.docktape.swagger.brake.runner.OutputFormat
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils

import static java.util.Collections.emptyList
import static java.util.Collections.emptySet

class OptionsFactory {
    static Options create(CheckBreakingChangesTaskParameter parameter) {
        Options options = new Options()
        options.setNewApiPath(parameter.newApi)
        options.setOldApiPath(parameter.oldApi)
        options.setMavenRepoUrl(parameter.mavenRepoUrl)
        options.setMavenSnapshotRepoUrl(parameter.mavenSnapshotRepoUrl)
        options.setArtifactId(parameter.artifactId)
        options.setGroupId(parameter.groupId)
        options.setCurrentArtifactVersion(parameter.currentVersion)
        options.setArtifactPackaging(resolveArtifactPackaging(parameter))
        options.setOutputFilePath(parameter.outputFilePath)
        options.setOutputFormats(resolveOutputFormat(parameter))
        options.setMavenRepoUsername(parameter.mavenRepoUsername)
        options.setMavenRepoPassword(parameter.mavenRepoPassword)
        options.setDeprecatedApiDeletionAllowed(parameter.deprecatedApiDeletionAllowed)
        options.setBetaApiExtensionName(StringUtils.defaultIfBlank(parameter.betaApiExtensionName, null))
        options.setApiFilename(StringUtils.defaultIfBlank(parameter.apiFilename, null))
        options.setExcludedPaths(Sets.newHashSet(Optional.ofNullable(parameter.excludedPaths).orElse(emptyList())))
        options.setIgnoredBreakingChangeRules(Sets.newHashSet(Optional.ofNullable(parameter.ignoredBreakingChangeRules).orElse(emptyList())))
        return options
    }

    private static Set<OutputFormat> resolveOutputFormat(CheckBreakingChangesTaskParameter parameter) {
        def formats = parameter.outputFormats
        if (CollectionUtils.isEmpty(formats)) {
            return emptySet()
        }
        return ImmutableSet.copyOf(formats.collect { OutputFormat.valueOf(it.toUpperCase()) })
    }

    private static ArtifactPackaging resolveArtifactPackaging(CheckBreakingChangesTaskParameter parameter) {
        String artifactPackaging = parameter.getArtifactPackaging();
        if (StringUtils.isBlank(artifactPackaging)) {
            return ArtifactPackaging.JAR;
        }
        return ArtifactPackaging.forPackaging(artifactPackaging.trim().toLowerCase());
    }
}
