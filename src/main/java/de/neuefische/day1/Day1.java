package de.neuefische.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Day1 {

    static Map<String, Integer> spelledDigits = Map.of(
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9
    );

    public static void main(String[] args) {
        int sum1 = 0;
        int sum2 = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/de/neuefische/day1/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int[] digits1 = getFirstAndLastDigits(line, false);
                sum1 += digits1[0] * 10 + digits1[1];

                int[] digits2 = getFirstAndLastDigits(line, true);
                sum2 += digits2[0] * 10 + digits2[1];
            }

            System.out.println("Part 1:" + sum1);
            System.out.println("Part 2:" + sum2);
        } catch (IOException e) {
            System.err.println("Error occurred while reading the file: " + e.getMessage());
        }
    }


    public static int[] getFirstAndLastDigits(String line, boolean readSpelledDigits) {
        StringBuilder word = new StringBuilder();
        int firstDigit = -1;
        int lastDigit = -1;

        for (int i = 0; i < line.length(); i++) {
            int digit = -1;
            char c = line.charAt(i);
            word.append(c);

            if (c >= '0' && c <= '9') {
                digit = c - '0';
            } else if (readSpelledDigits) {
                digit = getSpelledDigit(word.toString());
            }

            if (digit == -1) continue;

            if (firstDigit == -1) {
                firstDigit = digit;
            }

            lastDigit = digit;
        }

        return new int[]{firstDigit, lastDigit};
    }

    public static int getSpelledDigit(String str) {
        int digit = -1;

        for (Map.Entry<String, Integer> entry : spelledDigits.entrySet()) {
            String key = entry.getKey();
            String subbed = str.substring(Math.max(0, str.length() - key.length()));

            if (key.length() <= str.length() && subbed.equals(key)) {
                digit = entry.getValue();
                break;
            }
        }


        return digit;
    }
}