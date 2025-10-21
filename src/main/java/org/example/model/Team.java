package org.example.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;
import java.util.List;

@Node("Team")
@Data
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    private String country;
    
    @Relationship(type = "HAS_PLAYER", direction = Relationship.Direction.OUTGOING)
    private List<Player> players;
    
    @Relationship(type = "PLAYED_MATCH")
    private List<Match> matches;
}