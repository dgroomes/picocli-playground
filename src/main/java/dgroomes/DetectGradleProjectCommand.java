package dgroomes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Detect the presence of Gradle projects by searching a given directory and its sub-directories.
 */
@Command
public class DetectGradleProjectCommand implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(DetectGradleProjectCommand.class);

    @Option(names = {"--depth"}, description = "Directory search depth")
    int depth = 1;

    @SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
    @Parameters(paramLabel = "<search-directory>", defaultValue = ".", description = "The directory to search for and detect Gradle projects")
    private String directory = ".";

    @Override
    public void run() {
        log.info("Searching for and detecting Gradle projects in the directory '{}' at a depth of '{}'", directory, depth);
        log.warn("NOT YET IMPLEMENTED");
    }
}
