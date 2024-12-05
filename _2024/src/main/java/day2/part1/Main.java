package day2.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

enum Order {
    INCREASING, DECREASING
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String inputFilePath = "./inputs/day2_input";

        try(Stream<String> lines = Files.lines(Path.of(inputFilePath))) {
            long safeReportsCount = lines
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.split(" "))
                    .filter(levels -> {
                        Order order = null;
                        for(int i = 0; i < levels.length-1; i++) {
                            int leftLevel = Integer.parseInt(levels[i]);
                            int rightLevel = Integer.parseInt(levels[i+1]);
                            int diff = leftLevel - rightLevel;

                            if(Math.abs(diff) < 1 || Math.abs(diff) > 3) return false;

                            Order currentOrder = diff < 0 ? Order.INCREASING : Order.DECREASING;
                            if(order == null) order = currentOrder;
                            else if(order != currentOrder) return false;
                        }
                        return true;
                    })
                    .count();

            System.out.println(safeReportsCount);
        } catch (IOException e) {}
    }
}