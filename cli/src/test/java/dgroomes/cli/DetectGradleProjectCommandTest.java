package dgroomes.cli;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

class DetectGradleProjectCommandTest {

    @Test
    void noArguments() {
        int exitCode = new CommandLine(new DetectGradleProjectCommand()).execute();

        assertThat(exitCode).isEqualTo(1);
    }
}
