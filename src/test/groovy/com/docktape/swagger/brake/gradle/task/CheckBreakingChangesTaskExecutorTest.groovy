package com.docktape.swagger.brake.gradle.task

import com.docktape.swagger.brake.core.BreakingChange
import com.docktape.swagger.brake.gradle.task.starter.Starter
import com.docktape.swagger.brake.gradle.task.starter.StarterFactory
import com.docktape.swagger.brake.runner.Options
import com.docktape.swagger.brake.runner.exception.LatestArtifactDownloadException
import spock.lang.Specification

import static java.util.Collections.emptyList

class CheckBreakingChangesTaskExecutorTest extends Specification {
    private Options options
    private StarterFactory starterFactory

    private CheckBreakingChangesTaskExecutor underTest

    void setup() {
        options = Mock(Options)
        starterFactory = Mock(StarterFactory)
        underTest = new CheckBreakingChangesTaskExecutor(starterFactory)
    }

    def "Starter is called and nothing strange happens when no breaking changes are detected"() {
        given:
        def starter = Mock(Starter)

        when:
        underTest.execute(options)

        then:
        1 * starterFactory.create() >> starter
        1 * starter.start(options) >> emptyList()
    }

    def "Starter is called and execution continues when only latest artifact resolution was a problem"() {
        given:
        def starter = Mock(Starter)

        when:
        underTest.execute(options)

        then:
        1 * starterFactory.create() >> starter
        1 * starter.start(options) >> { throw new LatestArtifactDownloadException("", new RuntimeException()) }
    }

    def "Starter is called and exception is thrown when breaking changes are found"() {
        given:
        def bc = Mock(BreakingChange)
        def starter = Mock(Starter)

        when:
        underTest.execute(options)

        then:
        1 * starterFactory.create() >> starter
        1 * starter.start(options) >> Collections.singleton(bc)
        thrown(BreakingChangeDetectedException)
    }
}
