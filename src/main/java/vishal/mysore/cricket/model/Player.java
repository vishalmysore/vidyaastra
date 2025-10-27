package vishal.mysore.cricket.model;

import lombok.Data;
import java.util.UUID;
import org.springframework.data.neo4j.core.schema.*;

@Node("Player")
@Data
@lombok.EqualsAndHashCode(exclude = "team")
@lombok.ToString(exclude = "team")
public class Player {
    @Id
    @GeneratedValue(GeneratedValue.UUIDGenerator.class)
    private UUID id;
    
    private String name;
    private String country;
    private String role; // batsman, bowler, all-rounder
    private int jerseyNumber;
    
    @Relationship(type = "PLAYS_FOR", direction = Relationship.Direction.OUTGOING)
    private Team team;
    
    // Helper method to set team and establish relationship
    public void setTeam(Team team) {
        if (this.team != null && !this.team.equals(team)) {
            // Handle old team relationship cleanup if needed
            this.team = null;
        }
        this.team = team;
    }
}