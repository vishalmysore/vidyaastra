package org.example.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;
import java.time.LocalDateTime;

@Node("Match")
@Data
public class Match {
    @Id
    @GeneratedValue
    private Long id;
    
    private String venue;
    private LocalDateTime matchDate;
    private String matchType; // T20, ODI, Test
    
    @Relationship(type = "TEAM_1")
    private Team team1;
    
    @Relationship(type = "TEAM_2")
    private Team team2;
    
    @Relationship(type = "WINNER")
    private Team winner;
    
    private String result; // e.g., "Team1 won by 5 wickets"
}