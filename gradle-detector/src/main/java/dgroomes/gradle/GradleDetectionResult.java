package dgroomes.gradle;

public sealed interface GradleDetectionResult permits InvalidSearchParams, NoProjectsFound, ProjectsFound {

}
