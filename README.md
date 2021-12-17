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

Tip: follow the below instructions to install the CLI on the system.

1. Build the program distribution
   * `./gradlew cli:installDist`
1. Symlink the launcher script to somewhere on the `PATH`
   * `ln -sf "$PWD/cli/build/install/cli/bin/cli" /usr/local/bin/detect-gradle`
1. Run it!
   * `detect-gradle .`
1. Change directories. Run it from anywhere!
   * `cd ~/repos; detect-gradle . --depth 2`

## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* DONE Integrate picocli
* ABANDONED (this doesn't appear to actually do code gen. I see `build/generated/` directory but it contains no files,
  just empty directories. Plus when I make a change in `DetectGradleProjectCommand` to use an Option in an invalid way,
  specifically `final int depth = 0;` I don't get an error at compile time, only runtime. The premise of the annotation
  processor is to catch the issue at compile time). Use picocli's [code-gen](https://picocli.info/#_annotation_processor). I am generally skeptical of using code gen in
  projects of significance but I'm happy to learn it, especially because picocli recommends it.
  * Also see the [picocli-codegen module README](https://github.com/remkop/picocli/tree/master/picocli-codegen)
* Generate auto completion scripts
* DONE Include instructions to install the CLI
* Implement CLI help output (e.g. the `--help` option output)
* DONE Implement something at least vaguely useful, like "detect a Gradle project" and take options for "how many
  levels deep to check in the directory structure (depth)"

## Reference

* [picocli docs](https://picocli.info/)
