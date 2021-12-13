# picocli-playground

NOT YET FULLY IMPLEMENTED

📚 Learning and exploring picocli.

> picocli - a mighty tiny command line interface
>
> -- <cite>https://picocli.info/</cite>

## Instructions

Follow the below instructions to build and run the program.

1. Use Java 17
2. Build the program and run the tests:
   * `./gradlew test`
3. Build and run the program: 
   * `./gradlew cli:run`
4. Run the program but with some command-line arguments this time:
   * `./gradlew cli:run --args '.. --depth 1'`

## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* DONE Integrate picocli
* Use picocli's [code-gen](https://picocli.info/#_annotation_processor). I am generally skeptical of using code gen in
  projects of significance but I'm happy to learn it, especially because picocli recommends it.
* Generate auto completion scripts
* Include instructions that eject from Gradle
* Implement CLI help output (e.g. the `--help` option output)
* IN PROGRESS Implement something at least vaguely useful, like "detect a Gradle project" and take options for "how many levels deep
  to check in the directory structure (depth)" 

## Reference

* [picocli docs](https://picocli.info/)
