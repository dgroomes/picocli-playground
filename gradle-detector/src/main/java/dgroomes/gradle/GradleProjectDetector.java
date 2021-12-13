package dgroomes.gradle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * NOT YET IMPLEMENTED
 *
 * Detect the presence of Gradle projects by searching a given directory and its sub-directories.
 */
public class GradleProjectDetector {

    private static final Logger log = LoggerFactory.getLogger(GradleProjectDetector.class);

    private final File searchDirectory;
    private final int searchDepth;

    private GradleProjectDetector(File searchDirectory, int searchDepth) {
        this.searchDirectory = searchDirectory;
        this.searchDepth = searchDepth;
    }

    /**
     * Search
     *
     * @param searchDirectory search for projects in this directory and its sub-directories
     * @param searchDepth     the max depth to search. A value of 0 means "only search the given directory and don't look
     *                        in sub-directories". A value of 1 means "search direct sub-directories". A value of 2 means
     *                        "search one layer deeper", etc.
     */
    public static GradleDetectionResult search(Path searchDirectory, int searchDepth) {
        var searchDir = searchDirectory.toAbsolutePath().normalize().toFile();
        if (!searchDir.exists()) {
            return new InvalidSearchParams("'%s' file does not exist".formatted(searchDir));
        }
        if (!searchDir.isDirectory()) {
            return new InvalidSearchParams("'%s' is not a directory".formatted(searchDir));
        }

        if (searchDepth < 0) {
            return new InvalidSearchParams("The search depth must be 0 or greater, but found %d".formatted(searchDepth));
        }
        var detector = new GradleProjectDetector(searchDir, searchDepth);

        List<File> found = detector.detectGradleProjects();
        if (found.isEmpty()) return new NoProjectsFound();

        return new ProjectsFound(found);
    }


    /**
     * Detect Gradle projects.
     * <p>
     * This uses some heuristics to identify if a directory defines a Gradle project.
     *
     * @return the directories of detected Gradle projects
     */
    private List<File> detectGradleProjects() {
        log.info("Searching for and detecting Gradle projects in the directory '{}' at a depth of '{}'", searchDirectory, searchDepth);
        throw new IllegalStateException("not implemented");
    }
}
