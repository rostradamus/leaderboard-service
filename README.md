# Leaderboard Service
## Getting Started

### Required Dependencies
* JDK 1.8 or later
* Apache Maven 3.6.3
### Run Guide
```
$ git clone git@github.com:rostradamus/leaderboard-service.git
$ mvn clean install
$ mvn spring-boot:run
```

## Supported APIs
```
GET /api/leaderboard/by-id/{userId}
GET /api/leaderboard/by-rank/{targetRank}
POST /api/leaderboard/by-id/{userId}
    Body: { userId: int, score: double }
```
