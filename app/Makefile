.PHONY: build install run-dist test report lint check

build:
	./gradlew build installDist

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
