# Explore with me

## Description
Explore with me - приложение с микросервисной архитектурой, в котором можно предложить какое-либо событие от выставки до похода в кино и собрать компанию для участия в нём.
Реализована возможность создавать события, оставлять заявки на участие в событиях, а также создавать подбоорки событий. Разработан отдельный микросервис, который учитывает статистику запросов к определенным эндпоинтам.

## Tech Stack 🔧
[![Java](https://img.shields.io/badge/Java%2011-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/) [![Spring](https://img.shields.io/badge/Spring%20Boot%202.7.9-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-framework) [![JPA](https://img.shields.io/badge/JPA-FF5733?style=for-the-badge&logo=JUnit&logoColor=white)](https://docs.oracle.com/javase/tutorial/jdbc/overview/index.html) [![PostgreSQL Database](https://img.shields.io/badge/PostgreSQL-0000FF?style=for-the-badge&logo=H2&logoColor=white)](https://www.postgresql.org/) [![JUnit](https://img.shields.io/badge/JUnit%205-9F2B68?style=for-the-badge&logo=JUnit&logoColor=white)](https://junit.org/junit5/docs/current/user-guide/)
[![Maven](https://img.shields.io/badge/Maven-00008B?style=for-the-badge&logo=Maven&logoColor=white)](https://maven.apache.org/) [![Docker](https://img.shields.io/badge/Docker-00008B?style=for-the-badge&logo=Docker&logoColor=white)](https://www.docker.com/) [![Swagger](https://img.shields.io/badge/Swagger-006400?style=for-the-badge&logo=Maven&logoColor=white)](https://swagger.io/)

## How to set up the project ▶

1) Склонируйте репозиторий и перейдите в него
```
git clone https://github.com/Antroverden/java-explore-with-me.git
```
2) Запустите проект в Intellij IDEA или введите в консоли
```
mvn clean package
```
3) Убедитесь, что у вас запущен Docker и введите в консоли
```
docker compose up
```
Примеры HTTP-запросов к контроллерам при запущенном приложении можно увидеть по ссылке:
```
http://localhost:8080/swagger-ui/index.html
http://localhost:9090/swagger-ui/index.html
```
