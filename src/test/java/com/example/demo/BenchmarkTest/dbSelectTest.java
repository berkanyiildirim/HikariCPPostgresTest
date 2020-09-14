package com.example.demo.BenchmarkTest;

import com.example.demo.models.Session;
import com.example.demo.repositories.SessionRepository;
import com.example.demo.repositories.SpeakerRepository;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class dbSelectTest {

    private static SessionRepository sessionRepository;
    private static SpeakerRepository speakerRepository;

    @Autowired
    public void setRepository(SessionRepository sessionRepository,SpeakerRepository speakerRepository) {
        dbSelectTest.sessionRepository = sessionRepository;
        dbSelectTest.speakerRepository = speakerRepository;
    }

    @Test
    public void runBenchmarks() throws Exception {
        Options opts = new OptionsBuilder()
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(3)
                .measurementIterations(3)
                .forks(0)
                .threads(1)
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

    @Benchmark
    public void dbSelects(Parameters parameters) throws SQLException {
        int size = Integer.parseInt(parameters.batchSize);

        for (int i = 0; i < size; i++) {
            speakerRepository.findAll();
        }
    }

    @State(value = Scope.Benchmark)
    public static class Parameters {

        @Param({"1", "1000"})
        String batchSize;
    }

}
