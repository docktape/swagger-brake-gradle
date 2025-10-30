package com.docktape.swagger.brake.gradle.task


import com.docktape.swagger.brake.gradle.task.starter.StarterFactory
import com.docktape.swagger.brake.runner.Options
import com.docktape.swagger.brake.runner.exception.LatestArtifactDownloadException
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty

class CheckBreakingChangesTaskExecutor {
    private static final Logger logger = Logging.getLogger(CheckBreakingChangesTaskExecutor)

    private final StarterFactory starterFactory

    CheckBreakingChangesTaskExecutor(StarterFactory starterFactory) {
        this.starterFactory = starterFactory
    }

    void execute(Options options) {
        try {
            def bcs = starterFactory.create().start(options)
            if (isNotEmpty(bcs)) {
                throw new BreakingChangeDetectedException("Breaking change has been found. See report further details at ${options.outputFilePath}")
            }
        } catch (LatestArtifactDownloadException e) {
            logger.quiet("Latest version of the artifact could not be retrieved from {} with {}:{}", options.mavenRepoUrl, options.groupId, options.artifactId)
            logger.quiet("Assuming this is the first version of the artifact, skipping check for breaking changes", e)
        }
    }
}
