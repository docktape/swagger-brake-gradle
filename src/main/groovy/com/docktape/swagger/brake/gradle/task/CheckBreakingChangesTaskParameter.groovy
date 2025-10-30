package com.docktape.swagger.brake.gradle.task

class CheckBreakingChangesTaskParameter {
    String newApi
    String oldApi
    String mavenRepoUrl
    String mavenSnapshotRepoUrl
    String artifactId
    String groupId
    String currentVersion
    String artifactPackaging
    String outputFilePath
    List<String> outputFormats
    String mavenRepoUsername
    String mavenRepoPassword
    Boolean deprecatedApiDeletionAllowed
    String betaApiExtensionName
    String apiFilename
    List<String> excludedPaths
    List<String> ignoredBreakingChangeRules


    @Override
    public String toString() {
        return "CheckBreakingChangesTaskParameter{" +
                "newApi='" + newApi + '\'' +
                ", oldApi='" + oldApi + '\'' +
                ", mavenRepoUrl='" + mavenRepoUrl + '\'' +
                ", mavenSnapshotRepoUrl='" + mavenSnapshotRepoUrl + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", currentVersion='" + currentVersion + '\'' +
                ", artifactPackaging='" + artifactPackaging + '\'' +
                ", outputFilePath='" + outputFilePath + '\'' +
                ", outputFormats=" + outputFormats +
                ", mavenRepoUsername='" + mavenRepoUsername + '\'' +
                ", mavenRepoPassword='" + mavenRepoPassword + '\'' +
                ", deprecatedApiDeletionAllowed=" + deprecatedApiDeletionAllowed +
                ", betaApiExtensionName='" + betaApiExtensionName + '\'' +
                ", apiFilename='" + apiFilename + '\'' +
                ", excludedPaths=" + excludedPaths + '\'' +
                ", ignoredBreakingChangeRules=" + ignoredBreakingChangeRules + '\'' +
                '}';
    }
}
