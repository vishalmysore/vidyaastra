# Cricket Match Graph Database Project

This project implements a graph database using Neo4j to store and analyze cricket matches, players, and teams data. It uses Spring Boot with Spring Data Neo4j for database interactions.

## Prerequisites

- Java 17 or higher
- Maven
- Neo4j Aura account (or local Neo4j instance)
- Git (optional)

## Project Structure

```
VidyaAstra/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/
│   │   │       ├── model/
│   │   │       │   ├── Player.java
│   │   │       │   ├── Team.java
│   │   │       │   ├── Match.java
│   │   │       │   └── PlayerScore.java
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── util/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## Database Schema

The project uses the following graph model:
- Players (nodes) with properties: name, country, role, jerseyNumber
- Teams (nodes) with properties: name, country
- Matches (nodes) with properties: venue, matchDate, matchType, result
- Relationships:
  - PLAYS_FOR: Player → Team
  - PLAYED_MATCH: Team → Match
  - WINNER: Match → Team
  - SCORED_IN: Player → Match (with performance statistics)

## Configuration

1. Set up environment variables for Neo4j Aura connection:

```powershell
# Windows PowerShell
$env:NEO4J_PASSWORD="your-neo4j-password"
```

```bash
# Linux/macOS
export NEO4J_PASSWORD="your-neo4j-password"
```

The application.properties already contains the connection details for:
- URI: neo4j+s://bb3147a9.databases.neo4j.io
- Username: neo4j
- Database: neo4j

## Building and Running

1. Clone or download the project
2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will:
- Connect to Neo4j database
- Create the graph schema
- Load sample cricket match data (through CricketDataLoader)

## Available Queries

The application supports various queries through repository interfaces:

### Player Queries
- Find players by team
- Find players by country
- Find players who won Man of the Match

### Team Queries
- Find teams by number of wins
- Find teams by matches played
- Find team by name

### Match Queries
- Find matches by type (T20, ODI, Test)
- Find matches by venue
- Find matches won by a specific team
- Find matches between dates

## Sample Data

The application includes a data loader (`CricketDataLoader.java`) that populates the database with sample data including:
- Teams (India, Australia)
- Players (Virat Kohli, Rohit Sharma, Steve Smith, Pat Cummins)
- A sample match between India and Australia

## Testing

To run the tests:
```bash
mvn test
```

## Troubleshooting

1. **Connection Issues**
   - Verify your Neo4j password is correctly set in environment variables
   - Check if the Neo4j Aura instance is running
   - Verify network connectivity to Neo4j Aura

2. **Build Issues**
   - Ensure Java 17 is installed and JAVA_HOME is properly set
   - Clear Maven cache if needed: `mvn clean`

## Contributing

Feel free to submit issues and enhancement requests.

## License

This project is licensed under the MIT License - see the LICENSE file for details.