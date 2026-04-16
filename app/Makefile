.PHONY: build

build:
	./gradlew clean build

install:
	./gradlew clean installDist

run-dist:
	./build/install/app/bin/app

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

check:
	./gradlew check
