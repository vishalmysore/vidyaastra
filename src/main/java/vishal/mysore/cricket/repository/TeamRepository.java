package vishal.mysore.cricket.repository;

import vishal.mysore.cricket.model.Team;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;
import java.util.UUID;

public interface TeamRepository extends Neo4jRepository<Team, UUID> {
    Team findByName(String name);
    
    @Query("MATCH (t:Team)-[r:WINNER]-(m:Match) RETURN t, COUNT(m) as wins ORDER BY wins DESC")
    List<Team> findTeamsByWins();
    
    @Query("MATCH (t:Team)-[r:PLAYED_MATCH]-(m:Match) " +
           "WHERE m.matchType = $matchType " +
           "RETURN t, COUNT(m) as matches ORDER BY matches DESC")
    List<Team> findTeamsByMatchesPlayed(String matchType);
}