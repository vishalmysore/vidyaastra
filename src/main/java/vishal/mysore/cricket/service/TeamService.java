package vishal.mysore.cricket.service;

import lombok.RequiredArgsConstructor;
import vishal.mysore.cricket.model.Team;
import vishal.mysore.cricket.repository.TeamRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }
    
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
    
    public Team getTeamByName(String name) {
        return teamRepository.findByName(name);
    }
    
    public List<Team> getTeamsByWins() {
        return teamRepository.findTeamsByWins();
    }
    
    public List<Team> getTeamsByMatchesPlayed(String matchType) {
        return teamRepository.findTeamsByMatchesPlayed(matchType);
    }
}