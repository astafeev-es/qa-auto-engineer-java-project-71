.PHONY: build

build:
	./gradlew clean build installDist

install:
	./gradlew clean installDist

run-dist:
	./build/install/app/bin/app $(ARGS)

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

check:
	./gradlew check
