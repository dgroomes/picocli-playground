package dgroomes.gradle;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GradleProjectDetectorTest {

    /**
     * Search in the project root directory. The directory should be identified as a Gradle project.
     */
    @Test
    void search() {
        // Arrange
        var path = projectRootDir();
        int searchDepth = 0;

        // Act
        GradleDetectionResult result = GradleProjectDetector.search(path, searchDepth);

        // Assert
        ProjectsFound projectsFound = switch (result) {
            case ProjectsFound cast -> cast;
            default -> throw new AssertionFailedError("Result was not the expected type. Expected %s but found %s".formatted(ProjectsFound.class, result.getClass()));
        };
        assertThat(projectsFound.gradleProjects()).hasSize(1);
    }

    /**
     * An invalid search depth value should be forbidden.
     */
    @Test
    void invalidSearchDepth() {
        // Arrange
        var path = projectRootDir();
        int illegalSearchDepth = -1;

        // Act
        GradleDetectionResult result = GradleProjectDetector.search(path, illegalSearchDepth);

        // Assert
        InvalidSearchParams invalidSearchParams = switch (result) {
            case InvalidSearchParams cast -> cast;
            default -> throw new AssertionFailedError("Result was not the expected type. Expected %s but found %s".formatted(InvalidSearchParams.class, result.getClass()));
        };
        assertThat(invalidSearchParams.errorMessage()).containsIgnoringCase("must be 0 or greater");
    }

    /**
     * A search directory that doesn't exist should be forbidden.
     */
    @Test
    void nonExistentSearchDirectory() {
        // Arrange
        var path = Path.of("non-existent-directory");
        int searchDepth = 0;

        // Act
        GradleDetectionResult result = GradleProjectDetector.search(path, searchDepth);

        // Assert
        InvalidSearchParams invalidSearchParams = switch (result) {
            case InvalidSearchParams cast -> cast;
            default -> throw new AssertionFailedError("Result was not the expected type. Expected %s but found %s".formatted(InvalidSearchParams.class, result.getClass()));
        };
        assertThat(invalidSearchParams.errorMessage()).containsIgnoringCase("does not exist");
    }

    /**
     * Get the project root dir.
     */
    private Path projectRootDir() {
        // JUnit test suites are usually executed from the directory of the given sub-project and not the root project.
        // The root project should be one level above the "cli/" directory.
        return Path.of("..");
    }
}
