package com.docktape.swagger.brake.gradle.task.starter

class StarterFactory {
    private final Class<? extends Starter> starterClass

    StarterFactory(boolean testModeEnabled) {
        this.starterClass = getStarterClass(testModeEnabled)
    }

    Class<? extends Starter> getStarterClass(boolean testModeEnabled) {
        return testModeEnabled ? NoopStarter : SwaggerBrakeStarter
    }

    Starter create() {
        return starterClass.newInstance()
    }
}
