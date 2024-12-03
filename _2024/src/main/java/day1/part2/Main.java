package day1.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String inputFilePath = "./day1_input";

        List<String> leftList = new ArrayList<>();
        List<String> rightList = new ArrayList<>();

        Map<String, Integer> occurances = new HashMap<>();

        try(Stream<String> lineSteam = Files.lines(Path.of(inputFilePath))) {
            lineSteam
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.split("   "))
                    .forEach(s -> {
                        String leftItem = s[0];
                        String rightItem = s[1];

                        leftList.add(leftItem);
                        rightList.add(rightItem);

                        if(occurances.containsKey(rightItem)) {
                            occurances.put(rightItem, occurances.get(rightItem) + 1);
                        } else {
                            occurances.put(rightItem, 1);
                        }
                    });

            int similarityScore = calculateSimilarityScore(leftList, rightList, occurances);
            System.out.println("Similarity Score: " + similarityScore);

            Comparator<String> locationListComparator = (s1, s2) -> Integer.parseInt(s1) - Integer.parseInt(s2);

            leftList.sort(locationListComparator);
            rightList.sort(locationListComparator);

            Integer totalDiff = 0;

            for(int i = 0; i < leftList.size(); i++) {
                Integer leftLocation = Integer.parseInt(leftList.get(i));
                Integer rightLocation = Integer.parseInt(rightList.get(i));
                Integer diff = Math.abs(leftLocation - rightLocation);
                totalDiff += diff;
            }

            System.out.println(totalDiff);
        } catch (IOException exception) {}
    }

    public static int calculateSimilarityScore(List<String> leftList, List<String> rightList, Map<String, Integer> rightOccurances) {
        int score = 0;

        for(int i = 0; i < leftList.size(); i++) {
            String currentLeftItem = leftList.get(i);
            if(rightOccurances.containsKey(currentLeftItem)) {
                score += rightOccurances.get(currentLeftItem) * Integer.parseInt(currentLeftItem);
            }
        }

        return score;
    }
}