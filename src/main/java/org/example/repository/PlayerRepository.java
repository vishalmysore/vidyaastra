package org.example.repository;

import org.example.model.Player;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;

public interface PlayerRepository extends Neo4jRepository<Player, Long> {
    List<Player> findByTeamName(String teamName);
    
    @Query("MATCH (p:Player)-[r:PLAYS_FOR]->(t:Team) WHERE t.country = $country RETURN p")
    List<Player> findPlayersByCountry(String country);
    
    @Query("MATCH (p:Player)-[s:SCORED_IN]->(m:Match) " +
           "WHERE s.isManOfTheMatch = true " +
           "RETURN p")
    List<Player> findPlayersWithManOfTheMatch();
}