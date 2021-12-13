package dgroomes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Utility methods purpose-built for this codebase. The API and implementation of the methods bias towards the bespoke
 * needs of the codebase. This code does not aspire to be re-used by any other codebase.
 */
public class Util {

    private static final Logger log = LoggerFactory.getLogger(Util.class);

    /**
     * TODO i don't think this method is working out...
     * In the given directory, find files that match the criteria.
     *
     * @param directoryPath the path to a directory
     * @param criteria the criteria to match against
     * @return stream of paths that matched the criteria
     * @throws IllegalArgumentException if the given path does not resolve to a file that exists or the file is not a directory
     */
    public static Stream<Path> filesMatching(Path directoryPath, Predicate<Path> criteria) {
        var directoryFile = directoryPath.toAbsolutePath().normalize().toFile();
        if (!directoryFile.exists()) {
            throw new IllegalArgumentException("'%s' file does not exist".formatted(directoryPath));
        }
        if (!directoryFile.isDirectory()) {
            throw new IllegalArgumentException("'%s' is not a directory".formatted(directoryPath));
        }
        try {
            return Files.walk(directoryPath).filter(criteria);
        } catch (IOException e) {
            log.error("Something went wrong while reading walking the file at path '{}'", directoryPath, e);
            throw new IllegalStateException(e);
        }
    }
}
