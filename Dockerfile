# Используем базовый образ OpenJDK для запуска приложения
FROM openjdk:17-jdk-slim

# Устанавливаем рабочий каталог внутри контейнера
WORKDIR /app

# Копируем сборку приложения в контейнер
COPY target/WordleSpring-0.0.1-SNAPSHOT.jar /app/WordleSpring-0.0.1-SNAPSHOT.jar
COPY target/classes/static/dict.txt /app/data/dict.txt

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/WordleSpring-0.0.1-SNAPSHOT.jar"]

# Открываем порт, на котором работает приложение
EXPOSE 8080
