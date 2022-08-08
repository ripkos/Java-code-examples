package zad1;

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
    public static void processDir(String dirName, String resultFileName) {
        Charset input = Charset.forName("cp1250");
        Charset output = StandardCharsets.UTF_8;
        List<Path> pathList = new ArrayList<>();
        try {
            Stream<Path> pathStream = Files.walk(Paths.get(dirName));
            pathList = pathStream.filter( Files :: isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileChannel toWrite = FileChannel.open(Paths.get(resultFileName),CREATE, TRUNCATE_EXISTING, WRITE);
            for (Path path : pathList) {
                try {
                    FileChannel inputChannel = FileChannel.open(path);

                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) inputChannel.size());

                    inputChannel.read(byteBuffer);
                    //System.out.println(inputChannel);
                    byteBuffer.flip();
                    CharBuffer toConvert = input.decode(byteBuffer);
                    toWrite.write(output.encode(toConvert));

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
