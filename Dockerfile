# 1단계: 컴파일 및 빌드
FROM gradle:8.13-jdk17 AS build

WORKDIR /app

# 필요한 파일만 먼저 복사
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradle.properties .
COPY gradlew .
COPY gradle/ gradle/

# bbangzip-api 모듈만 복사
COPY bbangzip-api/ bbangzip-api/
COPY bbangzip-domain/ bbangzip-domain/

# bbangzip-api만 빌드
RUN ./gradlew :bbangzip-api:bootJar --no-daemon --build-cache

# 2단계: 이미지 실행
FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드 결과물 복사
COPY --from=build /app/bbangzip-api/build/libs/bbangzip.jar bbangzip.jar

EXPOSE 8080

# 실행 명령에 config 설정도 포함
CMD ["java", "-jar", "bbangzip.jar", "--spring.profiles.active=dev"]

