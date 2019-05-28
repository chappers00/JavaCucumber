# JavaCucumber

This is a template test harness project with [Cucumber](https://cucumber.io/) and [Selenium](https://www.seleniumhq.org/)

[![Build Status](https://dev.azure.com/ecsdigital/cucumber-container-ci/_apis/build/status/ecsdigital.JavaCucumber?branchName=master)](https://dev.azure.com/ecsdigital/cucumber-container-ci/_build/latest?definitionId=6&branchName=master)

## TLDR
Linux / Mac:

` ./gradlew build `

Windows:

`gradlew.bat build`

## Purpose
The idea is to provide a quick-start template for new browser test automation projects that want to use a [BDD Syntax](https://docs.cucumber.io/guides/bdd-tutorial/) for describing test cases.

## Contributing
Contributions are welcome, please raise a Pull Request to get your changes back into master

## Running the template
The template has a couple of tests built in which open Chrome and run a couple of searches; these can be found in [sample.feature](src/test/resources/sample.feature)

The harness is built using [Gradle](https://gradle.org/), the tests are configured to run with every build:

` ./gradlew build `

You can also run the tests by executing [RunCukesTest.java](src/test/java/com/agilitas/example/RunCukesTest.java)

## Outputs
The primary output is a HTML test report, this can be found in [build/cucumber-hmtl-reports/index.html](build/cucumber-hmtl-reports/index.html)

There is also a [JUnit](https://junit.org) report which is great for CI pipelines [build/test-results/test/](build/test-results/test/)

## Dependency Check
This project also makes use of the [OWASP Dependency Check](https://www.owasp.org/index.php/OWASP_Dependency_Check) component and the [Gradle Plugin](https://jeremylong.github.io/DependencyCheck/dependency-check-gradle/index.html) so a scan will be performed on every build. The first build may take a couple of minutes if you haven't run the plugin before.

## Step definitions 
These are located in [MyStepDefs.java](src/test/java/com/agilitas/example/steps/MyStepDefs.java)