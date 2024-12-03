package day1.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String inputFilePath = "./day1_input";

        List<String> leftList = new ArrayList<>();
        List<String> rightList = new ArrayList<>();

        try(Stream<String> lineSteam = Files.lines(Path.of(inputFilePath))) {
            lineSteam
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.split("   "))
                    .forEach(s -> {
                        leftList.add(s[0]);
                        rightList.add(s[1]);
                    });

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
}