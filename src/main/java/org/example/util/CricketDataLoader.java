package org.example.util;

import lombok.RequiredArgsConstructor;
import org.example.model.*;
import org.example.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CricketDataLoader implements CommandLineRunner {
    private final TeamService teamService;
    private final PlayerService playerService;
    private final MatchService matchService;

    @Override
    public void run(String... args) {
        // Create Teams
        Team india = new Team();
        india.setName("India");
        india.setCountry("India");
        teamService.saveTeam(india);

        Team australia = new Team();
        australia.setName("Australia");
        australia.setCountry("Australia");
        teamService.saveTeam(australia);

        // Create Players for India
        Player kohli = new Player();
        kohli.setName("Virat Kohli");
        kohli.setCountry("India");
        kohli.setRole("Batsman");
        kohli.setJerseyNumber(18);
        kohli.setTeam(india);
        playerService.savePlayer(kohli);

        Player rohit = new Player();
        rohit.setName("Rohit Sharma");
        rohit.setCountry("India");
        rohit.setRole("Batsman");
        rohit.setJerseyNumber(45);
        rohit.setTeam(india);
        playerService.savePlayer(rohit);

        // Create Players for Australia
        Player smith = new Player();
        smith.setName("Steve Smith");
        smith.setCountry("Australia");
        smith.setRole("Batsman");
        smith.setJerseyNumber(49);
        smith.setTeam(australia);
        playerService.savePlayer(smith);

        Player cummins = new Player();
        cummins.setName("Pat Cummins");
        cummins.setCountry("Australia");
        cummins.setRole("Bowler");
        cummins.setJerseyNumber(30);
        cummins.setTeam(australia);
        playerService.savePlayer(cummins);

        // Create a Match
        Match match = new Match();
        match.setVenue("Melbourne Cricket Ground");
        match.setMatchDate(LocalDateTime.now());
        match.setMatchType("ODI");
        match.setTeam1(india);
        match.setTeam2(australia);
        match.setWinner(india);
        match.setResult("India won by 5 wickets");
        matchService.saveMatch(match);
    }
}