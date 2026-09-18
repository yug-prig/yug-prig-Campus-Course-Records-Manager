package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class RecursiveFileUtils {

    // A recursive method to calculate the size of a directory
    public static long calculateDirectorySize(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            return Files.size(path);
        }

        long size = 0;
        try (Stream<Path> stream = Files.list(path)) {
            for (Path p : stream.collect(java.util.stream.Collectors.toList())) {
                if (Files.isDirectory(p)) {
                    size += calculateDirectorySize(p); // Recursive call
                } else {
                    size += Files.size(p);
                }
            }
        }
        return size;
    }
}