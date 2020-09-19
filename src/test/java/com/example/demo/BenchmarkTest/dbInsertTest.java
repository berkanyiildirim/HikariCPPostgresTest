package com.example.demo.BenchmarkTest;

import com.example.demo.models.Session;
import com.example.demo.repositories.SessionRepository;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class dbInsertTest {

    private static SessionRepository sessionRepository;

    @Autowired
    public void setRepository(SessionRepository sessionRepository) {
        dbInsertTest.sessionRepository = sessionRepository;
    }

    @Test
    public void runBenchmarks() throws Exception {
        Options opts = new OptionsBuilder()
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(3)
                .measurementIterations(3)
                .forks(0)
                .threads(16)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();

        new Runner(opts).run();
    }

    @Benchmark
    public void dbInserts(Parameters parameters) {
        int size = Integer.parseInt(parameters.batchSize);

        for (int i = 0; i < size; i++) {
            Session session = new Session();
            session.setSession_name("A Deep Dive Into Spring IoC");
            session.setSession_description("");
            session.setSession_length(45);
            sessionRepository.save(session);
        }
    }

    @State(value = Scope.Benchmark)
    public static class Parameters {

        @Param({"1", "1000"})
        String batchSize;
    }
}
