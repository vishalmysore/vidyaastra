package vishal.mysore.cricket.service;

import lombok.RequiredArgsConstructor;
import vishal.mysore.cricket.model.Match;
import vishal.mysore.cricket.repository.MatchRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    
    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }
    
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }
    
    public List<Match> getMatchesByType(String matchType) {
        return matchRepository.findByMatchType(matchType);
    }
    
    public List<Match> getMatchesByVenue(String venue) {
        return matchRepository.findByVenue(venue);
    }
    
    public List<Match> getMatchesWonByTeam(String teamName) {
        return matchRepository.findMatchesWonByTeam(teamName);
    }
    
    public List<Match> getMatchesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return matchRepository.findByMatchDateBetween(startDate, endDate);
    }
}