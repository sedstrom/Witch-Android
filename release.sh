#!/bin/bash

# Uploads library to maven central and updates docs version.

# Exit if some of the commands exit with error
set -e

./gradlew clean test uploadArchives

./gradlew -q -b  docs-version.gradle update
