package org.example.util;

import lombok.RequiredArgsConstructor;
import org.example.model.*;
import org.example.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.time.LocalDateTime;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class CricketDataLoader implements CommandLineRunner {

    private final TeamService teamService;
    private final PlayerService playerService;
    private final MatchService matchService;
    private final Neo4jClient neo4jClient;

    // Clear all nodes in the database
    private void clearDatabase() {
        neo4jClient.query("MATCH (n) DETACH DELETE n").run();
        System.out.println("Database cleared successfully");
    }

    // Print counts and node types for debugging
    private void verifyDatabaseCount(String stage) {
        long nodeCount = neo4jClient.query("MATCH (n) RETURN count(n) AS count")
                .fetchAs(Long.class)
                .mappedBy((ts, r) -> r.get("count").asLong())
                .one()
                .orElse(0L);
        System.out.println(stage + " - Total nodes in database: " + nodeCount);

        neo4jClient.query("CALL db.labels() YIELD label RETURN collect(label) as labels")
                .fetchAs(String.class)
                .mappedBy((ts, r) -> r.get("labels").toString())
                .one()
                .ifPresent(labels -> System.out.println("Node types: " + labels));
    }

    @Override
    public void run(String... args) {
        try {
            // Step 1: Clear database
            clearDatabase();
            verifyDatabaseCount("After clearing");

            // Step 2: Create Teams
            Team india = new Team();
            india.setName("India");
            india.setCountry("India");
            india.setPlayers(new HashSet<>());
            india.setMatches(new HashSet<>());
            india = teamService.saveTeam(india);

            Team australia = new Team();
            australia.setName("Australia");
            australia.setCountry("Australia");
            australia.setPlayers(new HashSet<>());
            australia.setMatches(new HashSet<>());
            australia = teamService.saveTeam(australia);

            // Step 3: Create Players for India
            Player kohli = new Player();
            kohli.setName("Virat Kohli");
            kohli.setCountry("India");
            kohli.setRole("Batsman");
            kohli.setJerseyNumber(18);
            kohli.setTeam(india);
            kohli = playerService.savePlayer(kohli);
            india.getPlayers().add(kohli);

            Player rohit = new Player();
            rohit.setName("Rohit Sharma");
            rohit.setCountry("India");
            rohit.setRole("Batsman");
            rohit.setJerseyNumber(45);
            rohit.setTeam(india);
            rohit = playerService.savePlayer(rohit);
            india.getPlayers().add(rohit);

            // Step 4: Create Players for Australia
            Player smith = new Player();
            smith.setName("Steve Smith");
            smith.setCountry("Australia");
            smith.setRole("Batsman");
            smith.setJerseyNumber(49);
            smith.setTeam(australia);
            smith = playerService.savePlayer(smith);
            australia.getPlayers().add(smith);

            Player cummins = new Player();
            cummins.setName("Pat Cummins");
            cummins.setCountry("Australia");
            cummins.setRole("Bowler");
            cummins.setJerseyNumber(30);
            cummins.setTeam(australia);
            cummins = playerService.savePlayer(cummins);
            australia.getPlayers().add(cummins);

            // Step 5: Save Teams again to update relationships
            teamService.saveTeam(india);
            teamService.saveTeam(australia);

            verifyDatabaseCount("After saving teams and players");

            // Step 6: Create Match
            Match match = new Match();
            match.setVenue("Melbourne Cricket Ground");
            match.setMatchDate(LocalDateTime.now());
            match.setMatchType("ODI");
            match.setTeam1(india);
            match.setTeam2(australia);
            match.setWinner(india);
            match.setResult("India won by 5 wickets");

            // Update teams with match
            india.getMatches().add(match);
            australia.getMatches().add(match);

            matchService.saveMatch(match);
            teamService.saveTeam(india);
            teamService.saveTeam(australia);

            System.out.println("\nCricket data loaded successfully!");

            // Step 7: Print Teams
            System.out.println("\n=== Teams ===");
            teamService.getAllTeams().forEach(team -> {
                System.out.println("Team: " + team.getName() + " (" + team.getCountry() + ")");
                System.out.println("Players:");
                team.getPlayers().forEach(player -> System.out.println(" - " + player.getName() + " (" + player.getRole() + ")"));
                System.out.println("Matches: " + team.getMatches().size());
            });

            // Step 8: Print Matches
            System.out.println("\n=== Matches ===");
            matchService.getAllMatches().forEach(m -> {
                System.out.println("Match: " + m.getTeam1().getName() + " vs " + m.getTeam2().getName());
                System.out.println("Venue: " + m.getVenue());
                System.out.println("Type: " + m.getMatchType());
                System.out.println("Result: " + m.getResult());
                System.out.println();
            });

            verifyDatabaseCount("Final count");

            // Exit
            System.exit(0);

        } catch (Exception e) {
            System.err.println("Failed to load cricket data: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load cricket data", e);
        }
    }
}
