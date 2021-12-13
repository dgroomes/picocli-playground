# picocli-playground

📚 Learning and exploring picocli.

> picocli - a mighty tiny command line interface
>
> -- <cite>https://picocli.info/</cite>

## Instructions

Follow the below instructions to build and run the program.

1. Use Java 17
2. Build the program and run the tests:
    * `./gradlew test`
3. Build and run the program with the required and optional command-line arguments:
    * `./gradlew cli:run --args ".. --depth 0"`
4. Next, try another directory:
    * For example, I place my own repositories in `~/repos/personal`. So, I can use the CLI to search for Gradle
      projects in that directory with the following command.
    * `./gradlew cli:run --args "$HOME/repos/personal --depth 1"`

## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* DONE Integrate picocli
* Use picocli's [code-gen](https://picocli.info/#_annotation_processor). I am generally skeptical of using code gen in
  projects of significance but I'm happy to learn it, especially because picocli recommends it.
* Generate auto completion scripts
* Include instructions that eject from Gradle
* Implement CLI help output (e.g. the `--help` option output)
* DONE Implement something at least vaguely useful, like "detect a Gradle project" and take options for "how many
  levels deep to check in the directory structure (depth)"

## Reference

* [picocli docs](https://picocli.info/)
