package vishal.mysore.cricket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.cricket.model.Team;
import vishal.mysore.cricket.repository.TeamRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Cricket Team Service Tests")
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    private Team testTeam;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testTeam = new Team();
        testTeam.setId(UUID.randomUUID());
        testTeam.setName("India");
        testTeam.setCountry("India");
    }

    @Test
    @DisplayName("Should save team successfully")
    void testSaveTeam() {
        when(teamRepository.save(any(Team.class))).thenReturn(testTeam);

        Team savedTeam = teamService.saveTeam(testTeam);

        assertNotNull(savedTeam);
        assertEquals("India", savedTeam.getName());
        assertEquals("India", savedTeam.getCountry());
        verify(teamRepository, times(1)).save(testTeam);
    }

    @Test
    @DisplayName("Should retrieve all teams")
    void testGetAllTeams() {
        Team team2 = new Team();
        team2.setId(UUID.randomUUID());
        team2.setName("Australia");
        team2.setCountry("Australia");

        List<Team> teams = Arrays.asList(testTeam, team2);
        when(teamRepository.findAll()).thenReturn(teams);

        List<Team> retrievedTeams = teamService.getAllTeams();

        assertNotNull(retrievedTeams);
        assertEquals(2, retrievedTeams.size());
        assertEquals("India", retrievedTeams.get(0).getName());
        assertEquals("Australia", retrievedTeams.get(1).getName());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get team by name")
    void testGetTeamByName() {
        when(teamRepository.findByName("India")).thenReturn(testTeam);

        Team result = teamService.getTeamByName("India");

        assertNotNull(result);
        assertEquals("India", result.getName());
        verify(teamRepository, times(1)).findByName("India");
    }

    @Test
    @DisplayName("Should get teams by wins")
    void testGetTeamsByWins() {
        List<Team> teams = Arrays.asList(testTeam);
        when(teamRepository.findTeamsByWins()).thenReturn(teams);

        List<Team> result = teamService.getTeamsByWins();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(teamRepository, times(1)).findTeamsByWins();
    }

    @Test
    @DisplayName("Should get teams by matches played")
    void testGetTeamsByMatchesPlayed() {
        List<Team> teams = Arrays.asList(testTeam);
        when(teamRepository.findTeamsByMatchesPlayed("ODI")).thenReturn(teams);

        List<Team> result = teamService.getTeamsByMatchesPlayed("ODI");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(teamRepository, times(1)).findTeamsByMatchesPlayed("ODI");
    }

    @Test
    @DisplayName("Should return null when team not found")
    void testGetTeamByNameNotFound() {
        when(teamRepository.findByName("NonExistent")).thenReturn(null);

        Team result = teamService.getTeamByName("NonExistent");

        assertNull(result);
        verify(teamRepository, times(1)).findByName("NonExistent");
    }

    @Test
    @DisplayName("Should validate team attributes")
    void testTeamAttributes() {
        assertNotNull(testTeam.getId());
        assertEquals("India", testTeam.getName());
        assertEquals("India", testTeam.getCountry());
        assertNotNull(testTeam.getPlayers());
        assertNotNull(testTeam.getMatches());
    }

    @Test
    @DisplayName("Should return empty list when no teams found")
    void testGetAllTeamsEmpty() {
        when(teamRepository.findAll()).thenReturn(Arrays.asList());

        List<Team> teams = teamService.getAllTeams();

        assertNotNull(teams);
        assertTrue(teams.isEmpty());
        verify(teamRepository, times(1)).findAll();
    }
}

