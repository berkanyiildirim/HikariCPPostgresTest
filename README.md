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
