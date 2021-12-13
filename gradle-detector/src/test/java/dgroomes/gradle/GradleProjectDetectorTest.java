package dgroomes.gradle;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GradleProjectDetectorTest {

    /**
     * Search in the project root directory. The directory should be identified as a Gradle project.
     */
    @Test
    @Disabled("Not yet implemented")
    void search() {
        // Arrange
        var path = Path.of(".");
        int illegalSearchDepth = 1;

        // Act
        GradleDetectionResult result = GradleProjectDetector.search(path, illegalSearchDepth);

        // Assert
        assertThat(result).isExactlyInstanceOf(ProjectsFound.class);
        ProjectsFound projectsFound = switch (result) {
            case ProjectsFound cast -> cast;
            default -> throw new AssertionFailedError("Result was not the expected type", ProjectsFound.class, result.getClass());
        };
        assertThat(projectsFound.gradleProjects()).hasSize(1);
    }

    /**
     * An invalid search depth value should be forbidden.
     */
    @Test
    void invalidSearchDepth() {
        // Arrange
        var path = Path.of(".");
        int illegalSearchDepth = -1;

        // Act
        GradleDetectionResult result = GradleProjectDetector.search(path, illegalSearchDepth);

        // Assert
        InvalidSearchParams invalidSearchParams = switch (result) {
            case InvalidSearchParams cast -> cast;
            default -> throw new AssertionFailedError("Result was not the expected type", InvalidSearchParams.class, result.getClass());
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
        int illegalSearchDepth = 1;

        // Act
        GradleDetectionResult result = GradleProjectDetector.search(path, illegalSearchDepth);

        // Assert
        InvalidSearchParams invalidSearchParams = switch (result) {
            case InvalidSearchParams cast -> cast;
            default -> throw new AssertionFailedError("Result was not the expected type", InvalidSearchParams.class, result.getClass());
        };
        assertThat(invalidSearchParams.errorMessage()).containsIgnoringCase("does not exist");
    }
}
