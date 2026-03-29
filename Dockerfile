# 1단계: 컴파일 및 빌드
FROM gradle:8.13-jdk17 AS build

WORKDIR /app

# 전체 프로젝트 복사 (리소스까지 다 포함)
COPY . .

# 클린 빌드 (캐시 사용하지 않음)
RUN ./gradlew clean :bbangzip-api:bootJar --no-daemon

# 2단계: 이미지 실행
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# 빌드 결과물 JAR 복사
COPY --from=build /app/bbangzip-api/build/libs/bbangzip.jar app.jar

# 프로필별 설정 (런타임 --spring.profiles.active 와 매칭)
COPY --from=build /app/bbangzip-api/src/main/resources/application-dev.yml /app/application-dev.yml
COPY --from=build /app/bbangzip-api/src/main/resources/application-prod.yml /app/application-prod.yml

ENV SPRING_PROFILES_ACTIVE=dev

EXPOSE 8080

CMD ["sh", "-c", "exec java -jar /app/app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
