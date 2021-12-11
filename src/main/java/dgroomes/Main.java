package dgroomes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Program invoked with {}", (Object) args);

        int exitCode = new CommandLine(new DetectGradleProjectCommand()).execute(args);
        System.exit(exitCode);
    }
}
