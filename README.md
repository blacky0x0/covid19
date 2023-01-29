### Build

```
./mvnw clean package -DskipTests=true
```

---

### Run

```
# start Redis in Docker container
docker-compose -f docker/docker-compose.yml up -d

# launch an application
./mvnw spring-boot:run

# stop Redis in Docker container
docker-compose -f docker/docker-compose.yml down
```

---

### Test

```
# start Redis in Docker container
docker-compose -f docker/docker-compose.yml up -d

# start tests
./mvnw test
```

---

### Swagger Endpoints

* http://localhost:8080/swagger-ui/index.html

![REST API](REST_API.png)

---