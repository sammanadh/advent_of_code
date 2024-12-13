package day6.part2;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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

            int[] values = trackGuard(currentDirectionIdx, position, allChars, changeForEachDirection, directionalPositions);

            System.out.println(values[1]);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static int[] trackGuard(int currentDirectionIdx, int[] position, List<List<Character>> allChars, int[][] changeForEachDirection, char[] directionalPositions) {
        int maxRowIdx = allChars.size() - 1;
        int maxColIdx = allChars.get(0).size() - 1;

        Map<String, Boolean> notUnique = new HashMap<>();
        notUnique.put(position[0] + "-" + position[1], true);

        int totalSteps = 1; // counting from the first position of the guard
        int totalNewObstaclePlaces = 0;
        while(true) {
            int[] changesForCurrentDirection = changeForEachDirection[currentDirectionIdx];
            int[] nextPosition = new int[]{position[0] + changesForCurrentDirection[0], position[1] + changesForCurrentDirection[1]};

            if(nextPosition[0] > maxRowIdx || nextPosition[1] > maxColIdx || nextPosition[0] < 0 || nextPosition[1] < 0) break;

            char charAtNextPosition = allChars.get(nextPosition[0]).get(nextPosition[1]);
            if(charAtNextPosition == '#') {
                currentDirectionIdx = (currentDirectionIdx + 1) % directionalPositions.length;
            } else {
                // for each next position position, check if its possible to put an obstacle there
                boolean canBePlaced = checkIfLooped(currentDirectionIdx, position, nextPosition, allChars, changeForEachDirection, directionalPositions);
                if(canBePlaced) {
                    totalNewObstaclePlaces++;
                }

                position = nextPosition;
                String keyForMap = position[0] + "-" + position[1];
                if(!notUnique.containsKey(keyForMap)) {
                    notUnique.put(keyForMap, true);
                    totalSteps++;
                }
            }
        }

        return new int[]{totalSteps, totalNewObstaclePlaces};
    }

    public static boolean checkIfLooped(int currentDirectionIdx, int[] currentPositionO, int[] positionToCheck, List<List<Character>> allChars, int[][] changeForEachDirection, char[] directionalPositions) {
        List<List<Character>> allCharsCopy = deepCopy(allChars);

        int maxRowIdx = allCharsCopy.size() - 1;
        int maxColIdx = allCharsCopy.get(0).size() - 1;

        int[] currentPosition = currentPositionO.clone();
        int directionIdxToCheck = currentDirectionIdx;

        // adding obstacle to the position we want to check
        List<Character> row = allCharsCopy.get(positionToCheck[0]);
        row.set(positionToCheck[1], '#');
        allCharsCopy.set(positionToCheck[0], row);

        boolean firstEncountered = false;

        while(true) {
            int[] changesForCurrentDirection = changeForEachDirection[currentDirectionIdx];
            int[] nextPosition = new int[]{currentPosition[0] + changesForCurrentDirection[0], currentPosition[1] + changesForCurrentDirection[1]};

            if(nextPosition[0] > maxRowIdx || nextPosition[1] > maxColIdx || nextPosition[0] < 0 || nextPosition[1] < 0) return false;

            char charAtNextPosition = allCharsCopy.get(nextPosition[0]).get(nextPosition[1]);
            if(charAtNextPosition == '#') {
                boolean nextPositionIsPositionToCheck = Arrays.equals(nextPosition, positionToCheck);
                boolean directionIsSame = directionIdxToCheck == currentDirectionIdx;
                if(nextPositionIsPositionToCheck && directionIsSame) {
                    if(firstEncountered) return true;
                    else firstEncountered = true;
                };
                currentDirectionIdx = (currentDirectionIdx + 1) % directionalPositions.length;
            } else {
                // for each new currentPosition, check if its possible to put an obstacle there
                currentPosition = nextPosition;
            }
        }
    }

    public static <E> List<List<E>> deepCopy(List<List<E>> listToCopy) {
        // Deep copy
        List<List<E>> deepCopy = new ArrayList<>();
        for (List<E> innerList : listToCopy) {
            deepCopy.add(new ArrayList<>(innerList));
        }
        return deepCopy;
    }
}
