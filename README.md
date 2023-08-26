# Luminous Onion
![Java CI](https://github.com/zkarpinski/Luminous-Onion/actions/workflows/maven.yml/badge.svg)
![CodeQL](https://github.com/zkarpinski/Luminous-Onion/actions/workflows/codeql.yml/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=zkarpinski_Luminous-Onion&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=zkarpinski_Luminous-Onion)
[![Docker](https://img.shields.io/badge/Docker-Latest-blue)](https://hub.docker.com/r/zkarpinski/luminous-onion/tags)
## Setup
Java Version: **17**

## Build & Deployment
To simplify, you may use the accompanying `buildAndPackageScript.cmd` script. Otherwise below are the steps to package and build into a docker image.
1. In the root of the project, run `mvn package` which builds the springboot back-end and node front-end.
2. Next run `docker build -t "luminous-onion" .` to build the docker image
3. Execute `docker run -d -P luminous-onion` to run the docker image.

## Running Instructions
TBD
## Examples & Samples
Example scripts and sample result files are available in `./examples/`.

## Resources
1. https://developer.okta.com/blog/2022/06/17/simple-crud-react-and-spring-boot