## Application Database Setup

* Setup the Tables:
```
docker cp create_tables.sql hikaricpDemo-postgres:/create_tables.sql
docker exec -it hikaricpDemo-postgres psql -d postgres -f create_tables.sql -U postgres
```

* Install the Data:
```
docker cp insert_data.sql hikaricpDemo-postgres:/insert_data.sql
docker exec -it hikaricpDemo-postgres psql -d postgres -f insert_data.sql -U postgres
```


### JMH Connection Pool Microbenchmark Results 

###### dbSelectTest
```
HikariCP
Benchmark                             (batchSize)  Mode  Cnt    Score     Error  Units
BenchmarkTest.dbSelectTest.dbSelects            1  avgt    3    0.448 ±   0.336  ms/op
BenchmarkTest.dbSelectTest.dbSelects         1000  avgt    3  458.125 ± 139.778  ms/op

Tomcat
Benchmark                             (batchSize)  Mode  Cnt    Score     Error  Units
BenchmarkTest.dbSelectTest.dbSelects            1  avgt    3    0.454 ±   0.074  ms/op
BenchmarkTest.dbSelectTest.dbSelects         1000  avgt    3  475.981 ± 188.922  ms/op
```

###### dbInserts 

```
HikariCP
Benchmark                             (batchSize)  Mode  Cnt     Score     Error  Units
BenchmarkTest.dbInsertTest.dbInserts            1  avgt    3     2.150 ±   1.202  ms/op
BenchmarkTest.dbInsertTest.dbInserts         1000  avgt    3  4567.841 ± 935.077  ms/op

Benchmark                             (batchSize)  Mode  Cnt     Score      Error  Units
BenchmarkTest.dbInsertTest.dbInserts            1  avgt    3     4.794 ±    1.143  ms/op
BenchmarkTest.dbInsertTest.dbInserts         1000  avgt    3  4596.015 ± 1094.391  ms/op
```