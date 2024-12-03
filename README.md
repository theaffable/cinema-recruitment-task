# Fast & Furious cinema

### Configuration
Before you run this application you need to configure OpenMovieDatabase api key. The key is loaded through an environment variable `OMDB_API_KEY`.

If you're using bash simply run\
`export OMDB_API_PATH=<YOUR_API_KEY>`


### Usage
TODO: add docker compose command to run the app & db

### API documentation
Full API documentation is located in `/docs/api.yml`. You can use [Swagger Editor](https://editor-next.swagger.io/) to view it

### Q&A
**Q:** requirements state that I should use OpenAPI 2.0, but it's an old standard - should I use 3.0 instead?\
**A**: at first glance 2.0 seems to be good enough so I'll stick with that, but if needed I'll upgrade to 3.0

**Q:** How do I lock movie screening editing to owners only?\
**A:** Options to consider:  
  - `API_KEY`:
    - pros: easy to set up
    - cons: I'd need a separate mechanism to determine if someone can rate a movie or allow same client to rate a movie multiple times
  - `Basic Auth`:
    - pros: easy to set up, can be used for different purposes depending on what authorities we give to the users
    - cons: insecure, needs to be used together with HTTPS, but since this is a demo app I'm not that worried about it and I need to save time somewhere
  - `IP Whitelisting`:
    - pros: handled on infrastructure layer, doesn't affect application logic
    - cons: the app needs to be deployed for it to work and clients need to have a static IP
  - `Bearer Token`:
    - pros: industry standard, field tested, lots of libraries to help with implementation
    - cons: requires significantly more work than API_KEY / basic auth solutions

After some consideration I decided to go with Basic Auth since I can implement it quickly and it can solve 2 of my issues:
  - verifying if the user is an owner
  - verifying if the user has already rated a movie

**Q:** Should I add pagination to movie screening and movies endpoints?\
**A:** No, I plan to implement filtering on the endpoint and both of those will have a rather limited amount of entries. Plus it's easy to add later on.

**Q:** How do I make sure that movie reviews don't get flooded with artificial ratings?\
**A:** The plan is to allow only one vote per user & movie

### Ideas
- add showtime booking
- add movie description full text filtering
- add movie sorting by rating/imdb_rating