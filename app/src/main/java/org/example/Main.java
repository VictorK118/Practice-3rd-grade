package org.example;

/*
В файл записываются результаты чемпионата по футболу в формате:
Домашняя команда;Гостевая команда;счет(два числа через :)

Необходимо вывести следующую информацию:
- команды, которые заняли первые 3 места
- команду (команды), которая не пропустила в домашнее игре ни одного мяча
- для каждой команды, вывести список побежденных противников

В задаче должны использоваться элементы функционального программирования
Должно использоваться логгирование
 */

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static final String FILE_PATH = "app/src/main/resources/data/file.txt";
    private static final Logger logger = Logger.getLogger(GamesResultsFileController.class.getName());

    public static void main(String[] args) throws IOException {
        try {
            List<GameResult> gamesResults = GamesResultsFileController.readFromFile(FILE_PATH);

            System.out.println("Топ-3 команды");
            System.out.println(GamesResultsService.getTopThree(gamesResults));
            System.out.println("Команды, не пропустившие ни одного мяча в домашнем матче");
            System.out.println(GamesResultsService.getHomeTeamsWithoutAwayGoals(gamesResults));
            System.out.println("Список побеждённых команд для каждой команды ");
            System.out.println(GamesResultsService.getTeamsWithDefeatedTeams(gamesResults));

            GamesResultsFileController.writeToFile(FILE_PATH, gamesResults);
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            logger.log(Level.SEVERE, e.toString());
            System.out.println(e.getMessage());
        }
    }
}