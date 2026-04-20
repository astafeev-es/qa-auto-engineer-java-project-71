[![Actions Status](https://github.com/astafeev-es/qa-auto-engineer-java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/astafeev-es/qa-auto-engineer-java-project-71/actions)
[![Build Status](https://github.com/astafeev-es/qa-auto-engineer-java-project-71/actions/workflows/build.yml/badge.svg)](https://github.com/astafeev-es/qa-auto-engineer-java-project-71/actions)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=astafeev-es_qa-auto-engineer-java-project-71&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=astafeev-es_qa-auto-engineer-java-project-71)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=astafeev-es_qa-auto-engineer-java-project-71&metric=coverage)](https://sonarcloud.io/summary/new_code?id=astafeev-es_qa-auto-engineer-java-project-71)

# Difference Generator

Difference Generator (gendiff) is a CLI tool that compares two configuration files and identifies the differences between them. It supports JSON and YAML formats and can output the results in several formats: stylish (default), plain, and JSON.

## Features

- Supports JSON and YAML (.yml, .yaml) input files.
- Multiple output formats: `stylish`, `plain`, `json`.
- Easy to use CLI interface.

## Installation

### Prerequisites

- Java Development Kit (JDK) 21 or higher.

### Build and Install

Clone the repository and build the project:

```bash
git clone https://github.com/astafeev-es/qa-auto-engineer-java-project-71.git
cd qa-auto-engineer-java-project-71/app
./gradlew installDist
```

The executable will be available at `./build/install/app/bin/gendiff`.

## Usage

```bash
./build/install/app/bin/gendiff -h
```

### Examples

**Stylish output (default):**
```bash
./build/install/app/bin/gendiff file1.json file2.json
```

**Plain output:**
```bash
./build/install/app/bin/gendiff --format plain file1.json file2.json
```

**JSON output:**
```bash
./build/install/app/bin/gendiff --format json file1.json file2.json
```

## Demos

### First Demo: JSON Comparison
[![asciicast](https://asciinema.org/a/271y3LW10Sp4Hca6.svg)](https://asciinema.org/a/271y3LW10Sp4Hca6)

### Second Demo: YAML Comparison
[![asciicast](https://asciinema.org/a/RI960ANinmQkpOGR.svg)](https://asciinema.org/a/RI960ANinmQkpOGR)

### Third Demo: Plain Format
[![asciicast](https://asciinema.org/a/WoHhhpBEkKSjNu2V.svg)](https://asciinema.org/a/WoHhhpBEkKSjNu2V)

### Fourth Demo: JSON Format
[![asciicast](https://asciinema.org/a/ugJt7HZY0ZkAGlc0.svg)](https://asciinema.org/a/ugJt7HZY0ZkAGlc0)
