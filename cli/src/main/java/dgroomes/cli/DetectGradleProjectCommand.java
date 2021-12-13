package dgroomes.cli;

import dgroomes.gradle.GradleProjectDetector;
import dgroomes.gradle.InvalidSearchParams;
import dgroomes.gradle.NoProjectsFound;
import dgroomes.gradle.ProjectsFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * A picoli adapter over {@link GradleProjectDetector}.
 */
@Command
public class DetectGradleProjectCommand implements Callable<Integer> {

    private static final Logger log = LoggerFactory.getLogger(DetectGradleProjectCommand.class);
    public static final int EXIT_CODE_SUCCESS = 0;
    public static final int EXIT_CODE_FAILURE = 1;

    @Option(names = {"--depth"}, description = "Directory search depth")
    int depth = 0;

    @SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
    @Parameters(paramLabel = "<search-directory>", defaultValue = ".", description = "The directory to search for and detect Gradle projects")
    private String directory = ".";

    @Override
    public Integer call() {
        var result = GradleProjectDetector.search(Path.of(directory), depth);

        return switch (result) {
            case InvalidSearchParams inValidSearchParams -> handle(inValidSearchParams);
            case NoProjectsFound ignored -> handleNoProjectsFound();
            case ProjectsFound projectsFound -> handle(projectsFound);
        };
    }

    private int handle(InvalidSearchParams invalidSearchParams) {
        log.error("Invalid search parameters: {}", invalidSearchParams.errorMessage());
        return EXIT_CODE_FAILURE;
    }

    private int handleNoProjectsFound() {
        log.info("Did not find any Gradle projects");
        return EXIT_CODE_SUCCESS;
    }

    private int handle(ProjectsFound projectsFound) {
        List<File> gradleProjects = projectsFound.gradleProjects();
        log.info("Found {} Gradle projects:", gradleProjects.size());
        for (var gradleProject : gradleProjects) {
            log.info(gradleProject.toString());
        }
        return EXIT_CODE_SUCCESS;
    }
}
