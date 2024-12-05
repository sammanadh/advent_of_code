package day3.part1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {
    public static void main(String args[]) {
        String inputFilePath = "./inputs/day3_input";
        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";

        try {
            String lines = Files.readString(Path.of(inputFilePath));

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(lines);

            List<String> matches = new ArrayList<>();
            while(matcher.find()) {
                matches.add(matcher.group());
            }

            int result = 0;
            for(String match : matches) {
                // remove the mul( and last )
                String paramsAsCommaSeperated = match.substring(4, match.length() - 1);
                String[] paramsAsStringArray = paramsAsCommaSeperated.split(",");
                result += Integer.parseInt(paramsAsStringArray[0]) * Integer.parseInt(paramsAsStringArray[1]);
            }

            System.out.println(result);
        } catch (Exception e) {
        }
    }
}
