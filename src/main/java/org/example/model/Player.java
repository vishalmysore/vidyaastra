package org.example.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

@Node("Player")
@Data
public class Player {
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    private String country;
    private String role; // batsman, bowler, all-rounder
    private int jerseyNumber;
    
    @Relationship(type = "PLAYS_FOR")
    private Team team;
}