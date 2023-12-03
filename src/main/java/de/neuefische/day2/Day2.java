package de.neuefische.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    private static final int RED_CUBES = 12;
    private static final int GREEN_CUBES = 13;
    private static final int BLUE_CUBES = 14;

    public static void main(String[] args) {
        ArrayList<Integer> gameIds = new ArrayList<>();
        String filePath = "src/main/java/de/neuefische/day2/input.txt"; // Path to your text file

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                gameIds.addAll(processGameData(line));
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading the file: " + e.getMessage());
        }

        int sum = gameIds.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum of IDs of possible games: " + sum);
    }

    private static ArrayList<Integer> processGameData(String gameData) {
        ArrayList<Integer> possibleGames = new ArrayList<>();
        Pattern pattern = Pattern.compile("Game (\\d+): (.+)");
        Matcher matcher = pattern.matcher(gameData);

        if (matcher.find()) {
            int gameId = Integer.parseInt(matcher.group(1));
            String[] subsets = matcher.group(2).split("; ");
            if (isGamePossible(subsets)) {
                possibleGames.add(gameId);
            }
        }

        return possibleGames;
    }

    private static boolean isGamePossible(String[] subsets) {
        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;

        for (String subset : subsets) {
            int red = countCubes(subset, "red");
            int green = countCubes(subset, "green");
            int blue = countCubes(subset, "blue");

            maxRed = Math.max(maxRed, red);
            maxGreen = Math.max(maxGreen, green);
            maxBlue = Math.max(maxBlue, blue);

            if (maxRed > RED_CUBES || maxGreen > GREEN_CUBES || maxBlue > BLUE_CUBES) {
                return false;
            }
        }

        return true;
    }

    private static int countCubes(String subset, String color) {
        Pattern pattern = Pattern.compile("(\\d+) " + color);
        Matcher matcher = pattern.matcher(subset);
        int count = 0;

        while (matcher.find()) {
            count += Integer.parseInt(matcher.group(1));
        }

        return count;
    }
}

class Day2Part2 {
    public static void main(String[] args) {
        String filePath = "src/main/java/de/neuefische/day2/input.txt"; // Path to your text file
        long totalPower = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                totalPower += calculateGamePower(line);
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading the file: " + e.getMessage());
        }

        System.out.println("Total power of all games: " + totalPower);
    }

    private static long calculateGamePower(String gameData) {
        Pattern pattern = Pattern.compile("Game (\\d+): (.+)");
        Matcher matcher = pattern.matcher(gameData);

        if (matcher.find()) {
            String[] subsets = matcher.group(2).split("; ");
            int maxRed = 0, maxGreen = 0, maxBlue = 0;

            for (String subset : subsets) {
                int red = countCubes(subset, "red");
                int green = countCubes(subset, "green");
                int blue = countCubes(subset, "blue");

                maxRed = Math.max(maxRed, red);
                maxGreen = Math.max(maxGreen, green);
                maxBlue = Math.max(maxBlue, blue);
            }

            return (long) maxRed * maxGreen * maxBlue;
        }

        return 0;
    }

    private static int countCubes(String subset, String color) {
        Pattern pattern = Pattern.compile("(\\d+) " + color);
        Matcher matcher = pattern.matcher(subset);
        int count = 0;

        while (matcher.find()) {
            count += Integer.parseInt(matcher.group(1));
        }

        return count;
    }
}
