# 1단계: 컴파일 및 빌드
FROM gradle:8.13-jdk17 AS build

WORKDIR /app

# 전체 프로젝트 복사 (리소스까지 다 포함)
COPY . .

# 클린 빌드 (캐시 사용하지 않음)
RUN ./gradlew clean :bbangzip-api:bootJar --no-daemon

# 2단계: 이미지 실행
FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드 결과물 복사
COPY --from=build /app/bbangzip-api/build/libs/bbangzip.jar app.jar

EXPOSE 8080

# 실행 명령에 config 설정도 포함
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]
