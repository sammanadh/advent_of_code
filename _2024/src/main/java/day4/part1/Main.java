package day4.part1;

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
                    if(currentLine.charAt(j) == 'X') {
                        for(int[] direction : directions) {
                            boolean isValid = isValidDirection(allLines, i, j, direction, "XMAS");
                            if(isValid) totalCount++;
                        }
                    }
                }
            }

            System.out.println(totalCount);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean isValidDirection(List<String> grid, int row, int col, int[] direction, String stringToMatch) {
        int dr = direction[0];
        int dc = direction[1];

        for(int i = 0; i < stringToMatch.length(); i++) {
            int ir = row + (dr * i);
            int ic = col + (dc * i);

            if(ir < 0 || ic < 0 || ir >= grid.size() || ic >= grid.get(row).length() || grid.get(ir).charAt(ic) != stringToMatch.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}
