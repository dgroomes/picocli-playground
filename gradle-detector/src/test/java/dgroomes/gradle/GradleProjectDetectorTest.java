package dgroomes.gradle;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GradleProjectDetectorTest {

    @Test
    void invalidSearchDepth() {
        Path path = Path.of(".");
        int illegalSearchDepth = -1;

        GradleDetectionResult result = GradleProjectDetector.search(path, illegalSearchDepth);

        assertThat(result).isExactlyInstanceOf(InvalidSearchParams.class);
    }
}
