# Tic Tac Toe Game (CLI – Java) 🎮

A simple and professional **command-line Tic-Tac-Toe game** built using Java.  
This project demonstrates core programming concepts such as control flow, arrays, input handling, and basic game logic.

---

## About the Project

This project is a terminal-based implementation of the classic **Tic-Tac-Toe** game.  
It is designed to help learners strengthen their understanding of:

- Conditional logic  
- Loops and flow control  
- Arrays and data handling  
- User input validation  
- Game state management  

---

## Features

- Two-player turn-based gameplay  
- 3×3 board displayed in the terminal  
- Automatic win detection  
- Draw detection when no moves remain  
- Input validation for invalid or occupied positions  
- Clean and well-structured code  

---

## Prerequisites ⚙️

- **Java JDK 8 or higher**  
- A terminal or command prompt  

---

Verify Java installation:
```bash
java -version
```

---

## Project Structure

```
01_TicTacToe_Game/
│
├── src/
│   └── Main.java        <-- Entry point and game logic
│
├── .gitignore
└── README.md
```

---

## How to Run

### Step 1 — Navigate to the project directory

```bash
cd CLI_Projects/01_TicTacToe_Game
```

### Step 2 — Compile

```bash
javac src/Main.java
```

### Step 3 — Run

```bash
java -cp src Main
```

---

## Gameplay 🕹️

* Player **X** goes first
* Player **O** goes second
* Players select positions from **1 to 9**
* The first player to align three marks wins
* If all positions are filled without a winner, the game ends in a draw

### Sample Output

```
Welcome to Tic Tac Toe!

 X | O | X
-----------
 O | X | 6
-----------
 X | O | 9

Player X wins!
```

---

## Contributing 🤝

Contributions are welcome.

1. Fork the repository
2. Create a new branch

   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Make your changes
4. Commit your work

   ```bash
   git commit -m "Add: short description of changes"
   ```
5. Push to your fork
6. Open a Pull Request

Please ensure your code is clean, readable, and well-structured.

---

## Future Improvements

* Single-player mode with AI
* Difficulty levels
* Replay option after game completion
* Score tracking
* GUI version using Java Swing or JavaFX

---

## License

This project is licensed under the **MIT License**.
You are free to use, modify, and distribute this software with proper attribution.

---

## Author

**Minsu Agrahari**
GitHub: [https://github.com/Minsu-Agrahari](https://github.com/Minsu-Agrahari)
