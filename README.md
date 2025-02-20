# Fast & Furious cinema

### Project Overview
This application was developed as part of a recruitment process. It is a simple movie database service that integrates with the OpenMovieDatabase API to fetch movie-related data. The application provides a RESTful API for managing movie information and interacts with a PostgreSQL database for persistence.

### Requirements
A small cinema, which only plays movies from the Fast & Furious franchise, is looking to develop a mobile/web app for its users. Specifically, they wish to support the following functions:

- An internal endpoint in which they (i.e., the cinema owners) can update show times and prices for their movie catalog
- An endpoint in which their customers (i.e., moviegoers) can fetch movie times
- An endpoint in which their customers (i.e., moviegoers) can fetch details about one of their movies (e.g., name, description, release date, rating, IMDb rating, and runtime).
- Even though there's a limited offering, please use the OMDb APIs (detailed below) to demonstrate how to communicate across APIs.
- An endpoint in which their customers (i.e., moviegoers) can leave a review rating (from 1-5) about a particular movie
- And add anything else that you think will be useful for the user...


### Running the Project
Before running the application, you need to set up your OpenMovieDatabase API key. The key is loaded from the environment variable `OMDB_API_KEY`.
If you're using Bash, you can set it with
```bash
export OMDB_API_KEY=<YOUR_API_KEY>
```

### Running the project
You can start both the application and the database using Docker Compose:
```bash
docker compose up
```

If you prefer to run the application separately (e.g., inside IntelliJ), add the following environment variable to your run configuration:
`SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/cinema`

The database may still be running inside a Docker container, but since the application is now running outside the Docker Compose network, it needs to connect via localhost.

### API documentation
The full API documentation is located in [/docs/api.yml](https://github.com/theaffable/cinema-recruitment-task/blob/main/docs/api.yaml). You can view it using [Swagger Editor](https://editor-next.swagger.io/).

### Tests
API functionality is tested using an HTTP client. The request collection can be found [here](https://github.com/theaffable/cinema/blob/main/application/src/test/resources/requests.http)