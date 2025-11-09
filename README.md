# Vidya Astra - Knowledge Tool 

This project implements multiple knowledge graphs using Neo4j and Spring Boot with Spring Data Neo4j for database interactions. It provides the following modules:

1) **Cricket** - Store and analyze cricket matches, players, teams, and performance statistics
2) **Healthcare (HC)** - Medical knowledge graph covering diseases, treatments, medications, and healthcare providers
3) **Yoga** - Yoga poses, practices, health benefits, and yoga-related knowledge
4) **Supermarket (Supermart)** - Retail knowledge graph with departments, products, categories, brands, suppliers, customers, and promotions
5) **Fraud Detection (FD)** - Fraud detection and prevention knowledge graph with fraud types, detection methods, indicators, and prevention strategies
6) **Bird Migration** - Knowledge graph on bird species, migration patterns, habitats, and conservation status


What does Vidya Astra mean?  
- Vidya means Knowledge/Wisdom in Sanskrit.
- Astra means Tool/Weapon in Sanskrit.
- So, Vidya Astra means "Tool of Knowledge" or "Weapon of Wisdom".

## Live Demos 

https://vishalmysore.github.io/vidyaastra/graphs/  - My Intro in Graph  
https://vishalmysore.github.io/vidyaastra/graphs/fraud_detection  - Fraud Detection Graph Demo  
https://vishalmysore.github.io/vidyaastra/graphs/cycle_detection - Cycle Detection Graph Demo for Fraud Detection 
https://vishalmysore.github.io/vidyaastra/graphs/bird_migration - Bird Migration Graph Demo  
https://vishalmysore.github.io/vidyaastra/graphs/healthcare - Healthcare Graph Demo  
https://vishalmysore.github.io/vidyaastra/graphs/cyber_security - Cyber Security Graph Demo
https://vishalmysore.github.io/vidyaastra/graphs/railgadi - Indian Railways Graph Demo
https://vishalmysore.github.io/vidyaastra/graphs/iloveny - New York Tourism Graph Demo

## Module Descriptions

### 🏏 Cricket Module
Stores and analyzes cricket matches, players, teams, and performance statistics. Track match results, player roles, and team achievements.

**Key Entities:**
- Team: name, country
- Player: name, country, role (Batsman/Bowler), jerseyNumber
- Match: venue, matchDate, matchType, result
- PlayerScore: performance statistics

### 🏥 Healthcare Module (HC)
Medical knowledge graph covering diseases, treatments, medications, and healthcare providers. Build relationships between medical conditions and their remedies.

**Key Entities:**
- Disease: name, symptoms, severity
- Treatment: name, description, type
- Medication: name, dosage, sideEffects
- Provider: name, specialization, location

### 🧘 Yoga Module
Yoga poses, practices, health benefits, and yoga-related knowledge. Organize yoga practices by difficulty level and target body areas.

**Key Entities:**
- Pose: name, difficulty, sanskritName
- Practice: name, description, duration
- Benefit: description, targetArea
- Instructor: name, experience, specialization

### 🛒 Supermarket Module (Supermart)
Retail knowledge graph with departments, products, categories, brands, suppliers, customers, and promotions.

**Key Entities:**
- Department: name, description
- Product: name, price, description
- Category: name, classification
- Brand: name, description
- Supplier: name, contact
- Customer: name, preferences
- Promotion: name, discount, validity

### 🚨 Fraud Detection Module (FD)
Fraud detection and prevention knowledge graph with fraud types, detection methods, indicators, and prevention strategies.

**Key Entities:**
- FraudType: name, category, riskLevel
- DetectionMethod: name, accuracy, description
- FraudIndicator: name, riskScore, pattern
- PreventionMethod: name, effectiveness
- DetectionTool: name, type, provider

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

### Prerequisites

1. Set up environment variables for Neo4j Aura connection:

```powershell
# Windows Command Prompt
set NEO4J_PASSWORD=your-neo4j-password

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

### Build the Project

```bash
mvn clean install
```

### Running Individual Modules

Each module can be run independently by specifying the active profile. Use the `-Dspring.profiles.active=` parameter:

**Cricket Module:**
```cmd
set NEO4J_PASSWORD=your-neo4j-password
mvn spring-boot:run -Dspring.profiles.active=cricket
```

**Healthcare Module (HC):**
```cmd
set NEO4J_PASSWORD=your-neo4j-password
mvn spring-boot:run -Dspring.profiles.active=hc
```

**Yoga Module:**
```cmd
set NEO4J_PASSWORD=your-neo4j-password
mvn spring-boot:run -Dspring.profiles.active=yoga
```

**Supermarket Module (Supermart):**
```cmd
set NEO4J_PASSWORD=your-neo4j-password
mvn spring-boot:run -Dspring.profiles.active=supermart
```

**Fraud Detection Module (FD):**
```cmd
set NEO4J_PASSWORD=your-neo4j-password
mvn spring-boot:run -Dspring.profiles.active=fd
```

For Linux/macOS, use `export` instead of `set`.

### Batch Scripts (Windows)

You can create batch files for quick execution. For example, `run-hc.bat`:

```batch
@echo off
set NEO4J_PASSWORD=your-neo4j-password
mvn spring-boot:run -Dspring.profiles.active=hc
pause
```

Then simply run:
```cmd
run-hc.bat
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