package dgroomes.gradle;

import java.io.File;
import java.util.List;
import java.util.Objects;

public record ProjectsFound(List<File> gradleProjects) implements GradleDetectionResult {

    public ProjectsFound {
        Objects.requireNonNull(gradleProjects);
    }
}
