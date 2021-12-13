package dgroomes.gradle;

/**
 * @param errorMessage a description for why the search params are invalid.
 */
public record InvalidSearchParams(String errorMessage) implements GradleDetectionResult {

}
