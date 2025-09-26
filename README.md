# 📘 Click2Test: An Online Test Application

Click2Test is a Java Swing & AWT–based online test application that provides an interactive multiple-choice exam environment with a timer, navigation panel, and automatic result evaluation.

---

## 🚀 Features

### Welcome Panel

- Displays exam instructions.

- Includes a confirmation checkbox (exam starts only when checked).

- Start button to begin the test.

### Exam Panel

- Timer starts as soon as the exam begins.

- Questions presented in MCQ format with four options (radio buttons).

- Right-side navigation panel with question numbers:

   🟩 Green → Attempted

   🟥 Red → Visited but Unattempted

   ⬜ Grey → Not Visited

- Navigation buttons at the bottom:

    🟨Previous – go to previous question

    🟦Next – move to next question

    🟥Submit – end the exam and view results

### Result Panel

- Displays:

    ✅ Score

    📊 Total questions

    ✍️ Questions attempted

    ✔️ Correct answers

    ❌ Wrong answers

- If time runs out:

    - A dialog box appears → “Time’s up!”

    - Scoreboard is displayed automatically.

---

## 🖥️ Screenshots


---


## 🛠️ Tech Stack

- Java

- Swing (GUI framework)

- AWT (GUI components)


---

## ⚡ How to Run

- Clone this repository:

```bash
 git clone https://github.com/your-username/click2test.git
 ```


- Navigate to the project folder:

```bash 
cd click2test
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

- Add question randomization for each exam attempt.

- Implement database connectivity for storing results.

- Add user login/register system for multiple users.

- Provide review mode after submission.

---


## 🏆 Acknowledgements

This project was created by me to practice Java Swing & AWT concepts such as event handling, layout management, and GUI-based test applications.

---


