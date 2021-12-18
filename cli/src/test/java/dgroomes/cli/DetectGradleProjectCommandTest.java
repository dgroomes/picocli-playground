package dgroomes.cli;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

class DetectGradleProjectCommandTest {

    /**
     * This is a "happy path" test case where the command is invoked without any arguments. The Gradle detection
     * command proceeds with default configuration values.
     */
    @Test
    void noArguments() {
        int exitCode = new CommandLine(new DetectGradleProjectCommand()).execute();

        assertThat(exitCode).isEqualTo(0);
    }

    @Test
    void misspelledArgument() {
        int exitCode = new CommandLine(new DetectGradleProjectCommand()).execute("--depthBAD_SPELLING", "1");

        // picocli seems to return an exit code with value 2 when a required parameter is omitted.
        // Any non-zero exit code makes sense, so 2 is fine, but I'd like to find the documentation for it. There's lots
        // of good docs at https://picocli.info/ but I haven't read it all yet.
        assertThat(exitCode).describedAs("""
                Expect an exit code of '2' as picocli's way of saying that the paremeters were unexpected.
                """).isEqualTo(2);
    }
}
