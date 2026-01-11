
---

# 🏦 **InBank Java Application — Detailed Documentation**

## 📘 **Overview**

**InBank** is a simple console-based Java banking system that allows users to:

* Create a new bank account
* Log in to an existing account
* Check balance
* Transfer money between accounts

It uses **MySQL** as the backend database and **JDBC** for database connectivity.

---

## 🧱 **System Architecture**

### 🔹 Packages and Classes

| Package   | Class             | Responsibility                                                                        |
| --------- | ----------------- | ------------------------------------------------------------------------------------- |
| `banking` | `Bank`            | Main entry point of the application. Handles user interaction and menu navigation.    |
| `banking` | `Bank_Management` | Core business logic for account creation, login, balance viewing, and money transfer. |
| `banking` | `DBConnection`    | Manages connection to the MySQL database using JDBC.                                  |

---

## ⚙️ **Class-by-Class Explanation**

---

### 🏁 **1. `Bank` Class**

**File:** `Bank.java`
**Purpose:** Entry point for the application; handles the main user interface.

#### **Responsibilities**

* Displays the main menu (Create Account / Login / Exit).
* Accepts user input using `BufferedReader`.
* Routes user choices to `Bank_Management` methods.

#### **Key Methods**

* `main(String[] args)`:
  Continuously displays the main menu and waits for user commands.
  Uses `try-catch` for handling invalid inputs.

#### **Menu Options**

| Option | Action                     | Called Method                     |
| ------ | -------------------------- | --------------------------------- |
| 1      | Create new user account    | `Bank_Management.createAccount()` |
| 2      | Log in to existing account | `Bank_Management.loginAccount()`  |
| 3      | Exit application           | `System.exit(0)`                  |

#### **Example Flow**

```
Welcome to InBank
1) Create Account
2) Login Account
3) Exit
Enter Choice: 1
Enter Unique Username: John
Enter Password: 1234
```

---

### 🧮 **2. `Bank_Management` Class**

**File:** `Bank_Management.java`
**Purpose:** Implements all banking operations (CRUD + transactions).

#### **Static Connection**

```java
static Connection con = DBConnection.getConnection();
```

A single shared connection obtained from the `DBConnection` class.

---

#### **A. `createAccount(String name, int passCode)`**

**Purpose:** Adds a new user record into the database.

**SQL:**

```sql
INSERT INTO customer (cname, balance, pass_code)
VALUES (?, 1000, ?);
```

**Steps:**

1. Validate inputs.
2. Insert new record with default balance = `1000`.
3. Catch duplicate username errors.

**Possible Errors:**

* `SQLIntegrityConstraintViolationException`: Username already exists.
* Empty name or password → rejected.

---

#### **B. `loginAccount(String name, int passCode)`**

**Purpose:** Validates user credentials and opens account operations menu.

**SQL:**

```sql
SELECT * FROM customer WHERE cname = ? AND pass_code = ?;
```

**Steps:**

1. Validate inputs.
2. If credentials match:

   * Retrieve account number.
   * Open secondary menu:

     * 1️⃣ Transfer Money
     * 2️⃣ View Balance
     * 3️⃣ Logout
3. If credentials invalid → show error.

---

#### **C. `getBalance(int acNo)`**

**Purpose:** Displays account information (number, name, balance).

**SQL:**

```sql
SELECT * FROM customer WHERE ac_no = ?;
```

**Output Format:**

```
------------------------------------------------------
  Account No   Customer Name     Balance
------------------------------------------------------
       101            John       1500.00
------------------------------------------------------
```

---

#### **D. `transferMoney(int sender_ac, int receiver_ac, int amount)`**

**Purpose:** Handles money transfer between two accounts using SQL transactions.

**Steps:**

1. Check that all inputs are valid.
2. Disable auto-commit (`con.setAutoCommit(false)`).
3. Verify sender has sufficient balance.
4. Execute debit and credit operations.
5. Commit transaction if both succeed.
6. Rollback on any failure.

**SQL Used:**

```sql
SELECT balance FROM customer WHERE ac_no = ?;
UPDATE customer SET balance = balance - ? WHERE ac_no = ?;
UPDATE customer SET balance = balance + ? WHERE ac_no = ?;
```

**Error Handling:**

* Rolls back on SQL exceptions.
* Prints stack trace for debugging.

---

### 🧩 **3. `DBConnection` Class**

**File:** `DBConnection.java`
**Purpose:** Establishes a JDBC connection to the MySQL database.

#### **Configuration:**

```java
String url = "jdbc:mysql://localhost:3306/BANK?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
String user = "root";
String password = "password@12345";
```

#### **Driver Loading:**

```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

*(Note: In your code, it’s misspelled as `"con.mysql.cj.jdbc.Driver"` — it should be `"com.mysql.cj.jdbc.Driver"`.)*

#### **Error Handling:**

* `ClassNotFoundException` → If MySQL JDBC driver is missing.
* `SQLException` → On invalid credentials or connection issues.

#### **Testing Main Method:**

A small test inside `main()` verifies database connectivity.

---

## 🧰 **Database Schema Example**

Assuming a `customer` table structure like:

```sql
CREATE DATABASE BANK;
USE BANK;

CREATE TABLE customer (
    ac_no INT AUTO_INCREMENT PRIMARY KEY,
    cname VARCHAR(50) UNIQUE NOT NULL,
    balance INT DEFAULT 1000,
    pass_code INT NOT NULL
);
```

---

## 🧪 **Sample Execution**

### **Create Account**

```
Enter Unique Username: alice
Enter Password: 1234
Account created successfully! You can now login.
```

### **Login and Check Balance**

```
Enter Username: alice
Enter Password: 1234
Hello, alice! What would you like to do?
1) Transfer Money
2) View Balance
3) Logout
```

### **Transfer Money**

```
Enter Receiver A/c No: 102
Enter Amount: 200
Transaction successful!
```

---

## ⚠️ **Error Handling & Validation**

| Scenario                | Message / Behavior                              |
| ----------------------- | ----------------------------------------------- |
| Empty username/password | `-- All fields are required!`                   |
| Invalid login           | `Invalid username or password!`                 |
| Duplicate username      | `Username already exists!`                      |
| Insufficient balance    | `Insufficient Balance!`                         |
| SQL errors              | Prints stack trace and rolls back transactions. |

---

## 💡 **Suggested Improvements**

| Area                 | Suggestion                                                                         |
| -------------------- | ---------------------------------------------------------------------------------- |
| **Security**         | Store password using hashing (e.g., SHA-256 or bcrypt) instead of plaintext `int`. |
| **Input Validation** | Prevent negative transfer amounts and self-transfer.                               |
| **Error Handling**   | Use proper logging (e.g., `java.util.logging`) instead of `printStackTrace()`.     |
| **Database Layer**   | Consider using connection pooling (`HikariCP` or `Apache DBCP`).                   |
| **Scalability**      | Implement DAO (Data Access Object) pattern for cleaner separation.                 |
| **UI/UX**            | Add retry limits and better prompts.                                               |
| **Driver Name**      | Fix driver string: `"com.mysql.cj.jdbc.Driver"`.                                   |

---

## 📄 **Summary**

| Feature        | Description                                      |
| -------------- | ------------------------------------------------ |
| Language       | Java                                             |
| Database       | MySQL                                            |
| Framework      | JDBC                                             |
| Design         | Procedural (single static connection)            |
| User Interface | Console-based text menu                          |
| Key Functions  | Account creation, login, balance check, transfer |
| Error Recovery | SQL rollback and exception handling              |

---

Would you like me to **format this into a PDF or DOCX documentation file** (with table of contents, headings, and examples) so you can include it in a report or submission?
