package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GamesResultsFileControllerTest {

    @Test
    void readFromFile_ShouldParseLinesCorrectly() throws IOException {
        String testPath = "test.txt";
        String testLine = "ROOK;KAZAN_YULBARS;2:1";

        BufferedReader mockReader = mock(BufferedReader.class);
        when(mockReader.readLine())
                .thenReturn(testLine)
                .thenReturn(null);

        try (MockedConstruction<FileReader> ignoredFileReader = mockConstruction(FileReader.class);
             MockedConstruction<BufferedReader> mockedBufferedReader = mockConstruction(
                     BufferedReader.class,
                     (mock, context) -> {
                         // Подменяем все созданные BufferedReader на наш мок
                         when(mock.readLine()).thenAnswer(inv -> mockReader.readLine());
                     }
             )) {

            List<GameResult> results = GamesResultsFileController.readFromFile(testPath);

            assertEquals(1, results.size());
            GameResult result = results.get(0);
            assertEquals(Team.ROOK, result.homeTeam());
            assertEquals(Team.KAZAN_YULBARS, result.awayTeam());
            assertEquals(2, result.homeTeamGoals());
            assertEquals(1, result.awayTeamGoals());

            verify(mockReader, times(2)).readLine();
        }
    }


    @Test
    void writeToFile_ShouldWriteCorrectFormat(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path testFile = tempDir.resolve("output.txt");
        List<GameResult> testResults = List.of(
                new GameResult(Team.ROOK, Team.KAZAN_YULBARS, 2, 1),
                new GameResult(Team.SPORT_UFU, Team.BERKUT, 0, 0)
        );

        GamesResultsFileController.writeToFile(testFile.toString(), testResults);

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(2, lines.size(), "Файл должен содержать 2 строки");
        assertEquals("ROOK;KAZAN_YULBARS;2:1", lines.get(0), "Неверный формат первой строки");
        assertEquals("SPORT_UFU;BERKUT;0:0", lines.get(1), "Неверный формат второй строки");
    }
}