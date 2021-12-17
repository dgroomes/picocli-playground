package dgroomes.cli;

import dgroomes.gradle.*;
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

    // Purposely write Java code that is non-functional for picocli at runtime. The annotation processor should complain
    // about this. If it doesn't then, the issue will be caught at runtime by picocli with `Constant (final) primitive and String fields like final int dgroomes.cli.DetectGradleProjectCommand.depth cannot be used as an @Option`
    @Option(names = {"--depth"}, defaultValue = "0", description = "Directory search depth")
    final int depth = 0;

    @SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
    @Parameters(paramLabel = "<search-directory>", description = "The directory to search for and detect Gradle projects")
    private String directory;

    @Override
    public Integer call() {
        var result = GradleProjectDetector.search(Path.of(directory), depth);

        return switch (result) {
            case InvalidSearchParams inValidSearchParams -> handle(inValidSearchParams);
            case UnexpectedException unexpectedException -> handle(unexpectedException);
            case NoProjectsFound ignored -> handleNoProjectsFound();
            case ProjectsFound projectsFound -> handle(projectsFound);
        };
    }

    private int handle(InvalidSearchParams invalidSearchParams) {
        log.error("Invalid search parameters: {}", invalidSearchParams.errorMessage());
        return EXIT_CODE_FAILURE;
    }

    private int handle(UnexpectedException unexpectedException) {
        log.error("Something went wrong during the detection process: {}", unexpectedException.exception().getMessage());
        return EXIT_CODE_FAILURE;
    }

    private int handleNoProjectsFound() {
        log.info("Did not find any Gradle projects");
        return EXIT_CODE_SUCCESS;
    }

    private int handle(ProjectsFound projectsFound) {
        List<File> gradleProjects = projectsFound.gradleProjects();
        int size = gradleProjects.size();
        if (size == 1) {
            log.info("Found 1 Gradle project:");
        } else {
            log.info("Found {} Gradle projects:", size);
        }
        for (var gradleProject : gradleProjects) {
            log.info(gradleProject.toString());
        }
        return EXIT_CODE_SUCCESS;
    }
}
