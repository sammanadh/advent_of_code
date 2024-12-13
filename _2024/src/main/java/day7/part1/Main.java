package day7.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "./inputs/day7_input";

        try (Stream<String> lines = Files.lines(Path.of(inputFilePath))) {
            long totalCount = lines
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.split(": "))
                    .map(lineAsArr -> {
                        String result = lineAsArr[0];

                        String operands = lineAsArr[1];
                        String[] operandsAsArr = operands.split(" ");

                        List<String> allValues = new ArrayList<>();
                        allValues.add(result);
                        Collections.addAll(allValues, operandsAsArr);

                        return allValues;
                    })
                    .map(lineAsList -> lineAsList.stream().map(Long::parseLong).collect(Collectors.toList()))
                    .filter(lineAsList -> {
                        boolean isValid = helper(lineAsList.get(0), lineAsList, lineAsList.get(1), 2);
                        return isValid;
                    })
                    .reduce(0l, (acc, next) -> acc + next.getFirst(), Long::sum);

            System.out.println(totalCount);
        } catch (IOException e) {

        }
    }

    public static boolean helper(long desiredResult, List<Long> operands, long currentResult, int index) {
        if (index == operands.size()) {
            return currentResult == desiredResult;
        }

        long nextVal = operands.get(index);
        return helper(desiredResult, operands, currentResult + nextVal, index + 1) ||
                helper(desiredResult, operands, Long.parseLong("" + currentResult + nextVal), index + 1) ||
                helper(desiredResult, operands, currentResult * nextVal, index + 1);
    }
}
