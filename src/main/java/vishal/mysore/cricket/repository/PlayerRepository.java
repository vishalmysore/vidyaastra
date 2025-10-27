package vishal.mysore.cricket.repository;

import vishal.mysore.cricket.model.Player;
import vishal.mysore.cricket.model.Team;
import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;

public interface PlayerRepository extends Neo4jRepository<Player, UUID> {
    @Query("MATCH (p:Player)-[r:PLAYS_FOR]->(t:Team) WHERE t.id = $team.id RETURN p")
    List<Player> findByTeam(Team team);
    
    @Query("MATCH (p:Player)-[r:PLAYS_FOR]->(t:Team) WHERE t.country = $country RETURN p")
    List<Player> findPlayersByCountry(String country);
    
    @Query("MATCH (p:Player)-[s:SCORED_IN]->(m:Match) " +
           "WHERE s.isManOfTheMatch = true " +
           "RETURN p")
    List<Player> findPlayersWithManOfTheMatch();
}