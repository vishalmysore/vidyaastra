package vishal.mysore.cricket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vishal.mysore.cricket.model.Player;
import vishal.mysore.cricket.model.Team;
import vishal.mysore.cricket.repository.PlayerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Cricket Player Service Tests")
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player testPlayer;
    private Team testTeam;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testTeam = new Team();
        testTeam.setId(UUID.randomUUID());
        testTeam.setName("India");
        testTeam.setCountry("India");

        testPlayer = new Player();
        testPlayer.setId(UUID.randomUUID());
        testPlayer.setName("Virat Kohli");
        testPlayer.setCountry("India");
        testPlayer.setRole("batsman");
        testPlayer.setJerseyNumber(18);
        testPlayer.setTeam(testTeam);
    }

    @Test
    @DisplayName("Should save player with valid team")
    void testSavePlayerWithValidTeam() {
        when(playerRepository.save(any(Player.class))).thenReturn(testPlayer);

        Player savedPlayer = playerService.savePlayer(testPlayer);

        assertNotNull(savedPlayer);
        assertEquals("Virat Kohli", savedPlayer.getName());
        assertEquals("batsman", savedPlayer.getRole());
        verify(playerRepository, times(1)).save(testPlayer);
    }

    @Test
    @DisplayName("Should throw exception when saving player without team")
    void testSavePlayerWithoutTeam() {
        Player playerWithoutTeam = new Player();
        playerWithoutTeam.setName("John Doe");
        playerWithoutTeam.setTeam(null);

        assertThrows(IllegalArgumentException.class, () -> playerService.savePlayer(playerWithoutTeam));
        verify(playerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should retrieve all players")
    void testGetAllPlayers() {
        Player player2 = new Player();
        player2.setId(UUID.randomUUID());
        player2.setName("Rohit Sharma");
        player2.setCountry("India");
        player2.setRole("batsman");
        player2.setJerseyNumber(45);
        player2.setTeam(testTeam);

        List<Player> players = Arrays.asList(testPlayer, player2);
        when(playerRepository.findAll()).thenReturn(players);

        List<Player> retrievedPlayers = playerService.getAllPlayers();

        assertNotNull(retrievedPlayers);
        assertEquals(2, retrievedPlayers.size());
        assertEquals("Virat Kohli", retrievedPlayers.get(0).getName());
        assertEquals("Rohit Sharma", retrievedPlayers.get(1).getName());
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get players by team")
    void testGetPlayersByTeam() {
        List<Player> players = Arrays.asList(testPlayer);
        when(playerRepository.findByTeam(testTeam)).thenReturn(players);

        List<Player> result = playerService.getPlayersByTeam(testTeam);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("India", result.get(0).getTeam().getName());
        verify(playerRepository, times(1)).findByTeam(testTeam);
    }

    @Test
    @DisplayName("Should get players by country")
    void testGetPlayersByCountry() {
        List<Player> players = Arrays.asList(testPlayer);
        when(playerRepository.findPlayersByCountry("India")).thenReturn(players);

        List<Player> result = playerService.getPlayersByCountry("India");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("India", result.get(0).getCountry());
        verify(playerRepository, times(1)).findPlayersByCountry("India");
    }

    @Test
    @DisplayName("Should get Man of the Match players")
    void testGetManOfTheMatchPlayers() {
        List<Player> players = Arrays.asList(testPlayer);
        when(playerRepository.findPlayersWithManOfTheMatch()).thenReturn(players);

        List<Player> result = playerService.getManOfTheMatchPlayers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(playerRepository, times(1)).findPlayersWithManOfTheMatch();
    }

    @Test
    @DisplayName("Should return empty list when no players found")
    void testGetAllPlayersEmpty() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList());

        List<Player> players = playerService.getAllPlayers();

        assertNotNull(players);
        assertTrue(players.isEmpty());
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should validate player attributes")
    void testPlayerAttributes() {
        assertNotNull(testPlayer.getId());
        assertEquals("Virat Kohli", testPlayer.getName());
        assertEquals("India", testPlayer.getCountry());
        assertEquals("batsman", testPlayer.getRole());
        assertEquals(18, testPlayer.getJerseyNumber());
        assertNotNull(testPlayer.getTeam());
    }
}

