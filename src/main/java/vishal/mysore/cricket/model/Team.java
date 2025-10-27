package vishal.mysore.cricket.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Node("Team")
@Data
@lombok.EqualsAndHashCode(exclude = {"players", "matches"})
@lombok.ToString(exclude = {"players", "matches"})
public class Team {
    @Id
    @GeneratedValue(GeneratedValue.UUIDGenerator.class)
    private UUID id;
    
    private String name;
    private String country;
    
    @Relationship(type = "PLAYED_MATCH")
    private Set<Match> matches = new HashSet<>();
    
    @Relationship(type = "PLAYS_FOR", direction = Relationship.Direction.INCOMING)
    private Set<Player> players = new HashSet<>();
    
    public void addPlayer(Player player) {
        if (players == null) {
            players = new HashSet<>();
        }
        players.add(player);
        player.setTeam(this);
    }
}