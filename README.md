# UniTrade - Student Flea Market Platform

UniTrade is a web-based, peer-to-peer campus marketplace designed exclusively for university students to buy, sell, and trade textbooks, electronics, furniture, clothing, and other student essentials safely.

This project was built as a university web development assignment, featuring a premium responsive user interface, dark mode engine, authentication, and in-app message inquiries.

---

## 🚀 Key Features

1. **Secure Student Authentication**
   * Uses **Spring Security** for session management and login filters.
   * Secure password hashing using **BCrypt**.
   * **Student Domain Verification**: Registration is restricted to emails ending in `.edu` or `.edu.gr` to ensure a safe, student-only platform.

2. **Marketplace Listings**
   * Browse all active listings on a responsive grid card layout.
   * Dynamic search by keyword queries.
   * Category filter pills with highlight selection states.

3. **In-App Message Inquiries Dashboard**
   * Interested buyers can send inquiries directly from item listings using an interactive modal.
   * Includes smart checks to prevent self-inquiring or guest submittals.
   * Dedicated **Inquiries Inbox** showing buyer details, item references, message content, and date received.
   * Integrated "Reply via Email" shortcut linking to local mail clients.

4. **Advertisements Management Dashboard**
   * View all active and sold listings.
   * Mark listings as "SOLD" or delete them permanently with automatic deletion checks.

5. **Aesthetics & Premium Polish**
   * **Global Dark Mode**: Full light/dark theme toggles built with Bootstrap 5.3's data themes.
   * Non-blocking header script preventing visual "white flashes" on dark theme loads.
   * Hover scale animations on grid cards and styled input focus rings.
   * Custom brand icon favicon linked globally.
   * Professional custom `/error` screen handling exceptions elegantly.

---

## 🛠️ Technology Stack

* **Backend**: Java 21, Spring Boot 4.x (MVC, Spring Data JPA, Spring Security)
* **Database**: H2 Database (configured with local file persistence)
* **Frontend**: HTML5, Thymeleaf Layout Fragments, JavaScript
* **CSS / Theme**: Bootstrap 5.3, Bootstrap Icons, custom CSS animations

---

## 🏁 How to Run

1. Make sure you have **Java 21** installed on your system.
2. Open a terminal in the project root directory.
3. Build and launch the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Access the application in your browser:
   * **App URL**: `http://localhost:8080/`

---

## 🗄️ Database Console (H2)

The application uses local file-based H2 database persistence to ensure listings and messages remain saved when the server restarts.

To inspect the database tables:
1. Navigate to: `http://localhost:8080/h2-console`
2. Configure connection settings exactly as shown:
   * **Driver Class**: `org.h2.Driver`
   * **JDBC URL**: `jdbc:h2:file:./data/unitradedb;DB_CLOSE_DELAY=-1;AUTO_SERVER=true`
   * **User Name**: `sa`
   * **Password**: *(leave completely empty)*
3. Click **Connect**.
