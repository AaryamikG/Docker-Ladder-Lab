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
