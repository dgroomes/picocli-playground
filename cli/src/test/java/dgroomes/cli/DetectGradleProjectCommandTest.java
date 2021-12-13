package dgroomes.cli;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

class DetectGradleProjectCommandTest {

    @Test
    void noArguments() {
        int exitCode = new CommandLine(new DetectGradleProjectCommand()).execute();

        // picocli seems to return an exit code with value 2 when a required parameter is omitted.
        // Any non-zero exit code makes sense, so 2 is fine, but I'd like to find the documentation for it. There's lots
        // of good docs at https://picocli.info/ but I haven't read it all yet.
        assertThat(exitCode).isEqualTo(2);
    }
}
