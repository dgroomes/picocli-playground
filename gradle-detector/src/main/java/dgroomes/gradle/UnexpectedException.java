package dgroomes.gradle;

public record UnexpectedException(Exception exception) implements GradleDetectionResult {
}
