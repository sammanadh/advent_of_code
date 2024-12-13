package day6.part1;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String inputFilePath = "./inputs/day6_input";

        try {
            List<List<Character>> allChars = new ArrayList<>();

            FileReader reader = new FileReader(inputFilePath);

            char[] directionalPositions = {'<', '^', '>', 'v'};
            int[][] changeForEachDirection = {{0, -1},  {-1, 0}, {0, 1}, {1, 0}};

            int currentDirectionIdx=-1;
            int[] position={};

            int c;
            List<Character> currentLine = new ArrayList<>();

            int lineCount = 0;

            while((c = reader.read()) != -1) {
                if(c == '\n') {
                    allChars.add(currentLine);
                    currentLine = new ArrayList<>();
                    lineCount++;
                } else {
                    char currentChar = (char)c;

                    currentLine.add(currentChar);

                    for(int i = 0; i < directionalPositions.length; i++) {
                        if(directionalPositions[i] == c) {
                            currentDirectionIdx = i;
                            position = new int[]{lineCount, currentLine.size() - 1 };
                        }
                    }
                }
            }

            // for the last remning values in currentLine
            if(currentLine.size() > 0) allChars.add(currentLine);

            if(currentDirectionIdx == -1 || position.length == 0) return;

            int maxRowIdx = allChars.size() - 1;
            int maxColIdx = allChars.get(0).size() - 1;

            Map<String, Boolean> notUnique = new HashMap<>();
            notUnique.put(position[0] + "-" + position[1], true);

            int totalSteps = 1; // counting from the first position of the guard
            while(true) {
                int[] changesForCurrentDirection = changeForEachDirection[currentDirectionIdx];
                int[] nextPosition = new int[]{position[0] + changesForCurrentDirection[0], position[1] + changesForCurrentDirection[1]};

                if(nextPosition[0] > maxRowIdx || nextPosition[1] > maxColIdx || nextPosition[0] < 0 || nextPosition[1] < 0) break;

                char charAtNextPosition = allChars.get(nextPosition[0]).get(nextPosition[1]);
                if(charAtNextPosition == '#') {
                    currentDirectionIdx = (currentDirectionIdx + 1) % directionalPositions.length;
                } else {
                    position = nextPosition;
                    String keyForMap = position[0] + "-" + position[1];
                    if(!notUnique.containsKey(keyForMap)) {
                        notUnique.put(keyForMap, true);
                        totalSteps++;
                    }
                }
            }

            System.out.println(totalSteps);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
