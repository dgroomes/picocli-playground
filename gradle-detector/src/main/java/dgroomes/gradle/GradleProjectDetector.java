package dgroomes.gradle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Detect the presence of Gradle projects by searching a given directory and its sub-directories.
 */
public class GradleProjectDetector {

    public static final Set<String> GRADLE_SETTINGS_FILES = Set.of("settings.gradle", "settings.gradle.kts");
    private static final Logger log = LoggerFactory.getLogger(GradleProjectDetector.class);

    private final File searchDirectory;
    private final int searchDepth;

    private GradleProjectDetector(File searchDirectory, int searchDepth) {
        this.searchDirectory = Objects.requireNonNull(searchDirectory);
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

        List<File> found;
        try {
            found = detector.detectGradleProjects();
        } catch (IOException e) {
            log.error("Something went wrong during the detection process", e);
            return new UnexpectedException(e);
        }
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
    private List<File> detectGradleProjects() throws IOException {
        log.info("Searching for and detecting Gradle projects in the directory '{}' at a depth of '{}'", searchDirectory, searchDepth);
        var searchPath = searchDirectory.toPath();
        try (var settingsFiles = Files.find(searchPath, searchDepth, this::isGradleSettingsFile)) {
            return settingsFiles.map(path -> path.getParent().toFile()).toList();
        }
    }

    /**
     * Is the file at the given path a Gradle settings file?
     * <p>
     * A Gradle settings file ('settings.gradle' or 'settings.gradle.kts') indicates that the current directory is a
     * Gradle project.
     */
    private boolean isGradleSettingsFile(Path path, BasicFileAttributes attributes) {
        var normalizedFile = path.normalize().toFile();
        var fileName = normalizedFile.getName().toLowerCase();

        if (GRADLE_SETTINGS_FILES.contains(fileName)) {
            log.trace("{} is a Gradle settings file ✅", normalizedFile);
            return true;
        }
        log.trace("{} is not a Gradle settings file ❌", normalizedFile);
        return false;
    }
}
