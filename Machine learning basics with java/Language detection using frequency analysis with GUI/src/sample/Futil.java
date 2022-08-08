package sample;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.*;

public class Futil {
    public static List<DataModel> processDirs(String dirName) throws IOException {
        List<DataModel> data = new ArrayList<>();
        List<Path> dirPathList = new ArrayList<>();
        Stream<Path> pathStream = Files.walk(Paths.get("src/sample/data/"+dirName));
        dirPathList = pathStream.filter( Files :: isDirectory).collect(Collectors.toList());
        dirPathList.removeIf(a -> a.getFileName().toString().equals(dirName));
        for (Path dirPath : dirPathList) {
            List<Path> filePathList;
            Stream<Path> filePathStream = Files.walk(dirPath);
            filePathList = filePathStream.filter(Files :: isRegularFile).collect(Collectors.toList());
            List<double[]> proportions = new ArrayList<>();
            for (Path filePath : filePathList) {
                StringBuilder stringBuilder = new StringBuilder();
                List<String> strings = Files.readAllLines(filePath, StandardCharsets.ISO_8859_1);
                for (String tmp : strings) {
                    stringBuilder.append(tmp.toLowerCase());
                }
                String content = stringBuilder.toString().replaceAll("[^a-z]+","");
                proportions.add(getProportions(countChars(content)));
            }
            data.add(new DataModel(dirPath.getFileName().toString(),proportions));
            // Trenowanie perceptronu
        }
        return data;
    }

    public static int[] countChars(String string) {
        int [] counts = new int[27];
        for(char c : string.toCharArray()) {
            counts[c-'a']++;
        }
        counts[26]=string.length();
        return counts;
    }
    public static double[] getProportions(int[] counts) {
        double [] prop = new double[26];
        for (int i = 0; i < prop.length ; i++) {
            prop[i]=(float)counts[i]/(float)counts[26];
        }
        return prop;
    }


    public static String cleanTextContent(String text)
    {
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");

        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");
        text=text.toLowerCase().trim();
        text = text.replaceAll("[^a-z]+","");

        return text;
    }
}
