# 📝 Diary App

Diary App is a simple web application that allows users to write, manage and view their personal diary entries. The application supports moods, top entries, filtering by mood, and a secure login/registration system.

---

## 📌 Features

- ✍️ Create diary entries with title, description, mood (1–10), date & time
- ⭐ Mark entries as "Top" favorites
- 🔍 View all entries in tables (Top Entries / Mood-based / All Entries)
- 🔐 Register and login with encrypted passwords (Spring Security)
- 🧠 Simple mood filters: Top mood, Mid-mood, Low mood
- 🧼 Flash messages for actions (e.g., user created, entry saved)
- 💾 All data stored in a relational database

---

## 🛠 Technologies Used

- **Backend:** Java, Spring Boot, Spring MVC, Spring Security, Spring Data JPA
- **Frontend:** Thymeleaf, Bootstrap 5
- **Database:**  MySQL 
- **Other:** MapStruct, Validation, Password Encoding, Flash messages

---

## 🧰 Installation

 Make sure you have **Java 17+**, **Maven**, and optionally **MySQL** installed.

### 1. Clone the Repository

```bash
git clone https://github.com/PavolKohar/Diary.git
cd Diary
```

### 2. Configure the Application

Edit the `src/main/resources/application-example.properties` file if needed:

```properties
spring.datasource.url=jdbc:h2:mem:testDb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

Or switch to MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/diaryDb
spring.datasource.username=yourUsername
spring.datasource.password=yourPassword
```

### 3. Build and Run

```bash
./mvnw spring-boot:run
```

Or using Maven directly:

```bash
mvn clean install
mvn spring-boot:run
```

### 💻 Or run from your IDE:
1.	Open the project in your preferred IDE (e.g. IntelliJ IDEA, VS Code).
2.	Locate the DiaryApplication class.
3.	Right-click the class and select Run ‘DiaryApplication’.

Then open your browser:

```
http://localhost:8080
```

---

## 👤 Author

[Pavol Kohár](https://github.com/PavolKohar)