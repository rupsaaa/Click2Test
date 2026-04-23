# 📘 Click2Test: An Online Test Application

Click2Test is a Java Swing & AWT–based online test application that provides an interactive multiple-choice exam environment with a timer, navigation panel, and automatic result evaluation.

---

## 🚀 Features

## 👤 User Input Panel
- Prompts user to enter Name and Roll Number before starting the test
- Ensures valid input before proceeding
## 📋 Welcome Panel
- Displays exam instructions
- Includes a confirmation checkbox (exam starts only when checked)
- Start button enabled only after confirmation
## 🧠 Exam Panel
- Timer starts as soon as the exam begins
- Fetches 15 random questions from database (no repetition per test)
- Questions displayed in MCQ format with four options (radio buttons)
- Right-side navigation panel with question number:
     > 🟩 Green → Attempted
      🟥 Red → Visited but Unattempted
      ⬜ Grey → Not Visited
- Navigation buttons at the bottom:
   > 🟨 Previous – go to previous question
🟦 Next – move to next question
🟥 Submit – end the exam and view results
## ⏱️ Timer Feature
- 15-minute countdown timer
- Auto-submits test when time expires
- Displays alert → “Time’s up!”
## 📊 Result Panel
- Displays:
   > ✅ Score
📊 Total questions
✍️ Questions attempted
✔️ Correct answers
❌ Wrong answers
- Result is automatically shown after submission or timeout
## 💾 Database Integration
- Questions are dynamically loaded from SQL database using JDBC
- Stores student details and scores after test submission
## ⭐ Feedback & Rating Panel
- Star-based rating system (1–5 stars)
- Dynamic rating labels (Worst → Excellent)
- Text area for user feedback
- Submit feedback button with confirmation message


---

## 🖥️ Screenshots

<img width="334" height="214" alt="Screenshot 2026-04-23 125238" src="https://github.com/user-attachments/assets/e2901575-7b3f-492f-880e-354927659b78" />

<img width="1920" height="1080" alt="Screenshot (6)" src="https://github.com/user-attachments/assets/88ba8c00-efdf-4fbd-b5a8-34732095e5bf" />

<img width="1920" height="1080" alt="Screenshot (7)" src="https://github.com/user-attachments/assets/2f6f6fcb-3850-4b16-9fe2-7b0d6fc2d846" />

<img width="1920" height="1080" alt="Screenshot (8)" src="https://github.com/user-attachments/assets/26a9d3a6-4952-4379-b104-e1393323a924" />

<img width="1920" height="1080" alt="Screenshot (9)" src="https://github.com/user-attachments/assets/53d9a0c9-cb67-4fa1-b181-37749ffb69ee" />

---


## 🛠️ Tech Stack

- Java

- Swing (GUI framework)

- AWT (GUI components)

- JDBC (Database Connection)

- MySQL / SQL Database
  
---

## ⚡ How to Run

- Clone this repository:

```bash
 git clone https://github.com/rupsaaa/Click2Test.git
 ```


- Navigate to the project folder:

```bash 
cd Click2Test
```


- Compile the program:

```bash
javac Click2Test.java
```


- Run the application:

```bash
java Click2Test
```

---


## 📌 Future Enhancements

- Convert to Spring Boot (REST API based system)
- Web-based version (React + Backend)
- Admin panel to manage questions
- Store feedback in database

---


## 🏆 Acknowledgements

This project was created by me to practice Java Swing & AWT concepts such as event handling, layout management, and GUI-based test applications.

---


