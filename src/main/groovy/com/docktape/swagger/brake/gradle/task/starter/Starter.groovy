package com.docktape.swagger.brake.gradle.task.starter

import com.docktape.swagger.brake.core.BreakingChange
import com.docktape.swagger.brake.runner.Options

interface Starter {
    Collection<BreakingChange> start(Options options)
}
