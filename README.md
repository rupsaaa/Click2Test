Here’s a clean, professional **README.md** you can directly paste into GitHub 👇

---

# 🎯 Click2Test – Java Online Test Application

## 📌 Introduction

**Click2Test** is a desktop-based online examination system developed using Java Swing. It simulates a real-time test environment where users can attempt MCQ-based questions, track progress, and receive instant results. The system also includes student authentication, timer functionality, feedback collection, and database integration.

This project is ideal for academic demonstrations, mini-projects, and understanding GUI + database integration in Java.

---

## 🚀 Features

### 👤 Student Authentication

* User enters **Name** and **Roll Number**
* Input validation:

  * Name → Only alphabets allowed
  * Roll Number → Exactly 6 digits
* Prevents duplicate roll numbers
* Detects previous attempts and asks:

  > *“You have already taken the test. Do you want to retake?”*

---

### ⏳ Progress Bar (Startup UX)

* Loading screen with animated progress bar
* Smooth transition to login window

---

### 📋 Instruction Panel

* Displays exam rules clearly
* Start button enabled only after confirmation checkbox

---

### 📝 Test Interface

* MCQ-based questions (loaded from database)
* Navigation:

  * Next / Previous buttons
  * Question palette (right side)
* Color indicators:

  * 🟩 Attempted
  * 🟥 Visited but not attempted
  * ⬜ Not visited

---

### ⏱ Timer

* 15-minute countdown timer
* Auto-submit when time ends

---

### 📊 Result System

* Displays:

  * Total Questions
  * Attempted
  * Correct
  * Wrong
* Score saved to database

---

### ⭐ Feedback System

* 5-star rating system
* Emoji-based experience indicator
* Text feedback submission

---

## 🛠 Tech Stack

| Technology   | Usage                 |
| ------------ | --------------------- |
| Java (Swing) | GUI Development       |
| AWT          | Event Handling        |
| JDBC         | Database Connectivity |
| MySQL        | Data Storage          |
| SQL          | Query Execution       |

---

## 🗂 Project Structure

```
Click2Test/
│── Click2Test.java        # Main application
│── StudentDetailsUI.java  # Login UI
│── ProgressBarScreen.java # Loading screen
│── DBConnection.java      # Database connection
│── Question.java          # Question model
│── README.md
```

---

## ⚙️ Installation & Setup

### 1️⃣ Prerequisites

* Java JDK (8 or above)
* MySQL Server
* IDE (IntelliJ / Eclipse / VS Code)

---

### 2️⃣ Clone the Repository

```bash
git clone https://github.com/rupsaaa/Click2Test.git
cd Click2Test
```

---

### 3️⃣ Setup Database

#### Create Database

```sql
CREATE DATABASE click2test;
USE click2test;
```

#### Create Questions Table

```sql
CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT,
    option_a VARCHAR(255),
    option_b VARCHAR(255),
    option_c VARCHAR(255),
    option_d VARCHAR(255),
    correct_option CHAR(1)
);
```

#### Create Results Table

```sql
CREATE TABLE results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    roll_no VARCHAR(6),
    score INT
);
```

#### (Optional but Recommended) Prevent Duplicate Roll Numbers

```sql
ALTER TABLE results ADD UNIQUE (roll_no);
```

---

### 4️⃣ Configure Database Connection

Edit your `DBConnection.java`:

```java
Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/click2test",
    "root",
    "your_password"
);
```

---

### 5️⃣ Run the Application

Compile:

```bash
javac *.java
```

Run:

```bash
java ProgressBarScreen
```

---

## 🎥 Demo

[Screen Recording 2026-04-30 120342.mp4](../../../Videos/Screen%20Recordings/Screen%20Recording%202026-04-30%20120342.mp4)




---

## 📌 Future Enhancements

* Admin panel for adding questions
* Leaderboard system
* Subject-wise tests
* Online deployment (Web version)
* User authentication with login/signup

---

## 🤝 Contribution

Feel free to fork this repo and improve features. Pull requests are welcome!

---



