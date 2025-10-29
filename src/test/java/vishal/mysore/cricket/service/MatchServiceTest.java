package vishal.mysore.cricket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.cricket.model.Match;
import vishal.mysore.cricket.model.Team;
import vishal.mysore.cricket.repository.MatchRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Cricket Match Service Tests")
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    private Match testMatch;
    private Team team1;
    private Team team2;
    private Team winner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        team1 = new Team();
        team1.setId(UUID.randomUUID());
        team1.setName("India");
        team1.setCountry("India");

        team2 = new Team();
        team2.setId(UUID.randomUUID());
        team2.setName("Australia");
        team2.setCountry("Australia");

        winner = team1;

        testMatch = new Match();
        testMatch.setId(UUID.randomUUID());
        testMatch.setVenue("Eden Gardens");
        testMatch.setMatchDate(LocalDateTime.of(2024, 10, 29, 14, 0));
        testMatch.setMatchType("ODI");
        testMatch.setTeam1(team1);
        testMatch.setTeam2(team2);
        testMatch.setWinner(winner);
        testMatch.setResult("India won by 5 wickets");
    }

    @Test
    @DisplayName("Should save match successfully")
    void testSaveMatch() {
        when(matchRepository.save(any(Match.class))).thenReturn(testMatch);

        Match savedMatch = matchService.saveMatch(testMatch);

        assertNotNull(savedMatch);
        assertEquals("Eden Gardens", savedMatch.getVenue());
        assertEquals("ODI", savedMatch.getMatchType());
        assertEquals("India won by 5 wickets", savedMatch.getResult());
        verify(matchRepository, times(1)).save(testMatch);
    }

    @Test
    @DisplayName("Should retrieve all matches")
    void testGetAllMatches() {
        List<Match> matches = Arrays.asList(testMatch);
        when(matchRepository.findAll()).thenReturn(matches);

        List<Match> retrievedMatches = matchService.getAllMatches();

        assertNotNull(retrievedMatches);
        assertEquals(1, retrievedMatches.size());
        assertEquals("Eden Gardens", retrievedMatches.get(0).getVenue());
        verify(matchRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get matches by type")
    void testGetMatchesByType() {
        List<Match> matches = Arrays.asList(testMatch);
        when(matchRepository.findByMatchType("ODI")).thenReturn(matches);

        List<Match> result = matchService.getMatchesByType("ODI");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ODI", result.get(0).getMatchType());
        verify(matchRepository, times(1)).findByMatchType("ODI");
    }

    @Test
    @DisplayName("Should get matches by venue")
    void testGetMatchesByVenue() {
        List<Match> matches = Arrays.asList(testMatch);
        when(matchRepository.findByVenue("Eden Gardens")).thenReturn(matches);

        List<Match> result = matchService.getMatchesByVenue("Eden Gardens");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Eden Gardens", result.get(0).getVenue());
        verify(matchRepository, times(1)).findByVenue("Eden Gardens");
    }

    @Test
    @DisplayName("Should get matches won by team")
    void testGetMatchesWonByTeam() {
        List<Match> matches = Arrays.asList(testMatch);
        when(matchRepository.findMatchesWonByTeam("India")).thenReturn(matches);

        List<Match> result = matchService.getMatchesWonByTeam("India");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("India", result.get(0).getWinner().getName());
        verify(matchRepository, times(1)).findMatchesWonByTeam("India");
    }

    @Test
    @DisplayName("Should get matches between dates")
    void testGetMatchesBetweenDates() {
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 31, 23, 59);
        List<Match> matches = Arrays.asList(testMatch);
        when(matchRepository.findByMatchDateBetween(startDate, endDate)).thenReturn(matches);

        List<Match> result = matchService.getMatchesBetweenDates(startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(matchRepository, times(1)).findByMatchDateBetween(startDate, endDate);
    }

    @Test
    @DisplayName("Should validate match attributes")
    void testMatchAttributes() {
        assertNotNull(testMatch.getId());
        assertEquals("Eden Gardens", testMatch.getVenue());
        assertEquals("ODI", testMatch.getMatchType());
        assertNotNull(testMatch.getMatchDate());
        assertNotNull(testMatch.getTeam1());
        assertNotNull(testMatch.getTeam2());
        assertNotNull(testMatch.getWinner());
        assertEquals("India won by 5 wickets", testMatch.getResult());
    }

    @Test
    @DisplayName("Should return empty list when no matches found")
    void testGetAllMatchesEmpty() {
        when(matchRepository.findAll()).thenReturn(Arrays.asList());

        List<Match> matches = matchService.getAllMatches();

        assertNotNull(matches);
        assertTrue(matches.isEmpty());
        verify(matchRepository, times(1)).findAll();
    }
}

