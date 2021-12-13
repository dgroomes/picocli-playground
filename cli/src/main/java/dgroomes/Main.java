package dgroomes;

import dgroomes.cli.DetectGradleProjectCommand;
import picocli.CommandLine;

public class Main {

    public static void main(String... args) {
        int exitCode = new CommandLine(new DetectGradleProjectCommand()).execute(args);
        System.exit(exitCode);
    }
}
