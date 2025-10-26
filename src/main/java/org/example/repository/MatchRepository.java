package org.example.repository;

import org.example.model.Match;
import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends Neo4jRepository<Match, UUID> {
    List<Match> findByMatchType(String matchType);
    
    List<Match> findByVenue(String venue);
    
    @Query("MATCH (m:Match)-[r:WINNER]->(t:Team) " +
           "WHERE t.name = $teamName " +
           "RETURN m ORDER BY m.matchDate DESC")
    List<Match> findMatchesWonByTeam(String teamName);
    
    List<Match> findByMatchDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}