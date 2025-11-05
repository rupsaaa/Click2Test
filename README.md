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
<img width="1227" height="992" alt="Screenshot 2025-11-05 112225" src="https://github.com/user-attachments/assets/0085cdd0-4808-4bcf-a7d9-667ebea93571" />

<img width="1919" height="1017" alt="Screenshot 2025-09-26 164344" src="https://github.com/user-attachments/assets/ca1de7f1-5250-4565-ab45-2bdde05451d8" />

<img width="1919" height="1016" alt="Screenshot 2025-09-26 164520" src="https://github.com/user-attachments/assets/fc3c5cd1-9008-4f93-9043-358d0da4a6bb" />

<img width="1916" height="1016" alt="Screenshot 2025-09-26 164632" src="https://github.com/user-attachments/assets/c71c8158-93bd-4ff6-8ca9-c04bc35f2d28" />

<img width="1919" height="1018" alt="Screenshot 2025-09-26 164652" src="https://github.com/user-attachments/assets/4acff67b-44f3-40fe-9b8d-bbf4ee3fadc0" />


---


## 🛠️ Tech Stack

- Java

- Swing (GUI framework)

- AWT (GUI components)


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

- Add question randomization for each exam attempt.

- Implement database connectivity for storing results.

- Add user login/register system for multiple users.

- Provide review mode after submission.

---


## 🏆 Acknowledgements

This project was created by me to practice Java Swing & AWT concepts such as event handling, layout management, and GUI-based test applications.

---


