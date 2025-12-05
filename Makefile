.PHONY: up down run test

up:
	docker-compose up -d

down:
	docker-compose down

down-volumes:
	docker-compose down -v

run: up
	./gradlew bootRun

test:
	./gradlew test

clean:
	./gradlew clean
