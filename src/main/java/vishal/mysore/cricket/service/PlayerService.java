package vishal.mysore.cricket.service;

import lombok.RequiredArgsConstructor;
import vishal.mysore.cricket.model.Player;
import vishal.mysore.cricket.model.Team;
import vishal.mysore.cricket.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    
    public Player savePlayer(Player player) {
        if (player.getTeam() == null) {
            throw new IllegalArgumentException("Player must be associated with a team");
        }
        // Save player directly - it's the owning side of the relationship
        return playerRepository.save(player);
    }
    
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
    
    public List<Player> getPlayersByTeam(Team team) {
        return playerRepository.findByTeam(team);
    }
    
    public List<Player> getPlayersByCountry(String country) {
        return playerRepository.findPlayersByCountry(country);
    }
    
    public List<Player> getManOfTheMatchPlayers() {
        return playerRepository.findPlayersWithManOfTheMatch();
    }
}