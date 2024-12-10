package day5.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String args[]) {
        String inputFilePath = "./inputs/day5_input";
        try {
            List<String> allLines = Files.readAllLines(Path.of(inputFilePath));

            HashMap<String, List<String>> orderMap = new HashMap<>();

            int totalSum = 0;

            for (int i = 0; i < allLines.size(); i++) {
                String currentLine = allLines.get(i);

                if(currentLine.isEmpty()) continue;

                if(currentLine.contains("|")) {
                    String[] nums = currentLine.split("\\|");
                    String comesBefore = nums[0];
                    String comesAfter = nums[1];
                    List<String> comesAfterList;
                    if(orderMap.containsKey(comesBefore)) {
                        comesAfterList = orderMap.get(comesBefore);
                    } else {
                        comesAfterList = new ArrayList<>();
                    }
                    comesAfterList.add(comesAfter);
                    orderMap.put(comesBefore, comesAfterList);
                } else {
                    String[] updatesList = currentLine.split(",");

                    boolean isInOrder = true;

                    int j = 0;
                    // check if updates are in currect order
                    while(j < updatesList.length) {
                        int k = j + 1;
                        while(k < updatesList.length) {
                            String shouldBeBefore = updatesList[j];
                            String shouldBeAfter = updatesList[k];

                            if(orderMap.containsKey(shouldBeAfter) && orderMap.get(shouldBeAfter).contains(shouldBeBefore)) {
                                isInOrder = false;

                                // fix the ordering
                                updatesList[j] = shouldBeAfter;
                                updatesList[k] = shouldBeBefore;
                            } else {
                                k++;
                            }
                        }
                        j++;
                    }

                    if(!isInOrder) {
                        int midIndex = (int)Math.floor((updatesList.length-1) / 2);
                        int midNum = Integer.parseInt(updatesList[midIndex]);
                        totalSum += midNum;
                    }
                }
            }

            System.out.println(totalSum);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
