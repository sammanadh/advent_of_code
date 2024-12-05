package day3.part2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String args[]) {
        String inputFilePath = "./inputs/day3_input";
        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)";

        try {
            String lines = Files.readString(Path.of(inputFilePath));

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(lines);

            List<String> matches = new ArrayList<>();
            while(matcher.find()) {
                matches.add(matcher.group());
            }

            boolean shouldDo = true;
            int result = 0;
            for(String match : matches) {
                if(match.startsWith("mul")) {
                    if(!shouldDo) continue;
                    String paramsAsCommaSeperated = match.substring(4, match.length() - 1);
                    String[] paramsAsStringArray = paramsAsCommaSeperated.split(",");
                    result += Integer.parseInt(paramsAsStringArray[0]) * Integer.parseInt(paramsAsStringArray[1]);
                } else if(match.startsWith("don't")) {
                    shouldDo = false;
                } else {
                    shouldDo = true;
                }
            }

            System.out.println(result);
        } catch (Exception e) {
        }
    }
}
