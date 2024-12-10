package day4.part2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String args[]) {
        String inputFilePath = "./inputs/day4_input";
        try {
            List<String> allLines = Files.readAllLines(Path.of(inputFilePath));

            List<int[]> directions = new ArrayList<int[]>();
            // Generate all the directions
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    directions.add(new int[]{i, j});
                }
            }

            int totalCount = 0;
            for(int i = 0; i < allLines.size(); i++) {
                String currentLine = allLines.get(i);
                for(int j = 0; j < currentLine.length(); j++) {
                    if(currentLine.charAt(j) == 'A') {
                        boolean isValid = isCrossed(allLines, i, j);
                        if(isValid) totalCount++;
                    }
                }
            }

            System.out.println(totalCount);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean isCrossed(List<String> grid, int row, int col) {
        if(!(row > 0 && row < grid.size()-1 && col > 0 && col < grid.get(row).length()-1)) return false;

        String d1 = "" + grid.get(row-1).charAt(col-1) + grid.get(row+1).charAt(col+1);
        String d2 = "" + grid.get(row-1).charAt(col+1) + grid.get(row+1).charAt(col-1);

        List<String> possibleCombinations = new ArrayList<>();
        possibleCombinations.add("SM");
        possibleCombinations.add("MS");

        if(possibleCombinations.contains(d1) && possibleCombinations.contains(d2)) {
            return true;
        }

        return false;
    }
}
