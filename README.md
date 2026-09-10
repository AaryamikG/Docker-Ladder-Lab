# Lab: Docker Ladder
#### Table of Contents
* [Scenario](#scenario)
* [Goals](#goals)
* [The Challenge](#the-challenge)
  * [Must-have requirements](#must-have-requirements)
* [Stretch-goals (not required)](#stretch-goals-not-required)
* [Constraints](#constraints)
* [What to Deliver](#what-to-deliver)
* [References](#references)
#### Scenario
You are tasked with containerizing a pair of Spring Boot microservices and a database so they can be run consistently in any environment. A teammate should be able to spin up the full system locally with one command.
#### Goals
* Create Dockerfiles for Java Spring Boot applications.
* Build and run containerized microservices locally.
* Use Docker Compose to orchestrate multi-container applications.
* Implement container networking and volume management.
#### The Challenge
Turn your Spring Boot services into portable containers and orchestrate them with Compose.
#### Must-have requirements
1. **Dockerfiles**
   * Write minimal Dockerfiles for two Spring Boot applications.
   * Use multi-stage builds to keep image size small.
   * Run containers as non-root users.
2. **Build and run**
   * Build images locally.
   * Verify containers run and expose application endpoints.
3. **Compose orchestration**
   * Create a `docker-compose.yml` that starts both services plus a PostgreSQL container.
   * Configure networks so services communicate by container name.
   * Use environment variables for service configuration.
4. **Volumes**
   * Persist PostgreSQL data in a named volume.
   * Optionally mount a local volume for hot reload in dev profile.
#### Stretch-goals (not required)
* Optimize image layers and caching.
* Add a healthcheck section in `docker-compose.yml`.
* Configure service dependencies (`depends_on`) with health conditions.
#### Constraints
* Keep Dockerfiles minimal, start from `eclipse-temurin:25-jdk` or similar.
* Avoid bloated images or unnecessary tools.
* Prefer convention over manual scripting where possible.
#### What to Deliver
* `Dockerfile` for each Spring Boot service.
* `docker-compose.yml` that starts services and database.
* `README.md` with:
  * Commands to build and run images.
  * Example curl or HTTPie calls to verify endpoints.
  * Explanation of network and volume setup.
#### References
* [Docker Documentation](https://docs.docker.com/)
* [Dockerfile Reference](https://docs.docker.com/reference/dockerfile/)
* [Docker Compose Documentation](https://docs.docker.com/compose/)
* [Spring Boot Container Images](https://docs.spring.io/spring-boot/docs/current/reference/html/container-images.html)

## Running it

#### Build & run
```
docker compose up --build
```
This builds the `catalog-service` and `orders-service` images from their Dockerfiles and starts them alongside a Postgres container — one command, full system.

Other useful commands:
```
docker compose build      # build images without starting containers
docker compose up -d      # start in the background
docker compose down       # stop and remove containers
docker compose down -v    # also remove the postgres_data volume (wipes DB data)
```

#### Verify the endpoints
```
curl http://localhost:8081/api/catalog/items
curl http://localhost:8081/api/catalog/database
curl http://localhost:8082/api/orders
curl http://localhost:8082/api/orders/catalog-items
curl http://localhost:8082/api/orders/database
```
`orders/catalog-items` calls out to catalog-service over the internal Docker network and proxies its response, so a successful response there confirms both services and their networking are working end-to-end.

#### Network & volume setup
All three containers (`postgres`, `catalog-service`, `orders-service`) join a user-defined bridge network, `ladder-net`. On a user-defined network, Docker's embedded DNS resolves each service by its Compose service name, so `orders-service` reaches Postgres at `postgres:5432` and calls catalog-service at `http://catalog-service:8080` — no hardcoded IPs, no host networking. Externally, only `catalog-service` (8081), `orders-service` (8082), and `postgres` (5432, for local debugging) are published to the host.

Postgres data is persisted in the named volume `postgres_data`, mounted at `/var/lib/postgresql/data` inside the container. Because the volume is named (not bound to the container's writable layer), the database survives `docker compose down` / container recreation and is only removed if you explicitly run `docker compose down -v`.
