# Vidya Astra - Knowledge Tool 

This project implements a graph database using Neo4j  and provides variouls examples 
1) to store and analyze cricket matches, players, and teams data. It uses Spring Boot with Spring Data Neo4j for database interactions.
2) My personal graph and knowledge about me collected from various sources.
3) A knowledge graph about various technologies and programming languages.

What does Vidya Astra mean?  
- Vidya means Knowledge/Wisdom in Sanskrit.
- Astra means Tool/Weapon in Sanskrit.
- So, Vidya Astra means "Tool of Knowledge" or "Weapon of Wisdom".



##  Neo4j Queries

1️⃣ List all teams and their players

```sql 
MATCH (t:Team)<-[r:PLAYS_FOR]-(p:Player)
RETURN t.name, collect(p.name);
``` 
shows which players belong to each team.

2️⃣ List all matches with teams and winners

```sql 
MATCH (m:Match)-[:TEAM_1]->(team1:Team),
      (m)-[:TEAM_2]->(team2:Team),
      (m)-[:WINNER]->(winner:Team)
RETURN m.venue AS Venue, m.matchDate AS Date, 
       team1.name AS Team1, team2.name AS Team2, 
       winner.name AS Winner, m.result AS Result;
```

✅ Shows all match details in one table.

3️⃣ Players grouped by role
```sql 
MATCH (p:Player)
RETURN p.role AS Role, collect(p.name) AS PlayersByRole;
```

✅ Groups players by Batsman, Bowler, etc.

4️⃣ Count of players per team
```sql 
MATCH (t:Team)<-[:PLAYS_FOR]-(p:Player)
RETURN t.name AS Team, count(p) AS PlayerCount;
```

✅ Quick check if every team has correct number of players.

5️⃣ List matches played by a specific team (e.g., India)
```sql 
MATCH (m:Match)-[:TEAM_1|TEAM_2]->(t:Team {name: "India"})
RETURN m.matchDate AS Date, m.venue AS Venue, 
       m.result AS Result;
```

✅ Shows all matches where India participated.

6️⃣ Teams that have won matches
```sql 
MATCH (m:Match)-[:WINNER]->(t:Team)
RETURN t.name AS WinningTeam, count(m) AS MatchesWon
ORDER BY MatchesWon DESC;
```

✅ Gives a leaderboard of teams by wins.

7️⃣ All players and their team
```sql 
MATCH (p:Player)-[:PLAYS_FOR]->(t:Team)
RETURN p.name AS Player, t.name AS Team;
```

✅ Just a flat list for reference.

8️⃣ Matches with players from both teams

```sql 
MATCH (m:Match)-[:TEAM_1]->(t1:Team)<-[:PLAYS_FOR]-(p1:Player),
      (m)-[:TEAM_2]->(t2:Team)<-[:PLAYS_FOR]-(p2:Player)
RETURN m.venue AS Venue, t1.name AS Team1, collect(p1.name) AS Team1Players,
       t2.name AS Team2, collect(p2.name) AS Team2Players;

```

✅ Shows each match and all players from both sides.

9️⃣ Count matches per team
```sql 
MATCH (t:Team)<-[:TEAM_1|TEAM_2]-(m:Match)
RETURN t.name AS Team, count(m) AS MatchesPlayed
ORDER BY MatchesPlayed DESC;
```

✅ See how many matches each team has played.

1️⃣0️⃣ Find players who have won at least one match
```sql 
MATCH (p:Player)-[:PLAYS_FOR]->(t:Team)<-[:WINNER]-(m:Match)
RETURN p.name AS Player, collect(m.result) AS Wins;
```

✅ Useful if you want a “winning players” list.

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