# Fast & Furious cinema

### Configuration
Before you run this application you need to configure OpenMovieDatabase api key. The key is loaded through an environment variable `OMDB_API_KEY`

If you're using bash simply run `export OMDB_API_KEY=<YOUR_API_KEY>`


### Running the project
You can use docker compose to start both the application and the database:
`docker compose up`

If you want to run the app separately (i.e. inside IntelliJ) add this env variable to your run configuration:
`SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/cinema`
The database might still be running inside a docker container, but the app itself is now launched outside of docker compose network - that's why we point it towards localhost.

### API documentation
Full API documentation is located in [/docs/api.yml](https://raw.githubusercontent.com/theaffable/cinema/refs/heads/main/docs/api.yaml). You can use [Swagger Editor](https://editor-next.swagger.io/) to view it

### Tests
I've used a http client to test API functionality, the requests file can be found [here](https://github.com/theaffable/cinema/blob/main/src/test/resources/requests.http)