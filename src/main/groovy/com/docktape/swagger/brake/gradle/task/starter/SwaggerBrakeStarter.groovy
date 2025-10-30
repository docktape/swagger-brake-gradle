package com.docktape.swagger.brake.gradle.task.starter

import com.docktape.swagger.brake.core.BreakingChange
import com.docktape.swagger.brake.runner.Options

class SwaggerBrakeStarter implements Starter {
    @Override
    Collection<BreakingChange> start(Options options) {
        return com.docktape.swagger.brake.runner.Starter.start(options)
    }
}
