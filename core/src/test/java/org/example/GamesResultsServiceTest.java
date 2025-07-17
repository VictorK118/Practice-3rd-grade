package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GamesResultsServiceTest {
    public static List<GameResult> generateFullGamesResults() {
        List<GameResult> results = new ArrayList<>();
        results.add(new GameResult(Team.PREDATORY_BEAVERS, Team.ROOK, 1, 0));
        results.add(new GameResult(Team.ROOK, Team.SFU, 3, 1));
        results.add(new GameResult(Team.SFU, Team.RSMU, 2, 1));
        results.add(new GameResult(Team.RSMU, Team.PREDATORY_BEAVERS, 1, 5));
        results.add(new GameResult(Team.ROOK, Team.RSMU, 1, 0));
        results.add(new GameResult(Team.SFU, Team.PREDATORY_BEAVERS, 1, 1));
        return results;
    }

    public static final List<GameResult> nullGamesResults = null;
    public static final List<GameResult> emptyGamesResults = new LinkedList<>();
    public static final List<GameResult> fullGamesResults = generateFullGamesResults();

    @Test
    public void testGetTopThreeNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> GamesResultsService.getTopThree(nullGamesResults));
    }

    @Test
    public void testGetTopThreeEmpty() {
        Set<Team> result = GamesResultsService.getTopThree(emptyGamesResults);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetTopThreeFull() {
        Set<Team> expected = new HashSet<>();
        expected.add(Team.PREDATORY_BEAVERS);
        expected.add(Team.ROOK);
        expected.add(Team.SFU);
        Set<Team> result = GamesResultsService.getTopThree(fullGamesResults);
        Assertions.assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }
}