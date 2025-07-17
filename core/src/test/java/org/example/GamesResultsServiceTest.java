package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

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

    @Test
    public void testGetHomeTeamsWithoutAwayGoalsNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> GamesResultsService.getHomeTeamsWithoutAwayGoals(nullGamesResults));
    }

    @Test
    public void testGetHomeTeamsWithoutAwayGoalsEmpty() {
        Set<Team> result = GamesResultsService.getHomeTeamsWithoutAwayGoals(emptyGamesResults);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetHomeTeamsWithoutAwayGoalsFull() {
        Set<Team> expected = new HashSet<>();
        expected.add(Team.PREDATORY_BEAVERS);
        expected.add(Team.ROOK);
        Set<Team> result = GamesResultsService.getHomeTeamsWithoutAwayGoals(fullGamesResults);
        Assertions.assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void testGetTeamsWithDefeatedTeamsNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> GamesResultsService.getTeamsWithDefeatedTeams(nullGamesResults));
    }

    @Test
    public void testGetTeamsWithDefeatedTeamsEmpty() {
        Map<Team, Set<Team>> result = GamesResultsService.getTeamsWithDefeatedTeams(emptyGamesResults);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetTeamsWithDefeatedTeamsFull() {
        Map<Team, Set<Team>> expected = Map.of(
                Team.PREDATORY_BEAVERS, Set.of(
                        Team.ROOK,
                        Team.RSMU
                ),
                Team.ROOK, Set.of(
                        Team.SFU,
                        Team.RSMU
                ),
                Team.SFU, Set.of(
                        Team.RSMU
                )
        );
        Map<Team, Set<Team>> result = GamesResultsService.getTeamsWithDefeatedTeams(fullGamesResults);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testGetGoalDifferenceNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> GamesResultsService.getGoalDifference(nullGamesResults));
    }

    @Test
    public void testGetGoalDifferenceEmpty() {
        Map<Team, Integer> result = GamesResultsService.getGoalDifference(emptyGamesResults);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetGoalDifferenceFull() {
        Map<Team, Integer> result = GamesResultsService.getGoalDifference(fullGamesResults);
        Map<Team, Integer> expected = Map.of(
                Team.SFU, -1,
                Team.RSMU, -6,
                Team.PREDATORY_BEAVERS, 5,
                Team.ROOK, 2
        );
        Assertions.assertEquals(expected, result);
    }


}