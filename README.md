### Hexlet tests and linter status:
[![Actions Status](https://github.com/astafeev-es/qa-auto-engineer-java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/astafeev-es/qa-auto-engineer-java-project-71/actions)

# Java Gendiff

Compares two configuration files and shows a difference.

## Usage example

```bash
./build/install/app/bin/app file1.json file2.json
```

Output:
```
{
  - follow: false
    host: hexlet.io
  - proxy: 123.234.53.22
  - timeout: 50
  + timeout: 20
  + verbose: true
}
```
