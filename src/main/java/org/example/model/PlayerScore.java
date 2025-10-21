package org.example.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@Data
public class PlayerScore {
    @Id
    @GeneratedValue
    private Long id;
    
    @TargetNode
    private Match match;
    
    private int runsScored;
    private int ballsFaced;
    private int wicketsTaken;
    private int oversCount;
    private int runsGiven;
    private boolean isManOfTheMatch;
}