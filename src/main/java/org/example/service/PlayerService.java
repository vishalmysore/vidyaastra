package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Player;
import org.example.model.Team;
import org.example.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }
    
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
    
    public List<Player> getPlayersByTeam(String teamName) {
        return playerRepository.findByTeamName(teamName);
    }
    
    public List<Player> getPlayersByCountry(String country) {
        return playerRepository.findPlayersByCountry(country);
    }
    
    public List<Player> getManOfTheMatchPlayers() {
        return playerRepository.findPlayersWithManOfTheMatch();
    }
}